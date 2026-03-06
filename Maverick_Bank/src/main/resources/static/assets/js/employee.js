async function loadEmployeesTable() {
  const body = document.getElementById('employeesTableBody');
  if (!body) return;

  try {
    const res = await window.employeeService.getAllEmployees();
    const term = (document.getElementById('employeeSearch')?.value || '').toLowerCase();
    const employees = (res.data || []).filter(e => !term || `${e.fullName} ${e.email} ${e.department}`.toLowerCase().includes(term));

    body.innerHTML = employees.map(employee => `
      <tr>
        <td>${employee.id}</td>
        <td>${escapeHtml(employee.fullName)}</td>
        <td>${escapeHtml(employee.email)}</td>
        <td>${escapeHtml(employee.phone)}</td>
        <td>${escapeHtml(employee.department)}</td>
        <td>${employee.userId}</td>
        <td><button class="btn btn-sm btn-outline-danger" onclick="deleteEmployeeAction(${employee.id})">Delete</button></td>
      </tr>
    `).join('') || '<tr><td colspan="7" class="text-center text-muted">No employees found</td></tr>';
  } catch (error) {
    setAlert('Unable to load employees.', 'danger');
  }
}

async function deleteEmployeeAction(id) {
  if (!confirm(`Delete employee profile #${id}?`)) return;
  try {
    await window.employeeService.deleteEmployee(id);
    setAlert('Employee profile deleted successfully.');
    loadEmployeesTable();
  } catch (error) {
    setAlert(error?.response?.data?.message || 'Unable to delete employee profile.', 'danger');
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('createEmployeeForm');
  if (form) {
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const payload = {
        fullName: document.getElementById('employeeFullName').value.trim(),
        department: document.getElementById('employeeDepartment').value.trim(),
        email: document.getElementById('employeeEmail').value.trim(),
        phone: document.getElementById('employeePhone').value.trim(),
        userId: Number(document.getElementById('employeeUserId').value)
      };

      try {
        const res = await window.employeeService.createEmployee(payload);
        setAlert(`Employee profile created successfully with ID ${res.data.id}.`);
        form.reset();
      } catch (error) {
        setAlert(error?.response?.data?.message || 'Unable to create employee profile.', 'danger');
      }
    });
  }

  if (document.getElementById('employeesTableBody')) {
    loadEmployeesTable();
    document.getElementById('employeeSearch')?.addEventListener('input', loadEmployeesTable);
  }
});
