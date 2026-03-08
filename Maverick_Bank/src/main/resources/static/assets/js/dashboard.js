document.addEventListener('DOMContentLoaded', async () => {
  try {
    const [usersRes, customersRes, employeesRes] = await Promise.all([
      window.userService.getAllUsers(),
      window.customerService.getAllCustomers(),
      window.employeeService.getAllEmployees()
    ]);

    const users = usersRes.data || [];
    const customers = users.filter(user => user.role === "CUSTOMER")
	const employeeUsers = users.filter(user => user.role === "EMPLOYEE");

    document.getElementById('usersCount').textContent = users.length;
    document.getElementById('customersCount').textContent = customers.length;
    document.getElementById('employeesCount').textContent = employeeUsers.length;

    const recentBody = document.getElementById('recentUsersBody');
    recentBody.innerHTML = users.slice(0, 5).map(user => `
      <tr>
        <td>${user.id}</td>
        <td>${escapeHtml(user.username)}</td>
        <td>${escapeHtml(user.role)}</td>
        <td>${renderActiveBadge(user.active)}</td>
      </tr>
    `).join('') || '<tr><td colspan="4" class="text-center text-muted">No users found</td></tr>';
  } catch (error) {
    setAlert('Unable to load dashboard data. Check backend URL, CORS, and admin credentials.', 'danger');
  }
});
