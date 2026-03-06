async function loadUsersTable() {
  const body = document.getElementById('usersTableBody');
  if (!body) return;

  try {
    const res = await window.userService.getAllUsers();
    const term = (document.getElementById('userSearch')?.value || '').toLowerCase();
    const users = (res.data || []).filter(u => !term || `${u.username} ${u.role}`.toLowerCase().includes(term));

    body.innerHTML = users.map(user => `
      <tr>
        <td>${user.id}</td>
        <td>${escapeHtml(user.username)}</td>
        <td>${escapeHtml(user.role)}</td>
        <td>${renderActiveBadge(user.active)}</td>
        <td class="d-flex gap-2 flex-wrap">
          <button class="btn btn-sm btn-outline-warning" onclick="deactivateUserAction(${user.id})">Deactivate</button>
          <button class="btn btn-sm btn-outline-danger" onclick="deleteUserAction(${user.id})">Delete</button>
        </td>
      </tr>
    `).join('') || '<tr><td colspan="5" class="text-center text-muted">No users found</td></tr>';
  } catch (error) {
    setAlert('Unable to load users.', 'danger');
  }
}

async function deactivateUserAction(id) {
  if (!confirm(`Deactivate user #${id}?`)) return;
  try {
    await window.userService.deactivateUser(id);
    setAlert('User deactivated successfully.');
    loadUsersTable();
  } catch (error) {
    setAlert(error?.response?.data?.message || 'Unable to deactivate user.', 'danger');
  }
}

async function deleteUserAction(id) {
  if (!confirm(`Delete user #${id}?`)) return;
  try {
    await window.userService.deleteUser(id);
    setAlert('User deleted successfully.');
    loadUsersTable();
  } catch (error) {
    setAlert(error?.response?.data?.message || 'Unable to delete user.', 'danger');
  }
}

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('createUserForm');
  if (form) {
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const payload = {
        email: document.getElementById('userEmail').value.trim(),
        password: document.getElementById('userPassword').value
      };
      const role = document.getElementById('userRole').value;

      try {
        const res = role === 'EMPLOYEE'
          ? await window.userService.createEmployeeUser(payload)
          : await window.userService.createCustomerUser(payload);
        setAlert(`User created successfully with ID ${res.data.id} and role ${res.data.role}.`);
        form.reset();
      } catch (error) {
        setAlert(error?.response?.data?.message || 'Unable to create user.', 'danger');
      }
    });
  }

  if (document.getElementById('usersTableBody')) {
    loadUsersTable();
    document.getElementById('userSearch')?.addEventListener('input', loadUsersTable);
  }
});
