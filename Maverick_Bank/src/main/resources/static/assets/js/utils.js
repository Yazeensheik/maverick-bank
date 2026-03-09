function setAlert(message, type = 'success', targetId = 'globalAlert') {
  const el = document.getElementById(targetId);
  if (!el) return;
  el.innerHTML = `<div class="alert alert-${type} alert-dismissible fade show" role="alert">${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div>`;
}

function escapeHtml(value) {
  return String(value ?? '')
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

async function loadLayout() {
  const [sidebarRes, navbarRes, footerRes] = await Promise.all([
    fetch('/components/sidebar.html'),
    fetch('/components/navbar.html'),
    fetch('/components/footer.html')
  ]);

  const sidebar = document.getElementById('sidebar');
  const navbar = document.getElementById('navbar');
  const footer = document.getElementById('footer');

  if (sidebar) sidebar.innerHTML = await sidebarRes.text();
  if (navbar) navbar.innerHTML = await navbarRes.text();
  if (footer) footer.innerHTML = await footerRes.text();

  applyActiveLink();
  const auth = getAuth();
  const navUsername = document.getElementById('navUsername');
  if (navUsername && auth?.username) navUsername.textContent = auth.username;
}

function applyActiveLink() {
  document.querySelectorAll('.sidebar-nav a').forEach(a => {
    if (a.getAttribute('href') === window.location.pathname) a.classList.add('active-link');
  });
}

function toggleSidebar() {
  const sidebar = document.getElementById('sidebar');
  if (sidebar) sidebar.classList.toggle('show');
}

function renderActiveBadge(active) {
  return active
    ? '<span class="badge badge-soft-success">Active</span>'
    : '<span class="badge badge-soft-danger">Inactive</span>';
}
