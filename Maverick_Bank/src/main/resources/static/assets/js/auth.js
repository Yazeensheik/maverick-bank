function saveAuth(email, password) {
  const token = 'Basic ' + btoa(`${email}:${password}`);
  localStorage.setItem(window.MB_CONFIG.STORAGE_KEY, JSON.stringify({ token, username: email }));
}

function getAuth() {
  return JSON.parse(localStorage.getItem(window.MB_CONFIG.STORAGE_KEY) || 'null');
}

function clearAuth() {
  localStorage.removeItem(window.MB_CONFIG.STORAGE_KEY);
}

async function verifyLogin() {
  await window.apiClient.get('/api/users/get/all');
}

function logout() {
  clearAuth();
  window.location.href = '/login.html';
}

function requireAuth() {
  if (!getAuth()) {
    window.location.href = '/login.html';
  }
}

window.apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if ([401, 403].includes(error?.response?.status)) {
      if (!window.location.pathname.endsWith('/login.html')) {
        clearAuth();
        window.location.href = '/login.html';
      }
    }
    return Promise.reject(error);
  }
);

const loginForm = document.getElementById('loginForm');
if (loginForm) {
  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;
    const btn = document.getElementById('loginBtn');

    try {
      btn.disabled = true;
      btn.textContent = 'Signing in...';
      saveAuth(email, password);
      await verifyLogin();
      window.location.href = '/dashboard.html';
    } catch (error) {
      clearAuth();
      const el = document.getElementById('loginAlert');
      if (el) {
        el.innerHTML = '<div class="alert alert-danger">Login failed. Use valid ADMIN credentials from your backend seeder/database.</div>';
      }
    } finally {
      btn.disabled = false;
      btn.textContent = 'Login';
    }
  });
}

if (!window.location.pathname.endsWith('/login.html')) {
  requireAuth();
  document.addEventListener('DOMContentLoaded', async () => {
    if (typeof loadLayout === 'function') {
      await loadLayout();
    }
  });
}
