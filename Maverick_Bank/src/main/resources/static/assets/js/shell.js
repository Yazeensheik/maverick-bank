window.Shell = (() => {
  const menus = {
    ADMIN: [
      { section: 'Dashboard', items: [{ hash: '#overview', icon: 'bi-grid', label: 'Overview' }] },
      { section: 'Management', items: [
        { hash: '#users', icon: 'bi-people', label: 'Users' },
        { hash: '#customers', icon: 'bi-person-vcard', label: 'Customers' },
        { hash: '#employees', icon: 'bi-person-workspace', label: 'Employees' },
        { hash: '#accounts', icon: 'bi-wallet2', label: 'Accounts' }
      ]},
      { section: 'Operations', items: [
        { hash: '#beneficiaries', icon: 'bi-person-lines-fill', label: 'Beneficiaries' },
        { hash: '#transactions', icon: 'bi-arrow-left-right', label: 'Transactions' },
        { hash: '#statements', icon: 'bi-file-earmark-text', label: 'Statements' },
        { hash: '#loans', icon: 'bi-cash-coin', label: 'Loans' }
      ]}
    ],
    EMPLOYEE: [
      { section: 'Dashboard', items: [{ hash: '#overview', icon: 'bi-grid', label: 'Overview' }] },
      { section: 'Management', items: [
        { hash: '#users', icon: 'bi-people', label: 'Users' },
        { hash: '#customers', icon: 'bi-person-vcard', label: 'Customers' },
        { hash: '#employees', icon: 'bi-person-workspace', label: 'Employees' },
        { hash: '#accounts', icon: 'bi-wallet2', label: 'Accounts' }
      ]},
      { section: 'Operations', items: [
        { hash: '#beneficiaries', icon: 'bi-person-lines-fill', label: 'Beneficiaries' },
        { hash: '#transactions', icon: 'bi-arrow-left-right', label: 'Transactions' },
        { hash: '#statements', icon: 'bi-file-earmark-text', label: 'Statements' },
        { hash: '#loans', icon: 'bi-cash-coin', label: 'Loans' }
      ]}
    ],
    CUSTOMER: [
      { section: 'Dashboard', items: [
        { hash: '#my-banking', icon: 'bi-grid', label: 'My Banking' },
        { hash: '#beneficiaries', icon: 'bi-person-lines-fill', label: 'Beneficiaries' },
        { hash: '#transactions', icon: 'bi-arrow-left-right', label: 'Transactions' },
        { hash: '#statements', icon: 'bi-file-earmark-text', label: 'Statements' },
        { hash: '#loans', icon: 'bi-cash-coin', label: 'Loans' }
      ]}
    ]
  };

  function buildSidebar(role) {
    const session = window.Auth.getSession();
    const groups = menus[role] || [];
    return `
      <div class="sidebar-brand">
        <span class="brand-logo">MB</span>
        <div>
          <div class="fw-semibold text-white">Maverick Bank</div>
          <div class="small text-secondary-emphasis">${role} Panel</div>
        </div>
      </div>
      ${groups.map((group) => `
        <div class="sidebar-section-title">${group.section}</div>
        ${group.items.map((item) => `<a class="sidebar-link" href="${item.hash}" data-hash="${item.hash}"><i class="bi ${item.icon}"></i><span>${item.label}</span></a>`).join('')}
      `).join('')}
      <div class="mt-4 px-2">
        <button class="btn btn-outline-light w-100" onclick="logout()">Logout</button>
      </div>
    `;
  }

  function buildTopbar(role) {
    const session = window.Auth.getSession();
    return `
      <div class="topbar-inner">
        <div>
          <div class="small text-muted">Role based banking dashboard</div>
          <div class="fw-semibold">${role} Workspace</div>
        </div>
        <div class="user-pill">
          <i class="bi bi-person-circle"></i>
          <div>
            <div class="fw-semibold">${window.Utils.escapeHtml(session?.user?.username || '')}</div>
            <div class="small text-muted">${role}</div>
          </div>
        </div>
      </div>
    `;
  }

  function syncActiveLinks() {
    const hash = window.location.hash || '#overview';
    document.querySelectorAll('.sidebar-link').forEach((link) => {
      link.classList.toggle('active', link.dataset.hash === hash);
    });
  }

  function init() {
    const session = window.Auth.requireAuth();
    if (!session) return null;
    const role = session.user.role;
    const sidebar = document.getElementById('sidebar');
    const topbar = document.getElementById('topbar');
    if (sidebar) sidebar.innerHTML = buildSidebar(role);
    if (topbar) topbar.innerHTML = buildTopbar(role);
    syncActiveLinks();
    window.addEventListener('hashchange', syncActiveLinks);
    return session;
  }

  return { init };
})();
