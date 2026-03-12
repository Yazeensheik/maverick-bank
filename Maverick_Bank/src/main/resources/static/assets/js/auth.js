window.Auth = (() => {
  function buildToken(username, password) {
    return 'Basic ' + btoa(`${username}:${password}`);
  }

  function saveSession(session) {
    localStorage.setItem(window.MB_CONFIG.STORAGE_KEY, JSON.stringify(session));
  }

  function getSession() {
    return JSON.parse(localStorage.getItem(window.MB_CONFIG.STORAGE_KEY) || 'null');
  }

  function clearSession() {
    localStorage.removeItem(window.MB_CONFIG.STORAGE_KEY);
  }

  function getDefaultRoute(role) {
    if (role === 'ADMIN') return '#overview';
    if (role === 'EMPLOYEE') return '#overview';
    return '#my-banking';
  }

  function goDashboard(role) {
    window.location.href = `/dashboard.html${getDefaultRoute(role)}`;
  }

  async function login(username, password) {
    const response = await window.apiClient.post('/api/users/login', { username, password });
    const user = response.data;
    const role = user?.role?.name || user?.role || 'CUSTOMER';
    const session = {
      token: buildToken(username, password),
      username,
      password,
      user: {
        id: user.id,
        username: user.username,
        active: user.active,
        role,
        customerProfile: user.customerProfile || null,
        employeeProfile: user.employeeProfile || null,
        raw: user
      }
    };
    saveSession(session);
    return session;
  }

  function requireAuth() {
    const session = getSession();
    if (!session?.token || !session?.user?.role) {
      window.location.href = '/login.html';
      return null;
    }
    return session;
  }

  function logout() {
    clearSession();
    window.location.href = '/login.html';
  }

  return { login, getSession, saveSession, clearSession, requireAuth, logout, getDefaultRoute, goDashboard };
})();

window.logout = window.Auth.logout;

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('loginForm');
  const toggle = document.getElementById('togglePassword');
  const password = document.getElementById('password');

  if (toggle && password) {
    toggle.addEventListener('click', () => {
      const isPassword = password.type === 'password';
      password.type = isPassword ? 'text' : 'password';
      toggle.innerHTML = `<i class="bi ${isPassword ? 'bi-eye-slash' : 'bi-eye'}"></i>`;
    });
  }

  if (!form) return;

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value.trim();
    const pwd = document.getElementById('password').value;
    const btn = document.getElementById('loginBtn');

    try {
      btn.disabled = true;
      btn.textContent = 'Signing in...';
      const session = await window.Auth.login(username, pwd);
      window.Auth.goDashboard(session.user.role);
    } catch (error) {
      window.Auth.clearSession();
      window.Utils.showAlert('authAlert', 'Login failed. Check email, password, or backend access.', 'danger');
    } finally {
      btn.disabled = false;
      btn.textContent = 'Login';
    }
  });
});
