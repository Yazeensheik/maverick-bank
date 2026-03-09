async function loadCustomersTable() {
  const body = document.getElementById('customersTableBody');
  if (!body) return;

  try {
    const res = await window.customerService.getAllCustomers();
    const term = (document.getElementById('customerSearch')?.value || '').toLowerCase();
    const customers = (res.data || []).filter(c => !term || `${c.fullName} ${c.email}`.toLowerCase().includes(term));

    body.innerHTML = customers.map(customer => `
      <tr>
        <td>${customer.id}</td>
        <td>${escapeHtml(customer.fullName)}</td>
        <td>${escapeHtml(customer.email)}</td>
        <td>${escapeHtml(customer.phone)}</td>
        <td>${customer.userId}</td>
        <td><button class="btn btn-sm btn-outline-danger" onclick="deleteCustomerAction(${customer.id})">Delete</button></td>
      </tr>
    `).join('') || '<tr><td colspan="6" class="text-center text-muted">No customers found</td></tr>';
  } catch (error) {
    setAlert('Unable to load customers.', 'danger');
  }
}

async function deleteCustomerAction(id) {
  if (!confirm(`Delete customer profile #${id}?`)) return;
  try {
    await window.customerService.deleteCustomer(id);
    setAlert('Customer profile deleted successfully.');
    loadCustomersTable();
  } catch (error) {
    setAlert(error?.response?.data?.message || 'Unable to delete customer profile.', 'danger');
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('createCustomerForm');
  if (form) {
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const payload = {
        fullName: document.getElementById('customerFullName').value.trim(),
        email: document.getElementById('customerEmail').value.trim(),
        phone: document.getElementById('customerPhone').value.trim(),
        userId: Number(document.getElementById('customerUserId').value)
      };

      try {
        const res = await window.customerService.createCustomer(payload);
        setAlert(`Customer profile created successfully with ID ${res.data.id}.`);
        form.reset();
      } catch (error) {
        setAlert(error?.response?.data?.message || 'Unable to create customer profile.', 'danger');
      }
    });
  }

  if (document.getElementById('customersTableBody')) {
    loadCustomersTable();
    document.getElementById('customerSearch')?.addEventListener('input', loadCustomersTable);
  }
});
