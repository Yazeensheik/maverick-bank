window.UI = (() => {
  async function loadPartial(targetSelector, url) {
    const target = document.querySelector(targetSelector);
    if (!target) return;
    const response = await fetch(url);
    target.innerHTML = await response.text();
    highlightSidebar();
    updateRoleLabels();
  }

  async function loadLayout({ navbar = true, footer = true, sidebar = false } = {}) {
    if (navbar) await loadPartial("#navbar-root", "/shared/navbar.html");
    if (footer) await loadPartial("#footer-root", "/shared/footer.html");
    if (sidebar) await loadPartial("#sidebar-root", "/shared/sidebar.html");
  }

  function showAlert(targetId, message, type = "info") {
    const el = document.getElementById(targetId);
    if (!el) return;
    el.innerHTML = `<div class="alert alert-${type} rounded-4 border-0 shadow-sm">${message}</div>`;
  }

  function clearAlert(targetId) {
    const el = document.getElementById(targetId);
    if (el) el.innerHTML = "";
  }

  function currency(value) {
    return new Intl.NumberFormat("en-IN", { style: "currency", currency: "INR" }).format(Number(value || 0));
  }

  function dateTime(value) {
    if (!value) return "—";
    try { return new Date(value).toLocaleString(); }
    catch { return value; }
  }

  function showLoader() {
    document.getElementById("loader")?.classList.add("show");
  }

  function hideLoader() {
    document.getElementById("loader")?.classList.remove("show");
  }

  function protectRoute(allowedRoles = []) {
    if (!AppSession.isLoggedIn()) {
      window.location.href = "/login.html";
      return false;
    }
    const role = AppSession.getCredentials().role;
    if (allowedRoles.length && !allowedRoles.includes(role)) {
      showAlert("pageAlert", `You are logged in as <b>${role || "UNKNOWN"}</b>. This page is limited to ${allowedRoles.join(", ")}.`, "warning");
      return false;
    }
    return true;
  }

  function highlightSidebar() {
    const current = window.location.pathname;
    document.querySelectorAll(".sidebar-link").forEach((link) => {
      if (link.getAttribute("href") === current) link.classList.add("active");
    });
    const role = AppSession.getCredentials().role;
    document.querySelectorAll("[data-role-link]").forEach((link) => {
      if (link.dataset.roleLink !== role) link.style.display = "none";
    });
  }

  function updateRoleLabels() {
    const role = AppSession.getCredentials().role || "Guest";
    document.querySelectorAll("#sidebarRoleLabel, #topRoleLabel").forEach((el) => el.textContent = role);
  }

  function fillText(id, value) {
    const el = document.getElementById(id);
    if (el) el.textContent = value ?? "—";
  }

  function getFormData(formId) {
    const form = document.getElementById(formId);
    return Object.fromEntries(new FormData(form).entries());
  }

  return {
    loadLayout, showAlert, clearAlert, currency, dateTime,
    showLoader, hideLoader, protectRoute, fillText, getFormData
  };
})();
