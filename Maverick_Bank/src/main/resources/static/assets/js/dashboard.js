window.DashboardApp = (() => {
  let chart;

  const state = {
    session: null,
    user: null,
    users: [],
    customers: [],
    employees: [],
    accounts: [],
    beneficiaries: [],
    currentCustomerProfile: null,
    currentEmployeeProfile: null,
    currentCustomerAccounts: [],
    currentCustomerAccountIds: [],
    currentCustomerBeneficiaries: [],
    transactions: [],
    loans: [],
    loanApplications: []
  };

  const mount = () => document.getElementById('pageMount');

  async function safeRequest(fn, fallback) {
    try {
      return await fn();
    } catch (error) {
      return fallback;
    }
  }

  function getErrorMessage(error, fallback = 'Operation failed.') {
    return error?.response?.data?.message || error?.response?.data || error?.message || fallback;
  }

  function getSessionRawUser() {
    return state.session?.user?.raw || state.user?.raw || null;
  }

  function inferCustomerProfileFromSession() {
    const rawUser = getSessionRawUser();
    const profile = rawUser?.customerProfile || state.user?.customerProfile || null;
    if (profile && (profile.id || profile.userId || rawUser?.id)) {
      return {
        id: profile.id ?? profile.customerProfileId ?? null,
        fullName: profile.fullName || profile.name || state.user.username,
        email: profile.email || rawUser?.username || state.user.username,
        phone: profile.phone || profile.mobileNumber || '-',
        userId: profile.userId || rawUser?.id || state.user.id
      };
    }
    return null;
  }

  function ensureCustomerOwnsAccount(accountId) {
    const id = Number(accountId);
    return state.user.role !== 'CUSTOMER' || state.currentCustomerAccountIds.includes(id);
  }

  function customerAccountOptions(selected = '') {
    return state.currentCustomerAccounts.map((a) => {
      const id = a.accountId ?? '';
      const label = `${id} - ${a.accountNumber || ''}`;
      return `<option value="${id}" ${String(selected) === String(id) ? 'selected' : ''}>${window.Utils.escapeHtml(label)}</option>`;
    }).join('');
  }

  function customerAccountField(name, label) {
    if (state.user.role === 'CUSTOMER') {
      return `<label class="form-label">${label}</label><select class="form-select" name="${name}" required>${customerAccountOptions()}</select>`;
    }
    return `<label class="form-label">${label}</label><input class="form-control" name="${name}" type="number" required>`;
  }

  async function loadSharedData() {
    const role = state.user.role;

    if (role === 'CUSTOMER') {
      state.users = [];
      state.customers = [];
      state.employees = [];
      state.accounts = [];
      state.beneficiaries = await safeRequest(async () => (await window.apiClient.get('/api/v1/beneficiaries/getAll')).data, []);
      state.loanApplications = await safeRequest(async () => (await window.apiClient.get('/api/loan-applications/all')).data, []);
      state.loans = await safeRequest(async () => (await window.apiClient.get('/api/loan-applications/loans')).data, []);
      return;
    }

    state.users = await safeRequest(async () => (await window.apiClient.get('/api/users/get/all')).data, []);
    state.customers = await safeRequest(async () => (await window.apiClient.get('/api/customer-profile/get/all')).data, []);
    state.employees = await safeRequest(async () => (await window.apiClient.get('/api/employee-profile/get/all')).data, []);
    state.accounts = await safeRequest(async () => (await window.apiClient.get('/api/v1/accounts/getAll')).data, []);
    state.beneficiaries = await safeRequest(async () => (await window.apiClient.get('/api/v1/beneficiaries/getAll')).data, []);
    state.loanApplications = await safeRequest(async () => (await window.apiClient.get('/api/loan-applications/all')).data, []);
    state.loans = await safeRequest(async () => (await window.apiClient.get('/api/loan-applications/loans')).data, []);
  }

  async function loadRoleData() {
    const role = state.user.role;

    state.currentCustomerProfile = null;
    state.currentEmployeeProfile = null;
    state.currentCustomerAccounts = [];
    state.currentCustomerAccountIds = [];
    state.currentCustomerBeneficiaries = [];

    if (role === 'CUSTOMER') {
      state.currentCustomerProfile = await safeRequest(
        async () => (await window.apiClient.get('/api/customer-profile/me')).data,
        inferCustomerProfileFromSession()
      );

      if (state.currentCustomerProfile?.id) {
        state.currentCustomerAccounts = await safeRequest(
          async () => (await window.apiClient.get(`/api/v1/accounts/customer/${state.currentCustomerProfile.id}`)).data,
          []
        );
      }

      state.currentCustomerAccountIds = state.currentCustomerAccounts
        .map((a) => Number(a.accountId))
        .filter((n) => !Number.isNaN(n));

      state.currentCustomerBeneficiaries = state.beneficiaries.filter((b) =>
        state.currentCustomerAccountIds.includes(Number(b.accountId))
      );

      return;
    }

    state.currentCustomerProfile =
      state.customers.find((item) => Number(item.userId) === Number(state.user.id)) ||
      inferCustomerProfileFromSession();

    state.currentEmployeeProfile =
      state.employees.find((item) => Number(item.userId) === Number(state.user.id)) || null;

    if (state.currentCustomerProfile?.id) {
      state.currentCustomerAccounts = await safeRequest(
        async () => (await window.apiClient.get(`/api/v1/accounts/customer/${state.currentCustomerProfile.id}`)).data,
        []
      );
    }

    state.currentCustomerAccountIds = state.currentCustomerAccounts
      .map((a) => Number(a.accountId))
      .filter((n) => !Number.isNaN(n));

    state.currentCustomerBeneficiaries = [];
  }

  function pageHeader(title, subtitle, action = '') {
    return `<div class="page-header"><div><h2 class="mb-1">${title}</h2><p class="text-muted mb-0">${subtitle}</p></div>${action}</div>`;
  }

  function actionButtons(buttons) {
    return `<div class="table-actions">${buttons.join('')}</div>`;
  }

  function ensureEditModal() {
    if (document.getElementById('editEntityModal')) return;
    document.body.insertAdjacentHTML('beforeend', `
      <div class="modal fade" id="editEntityModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="editEntityModalLabel">Edit</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div id="editEntityAlert"></div>
              <form id="editEntityForm" class="row g-3"></form>
            </div>
          </div>
        </div>
      </div>`);
  }

  function openEditModal(title, fields, values, onSubmit) {
    ensureEditModal();
    document.getElementById('editEntityModalLabel').textContent = title;
    const form = document.getElementById('editEntityForm');
    form.innerHTML = fields.map((field) => {
      const value = values[field.name] ?? '';
      const escaped = String(value)
        .replace(/&/g, '&amp;')
        .replace(/"/g, '&quot;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
      return `<div class="${field.col || 'col-md-6'}"><label class="form-label">${field.label}</label><input class="form-control" name="${field.name}" type="${field.type || 'text'}" value="${escaped}" ${field.readonly ? 'readonly' : ''} ${field.required === false ? '' : 'required'}></div>`;
    }).join('') + `<div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Save Changes</button></div>`;
    form.onsubmit = async (e) => {
      e.preventDefault();
      const fd = new FormData(form);
      await onSubmit(fd);
    };
    bootstrap.Modal.getOrCreateInstance(document.getElementById('editEntityModal')).show();
  }

  function closeEditModal() {
    const el = document.getElementById('editEntityModal');
    if (el) bootstrap.Modal.getOrCreateInstance(el).hide();
  }

  async function confirmAction(message, fn, successMessage, failMessage) {
    if (!window.confirm(message)) return;
    try {
      await fn();
      await refresh();
      route();
      window.Utils.showGlobal(successMessage, 'success');
    } catch (error) {
      window.Utils.showGlobal(getErrorMessage(error, failMessage), 'danger');
    }
  }

  function overviewStats() {
    return `
      <div class="row g-4 mb-4">
        <div class="col-md-6 col-xl-3"><div class="card-ui stat-box"><div class="stat-label">Users</div><div class="stat-value">${state.users.length}</div></div></div>
        <div class="col-md-6 col-xl-3"><div class="card-ui stat-box"><div class="stat-label">Customers</div><div class="stat-value">${state.customers.length}</div></div></div>
        <div class="col-md-6 col-xl-3"><div class="card-ui stat-box"><div class="stat-label">Employees</div><div class="stat-value">${state.employees.length}</div></div></div>
        <div class="col-md-6 col-xl-3"><div class="card-ui stat-box"><div class="stat-label">Accounts</div><div class="stat-value">${state.accounts.length}</div></div></div>
      </div>`;
  }

  function renderOverview() {
    mount().innerHTML = `
      ${pageHeader('Overview', 'System summary and quick access')}
      ${overviewStats()}
      <div class="row g-4">
        <div class="col-xl-8">
          <div class="card-ui section-card">
            <div class="d-flex justify-content-between align-items-center mb-3"><h5 class="mb-0">Quick access</h5></div>
            <div class="quick-grid">
              ${quickCards()}
            </div>
          </div>
        </div>
        <div class="col-xl-4">
          <div class="card-ui section-card">
            <h5 class="mb-3">Role mix</h5>
            <canvas id="roleChart" height="220"></canvas>
          </div>
        </div>
      </div>
      <div class="card-ui section-card mt-4">
        <h5 class="mb-3">Recent users</h5>
        ${window.Utils.table(['ID', 'Username', 'Role', 'Active'], state.users.slice(0, 8).map((u) => `<tr><td>${u.id ?? ''}</td><td>${window.Utils.escapeHtml(u.username)}</td><td>${window.Utils.escapeHtml(u.role)}</td><td>${window.Utils.statusBadge(u.active ? 'ACTIVE' : 'INACTIVE')}</td></tr>`).join(''))}
      </div>
    `;
    renderRoleChart();
  }

  function quickCards() {
    const role = state.user.role;
    const items = role === 'ADMIN'
      ? [
          ['#users', 'User management', 'Create and review application users'],
          ['#customers', 'Customer profiles', 'Manage customer profile records'],
          ['#employees', 'Employee profiles', 'Maintain employee information'],
          ['#accounts', 'Account operations', 'Create and review bank accounts']
        ]
      : role === 'EMPLOYEE'
      ? [
          ['#users', 'Users', 'Review and manage application users'],
          ['#customers', 'Customers', 'Review customer profile details'],
          ['#accounts', 'Accounts', 'Open, update, and close accounts'],
          ['#transactions', 'Transactions', 'Check history, deposits, and transfers']
        ]
      : [
          ['#my-banking', 'My banking', 'See your profile and linked accounts'],
          ['#beneficiaries', 'Beneficiaries', 'Add and view beneficiaries'],
          ['#transactions', 'Transactions', 'Deposit, withdraw, transfer, and history'],
          ['#statements', 'Statements', 'Generate account statement for date range']
        ];
    return items.map(([hash, title, text]) => `<a href="${hash}" class="quick-link"><h6>${title}</h6><p class="text-muted mb-0">${text}</p></a>`).join('');
  }

  function renderRoleChart() {
    const ctx = document.getElementById('roleChart');
    if (!ctx) return;
    if (chart) chart.destroy();
    const counts = {
      ADMIN: state.users.filter((u) => u.role === 'ADMIN').length,
      EMPLOYEE: state.users.filter((u) => u.role === 'EMPLOYEE').length,
      CUSTOMER: state.users.filter((u) => u.role === 'CUSTOMER').length
    };
    chart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['Admin', 'Employee', 'Customer'],
        datasets: [{ data: [counts.ADMIN, counts.EMPLOYEE, counts.CUSTOMER] }]
      },
      options: { plugins: { legend: { position: 'bottom' } } }
    });
  }

  function renderUsers() {
    mount().innerHTML = `
      ${pageHeader('Users', 'Create users and view existing accounts', createUserForm())}
      <div class="card-ui section-card mb-4"><div id="usersFormAlert"></div>${createUserFormBody()}</div>
      <div class="card-ui section-card">
        <h5 class="mb-3">All users</h5>
        ${window.Utils.table(['ID', 'Username', 'Role', 'Status', 'Actions'], state.users.map((u) => `<tr><td>${u.id ?? ''}</td><td>${window.Utils.escapeHtml(u.username)}</td><td>${window.Utils.escapeHtml(u.role)}</td><td>${window.Utils.statusBadge(u.active ? 'ACTIVE' : 'INACTIVE')}</td><td>${['ADMIN', 'EMPLOYEE'].includes(state.user.role) ? actionButtons([u.active ? `<button class="btn btn-sm btn-outline-warning" onclick="DashboardApp.deactivateUser(${u.id})">Deactivate</button>` : '', `<button class="btn btn-sm btn-outline-danger" onclick="DashboardApp.deleteUser(${u.id})">Delete</button>`].filter(Boolean)) : ''}</td></tr>`).join(''))}
      </div>`;
    bindUserForm();
  }

  function createUserForm() {
    return '';
  }

  function createUserFormBody() {
    return `<form id="userCreateForm" class="row g-3">
      <div class="col-md-5"><label class="form-label">Email</label><input class="form-control" name="email" type="email" required></div>
      <div class="col-md-4"><label class="form-label">Password</label><input class="form-control" name="password" type="password" required></div>
      <div class="col-md-3"><label class="form-label">Role</label><select class="form-select" name="role" required><option value="CUSTOMER">Customer</option><option value="EMPLOYEE">Employee</option></select></div>
      <div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Create User</button></div>
    </form>`;
  }

  function bindUserForm() {
    const form = document.getElementById('userCreateForm');
    if (!form) return;
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(form);
      const payload = { email: fd.get('email'), password: fd.get('password') };
      const role = fd.get('role');
      try {
        await window.apiClient.post(role === 'CUSTOMER' ? '/api/users/add/customer' : '/api/users/add/employee', payload);
        window.Utils.showAlert('usersFormAlert', 'User created successfully.', 'success');
        await refresh();
        renderUsers();
        form.reset();
      } catch (error) {
        window.Utils.showAlert('usersFormAlert', getErrorMessage(error, 'Unable to create user.'), 'danger');
      }
    });
  }

  function renderCustomers() {
    const allowCreate = state.user.role === 'ADMIN';
    mount().innerHTML = `
      ${pageHeader('Customers', 'Customer profile records')}
      ${allowCreate ? `<div class="card-ui section-card mb-4"><div id="customerFormAlert"></div>${customerFormBody()}</div>` : ''}
      <div class="card-ui section-card">
        <h5 class="mb-3">Customer profiles</h5>
        ${window.Utils.table(['ID', 'Full Name', 'Email', 'Phone', 'User ID', 'Actions'], state.customers.map((c) => `<tr><td>${c.id ?? ''}</td><td>${window.Utils.escapeHtml(c.fullName)}</td><td>${window.Utils.escapeHtml(c.email)}</td><td>${window.Utils.escapeHtml(c.phone)}</td><td>${c.userId ?? ''}</td><td>${['ADMIN', 'EMPLOYEE'].includes(state.user.role) ? actionButtons([`<button class="btn btn-sm btn-outline-primary" onclick="DashboardApp.editCustomer(${c.id})">Update</button>`, `<button class="btn btn-sm btn-outline-danger" onclick="DashboardApp.deleteCustomer(${c.id})">Delete</button>`]) : ''}</td></tr>`).join(''))}
      </div>`;
    if (allowCreate) bindCustomerForm();
  }

  function customerFormBody() {
    return `<form id="customerCreateForm" class="row g-3">
      <div class="col-md-4"><label class="form-label">Full Name</label><input class="form-control" name="fullName" required></div>
      <div class="col-md-3"><label class="form-label">Email</label><input class="form-control" name="email" type="email" required></div>
      <div class="col-md-2"><label class="form-label">Phone</label><input class="form-control" name="phone" required></div>
      <div class="col-md-3"><label class="form-label">User ID</label><input class="form-control" name="userId" type="number" required></div>
      <div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Create Customer Profile</button></div>
    </form>`;
  }

  function bindCustomerForm() {
    document.getElementById('customerCreateForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = { fullName: fd.get('fullName'), email: fd.get('email'), phone: fd.get('phone'), userId: Number(fd.get('userId')) };
      try {
        await window.apiClient.post('/api/customer-profile/add', payload);
        window.Utils.showAlert('customerFormAlert', 'Customer profile created successfully.', 'success');
        await refresh();
        renderCustomers();
      } catch (error) {
        window.Utils.showAlert('customerFormAlert', getErrorMessage(error, 'Unable to create customer profile.'), 'danger');
      }
    });
  }

  function renderEmployees() {
    const allowCreate = state.user.role === 'ADMIN';
    mount().innerHTML = `
      ${pageHeader('Employees', 'Employee profile records')}
      ${allowCreate ? `<div class="card-ui section-card mb-4"><div id="employeeFormAlert"></div>${employeeFormBody()}</div>` : ''}
      <div class="card-ui section-card">
        <h5 class="mb-3">Employee profiles</h5>
        ${window.Utils.table(['ID', 'Full Name', 'Email', 'Phone', 'Department', 'User ID', 'Actions'], state.employees.map((c) => `<tr><td>${c.id ?? ''}</td><td>${window.Utils.escapeHtml(c.fullName)}</td><td>${window.Utils.escapeHtml(c.email)}</td><td>${window.Utils.escapeHtml(c.phone)}</td><td>${window.Utils.escapeHtml(c.department)}</td><td>${c.userId ?? ''}</td><td>${['ADMIN', 'EMPLOYEE'].includes(state.user.role) ? actionButtons([`<button class="btn btn-sm btn-outline-primary" onclick="DashboardApp.editEmployee(${c.id})">Update</button>`, `<button class="btn btn-sm btn-outline-danger" onclick="DashboardApp.deleteEmployee(${c.id})">Delete</button>`]) : ''}</td></tr>`).join(''))}
      </div>`;
    if (allowCreate) bindEmployeeForm();
  }

  function employeeFormBody() {
    return `<form id="employeeCreateForm" class="row g-3">
      <div class="col-md-3"><label class="form-label">Full Name</label><input class="form-control" name="fullName" required></div>
      <div class="col-md-3"><label class="form-label">Email</label><input class="form-control" name="email" type="email" required></div>
      <div class="col-md-2"><label class="form-label">Phone</label><input class="form-control" name="phone" required></div>
      <div class="col-md-2"><label class="form-label">Department</label><input class="form-control" name="department" required></div>
      <div class="col-md-2"><label class="form-label">User ID</label><input class="form-control" name="userId" type="number" required></div>
      <div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Create Employee Profile</button></div>
    </form>`;
  }

  function bindEmployeeForm() {
    document.getElementById('employeeCreateForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = { fullName: fd.get('fullName'), email: fd.get('email'), phone: fd.get('phone'), department: fd.get('department'), userId: Number(fd.get('userId')) };
      try {
        await window.apiClient.post('/api/employee-profile/add', payload);
        window.Utils.showAlert('employeeFormAlert', 'Employee profile created successfully.', 'success');
        await refresh();
        renderEmployees();
      } catch (error) {
        window.Utils.showAlert('employeeFormAlert', getErrorMessage(error, 'Unable to create employee profile.'), 'danger');
      }
    });
  }

  function renderAccounts() {
    const canCreate = ['ADMIN', 'EMPLOYEE'].includes(state.user.role);
    const canManage = ['ADMIN', 'EMPLOYEE'].includes(state.user.role);
    const rows = (state.user.role === 'CUSTOMER' ? state.currentCustomerAccounts : state.accounts)
      .map((a) => `<tr><td>${a.accountId ?? ''}</td><td>${window.Utils.escapeHtml(a.accountNumber)}</td><td>${window.Utils.escapeHtml(a.accountType)}</td><td>${window.Utils.toCurrency(a.balance)}</td><td>${window.Utils.escapeHtml(a.status)}</td><td>${a.customerProfileId ?? ''}</td><td>${canManage ? actionButtons([`<button class="btn btn-sm btn-outline-primary" onclick="DashboardApp.editAccount(${a.accountId})">Update</button>`, `<button class="btn btn-sm btn-outline-danger" onclick="DashboardApp.deleteAccount(${a.accountId})">Delete</button>`]) : ''}</td></tr>`).join('');
    mount().innerHTML = `
      ${pageHeader(state.user.role === 'CUSTOMER' ? 'My Accounts' : 'Accounts', 'Account records and account opening')}
      ${canCreate ? `<div class="card-ui section-card mb-4"><div id="accountFormAlert"></div>${accountFormBody()}</div>` : ''}
      <div class="card-ui section-card">
        <h5 class="mb-3">${state.user.role === 'CUSTOMER' ? 'Linked accounts' : 'All accounts'}</h5>
        ${window.Utils.table(['Account ID', 'Account Number', 'Type', 'Balance', 'Status', 'Customer Profile ID', 'Actions'], rows)}
      </div>`;
    if (canCreate) bindAccountForm();
  }

  function accountFormBody() {
    return `<form id="accountCreateForm" class="row g-3">
      <div class="col-md-3">
        <label class="form-label">Account ID</label>
        <input class="form-control" value="Auto Generated" readonly>
      </div>
      <div class="col-md-3">
        <label class="form-label">Account Type</label>
        <input class="form-control" name="accountType" placeholder="SAVINGS" required>
      </div>
      <div class="col-md-2">
        <label class="form-label">Opening Balance</label>
        <input class="form-control" name="balance" type="number" step="0.01" required>
      </div>
      <div class="col-md-2">
        <label class="form-label">Status</label>
        <input class="form-control" name="status" value="ACTIVE" required>
      </div>
      <div class="col-md-2">
        <label class="form-label">Customer Profile ID</label>
        <input class="form-control" name="customerProfileId" type="number" required>
      </div>
      <div class="col-12 d-flex justify-content-end">
        <button class="btn btn-primary" type="submit">Open Account</button>
      </div>
    </form>`;
  }

  function bindAccountForm() {
    document.getElementById('accountCreateForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = {
        accountType: fd.get('accountType'),
        balance: Number(fd.get('balance')),
        status: fd.get('status'),
        customerProfileId: Number(fd.get('customerProfileId'))
      };
      try {
        await window.apiClient.post('/api/v1/accounts/create', payload);
        window.Utils.showAlert('accountFormAlert', 'Account created successfully.', 'success');
        await refresh();
        renderAccounts();
        e.target.reset();
      } catch (error) {
        window.Utils.showAlert('accountFormAlert', getErrorMessage(error, 'Unable to create account.'), 'danger');
      }
    });
  }

  function renderMyBanking() {
    const profile = state.currentCustomerProfile;
    mount().innerHTML = `
      ${pageHeader('My Banking', 'Customer profile and account summary')}
      <div class="row g-4 mb-4">
        <div class="col-lg-5"><div class="card-ui section-card h-100">
          <h5 class="mb-3">My Profile</h5>
          ${profile ? `<div class="vstack gap-2"><div><strong>Name:</strong> ${window.Utils.escapeHtml(profile.fullName || state.user.username)}</div><div><strong>Email:</strong> ${window.Utils.escapeHtml(profile.email || state.user.username)}</div><div><strong>Phone:</strong> ${window.Utils.escapeHtml(profile.phone || '-')}</div><div><strong>Profile ID:</strong> ${profile.id ?? '-'}</div><div><strong>User ID:</strong> ${profile.userId ?? state.user.id}</div></div>` : `<div class="text-muted">Customer profile not found for this user.</div>`}
        </div></div>
        <div class="col-lg-7"><div class="card-ui section-card h-100">
          <h5 class="mb-3">My Accounts</h5>
          ${window.Utils.table(['Account ID', 'Account Number', 'Type', 'Balance', 'Status'], state.currentCustomerAccounts.map((a) => `<tr><td>${a.accountId ?? ''}</td><td>${window.Utils.escapeHtml(a.accountNumber)}</td><td>${window.Utils.escapeHtml(a.accountType)}</td><td>${window.Utils.toCurrency(a.balance)}</td><td>${window.Utils.escapeHtml(a.status)}</td></tr>`).join(''))}
        </div></div>
      </div>
      <div class="quick-grid">${quickCards()}</div>`;
  }

  function renderBeneficiaries() {
    const list = state.user.role === 'CUSTOMER' ? state.currentCustomerBeneficiaries : state.beneficiaries;
    const rows = list.map((b) => `<tr><td>${window.Utils.escapeHtml(b.beneficiaryName)}</td><td>${window.Utils.escapeHtml(b.accountNumber)}</td><td>${window.Utils.escapeHtml(b.bankName)}</td><td>${window.Utils.escapeHtml(b.branchName)}</td><td>${window.Utils.escapeHtml(b.ifscCode)}</td><td>${b.accountId ?? ''}</td></tr>`).join('');
    mount().innerHTML = `
      ${pageHeader('Beneficiaries', 'Add and review beneficiary records')}
      <div class="card-ui section-card mb-4"><div id="beneficiaryFormAlert"></div>${beneficiaryFormBody()}</div>
      <div class="card-ui section-card">
        <div class="d-flex justify-content-between align-items-center mb-3">
          <h5 class="mb-0">Beneficiary list</h5>
          <span class="text-muted small">Delete action is hidden because the backend does not return beneficiary ID.</span>
        </div>
        ${window.Utils.table(['Name', 'Account Number', 'Bank', 'Branch', 'IFSC', 'Account ID'], rows)}
      </div>`;
    bindBeneficiaryForm();
  }

  function beneficiaryFormBody() {
    return `<form id="beneficiaryForm" class="row g-3">
      <div class="col-md-3"><label class="form-label">Beneficiary Name</label><input class="form-control" name="beneficiaryName" required></div>
      <div class="col-md-3"><label class="form-label">Account Number</label><input class="form-control" name="accountNumber" required></div>
      <div class="col-md-2"><label class="form-label">Bank Name</label><input class="form-control" name="bankName" required></div>
      <div class="col-md-2"><label class="form-label">Branch</label><input class="form-control" name="branchName" required></div>
      <div class="col-md-2"><label class="form-label">IFSC</label><input class="form-control" name="ifscCode" required></div>
      <div class="col-md-3">${state.user.role === 'CUSTOMER' ? `<label class="form-label">Your Account</label><select class="form-select" name="accountId" required>${customerAccountOptions()}</select>` : `<label class="form-label">Your Account ID</label><input class="form-control" name="accountId" type="number" required>`}</div>
      <div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Add Beneficiary</button></div>
    </form>`;
  }

  function bindBeneficiaryForm() {
    document.getElementById('beneficiaryForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = Object.fromEntries(fd.entries());
      payload.accountId = Number(payload.accountId);
      if (!ensureCustomerOwnsAccount(payload.accountId)) {
        window.Utils.showAlert('beneficiaryFormAlert', 'You can only use your own account in customer dashboard.', 'danger');
        return;
      }
      try {
        await window.apiClient.post('/api/v1/beneficiaries/add', payload);
        window.Utils.showAlert('beneficiaryFormAlert', 'Beneficiary added successfully.', 'success');
        await refresh();
        renderBeneficiaries();
      } catch (error) {
        window.Utils.showAlert('beneficiaryFormAlert', getErrorMessage(error, 'Unable to add beneficiary.'), 'danger');
      }
    });
  }

  function renderTransactions() {
    mount().innerHTML = `
      ${pageHeader('Transactions', 'Deposit, withdraw, transfer, balance, and history')}
      <div class="row g-4 mb-4">
        <div class="col-lg-4"><div class="card-ui section-card"><div id="depositAlert"></div>${transactionForm('deposit', 'Deposit')}</div></div>
        <div class="col-lg-4"><div class="card-ui section-card"><div id="withdrawAlert"></div>${transactionForm('withdraw', 'Withdraw')}</div></div>
        <div class="col-lg-4"><div class="card-ui section-card"><div id="transferAlert"></div>${transactionForm('transfer', 'Transfer')}</div></div>
      </div>
      <div class="row g-4">
        <div class="col-lg-4"><div class="card-ui section-card"><div id="balanceAlert"></div>${balanceForm()}</div></div>
        <div class="col-lg-8"><div class="card-ui section-card"><div id="historyAlert"></div>${historyForm()}<div id="historyTable" class="mt-3"></div></div></div>
      </div>`;
    bindTransactionForms();
  }

  function transactionForm(type, title) {
    return `<h5 class="mb-3">${title}</h5><form class="txn-form row g-3" data-type="${type}">
      <div class="col-12">${customerAccountField('accountId', 'Account ID')}</div>
      <div class="col-12"><label class="form-label">Amount</label><input class="form-control" name="amount" type="number" step="0.01" required></div>
      <div class="col-12 d-grid"><button class="btn btn-primary" type="submit">${title}</button></div>
    </form>`;
  }

  function balanceForm() {
    return `<h5 class="mb-3">Check Balance</h5><form id="balanceForm" class="row g-3"><div class="col-12">${customerAccountField('accountId', 'Account ID')}</div><div class="col-12 d-grid"><button class="btn btn-outline-primary" type="submit">Fetch Balance</button></div><div class="col-12"><div id="balanceResult" class="fw-semibold"></div></div></form>`;
  }

  function historyForm() {
    return `<h5 class="mb-3">Transaction History</h5><form id="historyForm" class="row g-3"><div class="col-md-6">${customerAccountField('accountId', 'Account ID')}</div><div class="col-md-6 d-flex align-items-end"><button class="btn btn-outline-primary w-100" type="submit">Load History</button></div></form>`;
  }

  function bindTransactionForms() {
    document.querySelectorAll('.txn-form').forEach((form) => {
      form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const type = form.dataset.type;
        const fd = new FormData(form);
        const prefixes = { deposit: 'DEP', withdraw: 'WDR', transfer: 'TXN' };
        const payload = {
          accountId: Number(fd.get('accountId')),
          amount: Number(fd.get('amount')),
          transactionType: type.toUpperCase(),
          referenceNumber: `${prefixes[type] || 'TXN'}-${Date.now()}`
        };
        if (!ensureCustomerOwnsAccount(payload.accountId)) {
          window.Utils.showAlert(`${type}Alert`, 'You can use only your own account in customer dashboard.', 'danger');
          return;
        }
        try {
          await window.apiClient.post(`/api/transactions/${type}`, payload);
          window.Utils.showAlert(`${type}Alert`, `${type[0].toUpperCase() + type.slice(1)} successful.`, 'success');
          await refresh();
          renderTransactions();
        } catch (error) {
          window.Utils.showAlert(`${type}Alert`, getErrorMessage(error, `Unable to ${type}.`), 'danger');
        }
      });
    });

    document.getElementById('balanceForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const accountId = new FormData(e.target).get('accountId');
      if (!ensureCustomerOwnsAccount(accountId)) {
        window.Utils.showAlert('balanceAlert', 'You can check only your own account balance.', 'danger');
        return;
      }
      try {
        const balance = (await window.apiClient.get(`/api/transactions/balance/${accountId}`)).data;
        document.getElementById('balanceResult').textContent = `Balance: ${window.Utils.toCurrency(balance)}`;
      } catch (error) {
        window.Utils.showAlert('balanceAlert', getErrorMessage(error, 'Unable to fetch balance.'), 'danger');
      }
    });

    document.getElementById('historyForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const accountId = new FormData(e.target).get('accountId');
      if (!ensureCustomerOwnsAccount(accountId)) {
        window.Utils.showAlert('historyAlert', 'You can view history only for your own account.', 'danger');
        return;
      }
      try {
        const data = (await window.apiClient.get(`/api/transactions/${accountId}`)).data;
        document.getElementById('historyTable').innerHTML = window.Utils.table(['Transaction ID', 'Type', 'Amount', 'Date', 'Reference', 'Account ID'], data.map((t) => `<tr><td>${t.transactionId ?? ''}</td><td>${window.Utils.escapeHtml(t.transactionType)}</td><td>${window.Utils.toCurrency(t.amount)}</td><td>${window.Utils.formatDateTime(t.transactionDate)}</td><td>${window.Utils.escapeHtml(t.referenceNumber)}</td><td>${t.accountId ?? ''}</td></tr>`).join(''));
      } catch (error) {
        window.Utils.showAlert('historyAlert', getErrorMessage(error, 'Unable to load transaction history.'), 'danger');
      }
    });
  }

  function renderStatements() {
    mount().innerHTML = `
      ${pageHeader('Statements', 'Generate statements by account and date range')}
      <div class="card-ui section-card"><div id="statementAlert"></div>
        <form id="statementForm" class="row g-3 mb-4">
          <div class="col-md-4">${customerAccountField('accountId', 'Account ID')}</div>
          <div class="col-md-4"><label class="form-label">Start Date</label><input class="form-control" name="startDate" type="datetime-local" required></div>
          <div class="col-md-4"><label class="form-label">End Date</label><input class="form-control" name="endDate" type="datetime-local" required></div>
          <div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Generate Statement</button></div>
        </form>
        <div id="statementResult"></div>
      </div>`;
    document.getElementById('statementForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = { accountId: Number(fd.get('accountId')), startDate: fd.get('startDate'), endDate: fd.get('endDate') };
      if (!ensureCustomerOwnsAccount(payload.accountId)) {
        window.Utils.showAlert('statementAlert', 'You can generate statements only for your own account.', 'danger');
        return;
      }
      try {
        const data = (await window.apiClient.post('/api/statements/generate', payload)).data;
        document.getElementById('statementResult').innerHTML = window.Utils.table(['Transaction ID', 'Type', 'Amount', 'Date', 'Reference'], data.map((t) => `<tr><td>${t.transactionId ?? ''}</td><td>${window.Utils.escapeHtml(t.transactionType)}</td><td>${window.Utils.toCurrency(t.amount)}</td><td>${window.Utils.formatDateTime(t.transactionDate)}</td><td>${window.Utils.escapeHtml(t.referenceNumber)}</td></tr>`).join(''));
      } catch (error) {
        window.Utils.showAlert('statementAlert', getErrorMessage(error, 'Unable to generate statement.'), 'danger');
      }
    });
  }

  function renderLoans() {
    const role = state.user.role;
    mount().innerHTML = `
      ${pageHeader('Loans', role === 'CUSTOMER' ? 'Apply for loans and review options' : 'Review and manage loan applications')}
      <div class="row g-4 mb-4">
        <div class="col-lg-5">
          <div class="card-ui section-card">
            <h5 class="mb-3">Available Loans</h5>
            ${window.Utils.table(
              ['Loan ID', 'Loan Type', 'Interest Rate', 'Min Amount', 'Max Amount', 'Tenure'],
              state.loans.map((l) => `
                <tr>
                  <td>${l.id ?? ''}</td>
                  <td>${window.Utils.escapeHtml(l.loanType || '')}</td>
                  <td>${window.Utils.escapeHtml(l.interestRate ?? '')}</td>
                  <td>${window.Utils.escapeHtml(l.minAmount ?? '')}</td>
                  <td>${window.Utils.escapeHtml(l.maxAmount ?? '')}</td>
                  <td>${window.Utils.escapeHtml(l.tenure ?? '')}</td>
                </tr>
              `).join('')
            )}
          </div>
        </div>
        <div class="col-lg-7"><div class="card-ui section-card"><div id="loanAlert"></div>${loanForm(role)}</div></div>
      </div>
      <div class="card-ui section-card">
        <h5 class="mb-3">Loan Applications</h5>
        ${role === 'CUSTOMER'
          ? '<div class="empty-state">Your backend does not store loan applications against a customer login, so only loan apply is shown here.</div>'
          : window.Utils.table(['Application ID', 'Loan ID', 'Amount', 'Purpose', 'Status', 'Actions'], state.loanApplications.map((a) => `<tr><td>${a.applicationId ?? a.id ?? ''}</td><td>${a.loanId ?? ''}</td><td>${window.Utils.toCurrency(a.amount)}</td><td>${window.Utils.escapeHtml(a.purpose)}</td><td>${window.Utils.statusBadge(a.status)}</td><td><div class="d-flex gap-2"><button class="btn btn-sm btn-success" onclick="DashboardApp.approveLoan(${a.applicationId ?? a.id})">Approve</button><button class="btn btn-sm btn-danger" onclick="DashboardApp.rejectLoan(${a.applicationId ?? a.id})">Reject</button></div></td></tr>`).join(''))}
      </div>`;
    bindLoanForm(role);
  }

  function loanForm(role) {
    if (role !== 'CUSTOMER') {
      return '<div class="empty-state">Employees and admins can review loan applications using the table below.</div>';
    }
    return `<h5 class="mb-3">Apply Loan</h5><form id="loanForm" class="row g-3"><div class="col-md-4"><label class="form-label">Loan ID</label><input class="form-control" name="loanId" type="number" required></div><div class="col-md-4"><label class="form-label">Amount</label><input class="form-control" name="amount" type="number" step="0.01" required></div><div class="col-md-4"><label class="form-label">Purpose</label><input class="form-control" name="purpose" required></div><div class="col-12 d-flex justify-content-end"><button class="btn btn-primary" type="submit">Apply Loan</button></div></form>`;
  }

  function bindLoanForm(role) {
    if (role !== 'CUSTOMER') return;
    document.getElementById('loanForm')?.addEventListener('submit', async (e) => {
      e.preventDefault();
      const fd = new FormData(e.target);
      const payload = { loanId: Number(fd.get('loanId')), amount: Number(fd.get('amount')), purpose: fd.get('purpose') };
      try {
        await window.apiClient.post('/api/loan-applications/apply', payload);
        window.Utils.showAlert('loanAlert', 'Loan application submitted.', 'success');
        await refresh();
        renderLoans();
      } catch (error) {
        window.Utils.showAlert('loanAlert', getErrorMessage(error, 'Unable to apply for loan.'), 'danger');
      }
    });
  }

  async function deactivateUser(id) {
    await confirmAction('Deactivate this user?', () => window.apiClient.put(`/api/users/deactivate/${id}`), 'User deactivated successfully.', 'Unable to deactivate user.');
  }

  async function deleteUser(id) {
    await confirmAction('Delete this user permanently?', () => window.apiClient.delete(`/api/users/delete/${id}`), 'User deleted successfully.', 'Unable to delete user.');
  }

  function editCustomer(id) {
    const customer = state.customers.find((item) => Number(item.id) === Number(id));
    if (!customer) return;
    openEditModal('Update Customer Profile', [
      { name: 'fullName', label: 'Full Name' },
      { name: 'email', label: 'Email', type: 'email' },
      { name: 'phone', label: 'Phone' },
      { name: 'userId', label: 'User ID', type: 'number' }
    ], customer, async (fd) => {
      const payload = { fullName: fd.get('fullName'), email: fd.get('email'), phone: fd.get('phone'), userId: Number(fd.get('userId')) };
      try {
        await window.apiClient.put(`/api/customer-profile/update/${id}`, payload);
        closeEditModal();
        await refresh();
        renderCustomers();
        window.Utils.showGlobal('Customer updated successfully.', 'success');
      } catch (error) {
        window.Utils.showAlert('editEntityAlert', getErrorMessage(error, 'Unable to update customer.'), 'danger');
      }
    });
  }

  async function deleteCustomer(id) {
    await confirmAction('Delete this customer profile?', () => window.apiClient.delete(`/api/customer-profile/delete/${id}`), 'Customer profile deleted successfully.', 'Unable to delete customer profile.');
  }

  function editEmployee(id) {
    const employee = state.employees.find((item) => Number(item.id) === Number(id));
    if (!employee) return;
    openEditModal('Update Employee Profile', [
      { name: 'fullName', label: 'Full Name' },
      { name: 'email', label: 'Email', type: 'email' },
      { name: 'phone', label: 'Phone' },
      { name: 'department', label: 'Department' },
      { name: 'userId', label: 'User ID', type: 'number' }
    ], employee, async (fd) => {
      const payload = { fullName: fd.get('fullName'), email: fd.get('email'), phone: fd.get('phone'), department: fd.get('department'), userId: Number(fd.get('userId')) };
      try {
        await window.apiClient.put(`/api/employee-profile/update/${id}`, payload);
        closeEditModal();
        await refresh();
        renderEmployees();
        window.Utils.showGlobal('Employee updated successfully.', 'success');
      } catch (error) {
        window.Utils.showAlert('editEntityAlert', getErrorMessage(error, 'Unable to update employee.'), 'danger');
      }
    });
  }

  async function deleteEmployee(id) {
    await confirmAction('Delete this employee profile?', () => window.apiClient.delete(`/api/employee-profile/delete/${id}`), 'Employee profile deleted successfully.', 'Unable to delete employee profile.');
  }

  function editAccount(id) {
    const list = state.user.role === 'CUSTOMER' ? state.currentCustomerAccounts : state.accounts;
    const account = list.find((item) => Number(item.accountId) === Number(id));
    if (!account) return;
    openEditModal('Update Account', [
      { name: 'accountId', label: 'Account ID', type: 'number', readonly: true },
      { name: 'accountType', label: 'Account Type' },
      { name: 'balance', label: 'Balance', type: 'number' },
      { name: 'status', label: 'Status' },
      { name: 'customerProfileId', label: 'Customer Profile ID', type: 'number' }
    ], account, async (fd) => {
      const payload = {
        accountId: Number(fd.get('accountId')),
        accountType: fd.get('accountType'),
        balance: Number(fd.get('balance')),
        status: fd.get('status'),
        customerProfileId: Number(fd.get('customerProfileId')),
        accountNumber: account.accountNumber
      };
      try {
        await window.apiClient.put(`/api/v1/accounts/update/${id}`, payload);
        closeEditModal();
        await refresh();
        renderAccounts();
        window.Utils.showGlobal('Account updated successfully.', 'success');
      } catch (error) {
        window.Utils.showAlert('editEntityAlert', getErrorMessage(error, 'Unable to update account.'), 'danger');
      }
    });
  }

  async function deleteAccount(id) {
    await confirmAction('Delete this account?', () => window.apiClient.delete(`/api/v1/accounts/delete/${id}`), 'Account deleted successfully.', 'Unable to delete account.');
  }

  async function approveLoan(id) {
    try {
      await window.apiClient.put(`/api/loan-applications/${id}/approve`);
      await refresh();
      renderLoans();
      window.Utils.showGlobal('Loan approved successfully.', 'success');
    } catch (error) {
      window.Utils.showGlobal(getErrorMessage(error, 'Unable to approve loan.'), 'danger');
    }
  }

  async function rejectLoan(id) {
    try {
      await window.apiClient.put(`/api/loan-applications/${id}/reject`);
      await refresh();
      renderLoans();
      window.Utils.showGlobal(getErrorMessage(null, 'Loan rejected successfully.'), 'success');
    } catch (error) {
      window.Utils.showGlobal(getErrorMessage(error, 'Unable to reject loan.'), 'danger');
    }
  }

  async function refresh() {
    await loadSharedData();
    await loadRoleData();
  }

  function getAllowedDefault(role) {
    return role === 'CUSTOMER' ? '#my-banking' : '#overview';
  }

  function route() {
    const role = state.user.role;
    const hash = window.location.hash || getAllowedDefault(role);
    const allowed = role === 'ADMIN'
      ? ['#overview', '#users', '#customers', '#employees', '#accounts', '#beneficiaries', '#transactions', '#statements', '#loans']
      : role === 'EMPLOYEE'
      ? ['#overview', '#users', '#customers', '#employees', '#accounts', '#beneficiaries', '#transactions', '#statements', '#loans']
      : ['#my-banking', '#accounts', '#beneficiaries', '#transactions', '#statements', '#loans'];

    if (!allowed.includes(hash)) {
      window.location.hash = getAllowedDefault(role);
      return;
    }

    if (hash === '#overview') renderOverview();
    if (hash === '#users') renderUsers();
    if (hash === '#customers') renderCustomers();
    if (hash === '#employees') renderEmployees();
    if (hash === '#accounts') renderAccounts();
    if (hash === '#my-banking') renderMyBanking();
    if (hash === '#beneficiaries') renderBeneficiaries();
    if (hash === '#transactions') renderTransactions();
    if (hash === '#statements') renderStatements();
    if (hash === '#loans') renderLoans();
  }

  async function init() {
    state.session = window.Shell.init();
    if (!state.session) return;
    state.user = state.session.user;
    await refresh();
    route();
    window.addEventListener('hashchange', route);
  }

  return {
    init,
    approveLoan,
    rejectLoan,
    deactivateUser,
    deleteUser,
    editCustomer,
    deleteCustomer,
    editEmployee,
    deleteEmployee,
    editAccount,
    deleteAccount
  };
})();

document.addEventListener('DOMContentLoaded', () => {
  if (document.getElementById('pageMount')) {
    window.DashboardApp.init();
  }
});