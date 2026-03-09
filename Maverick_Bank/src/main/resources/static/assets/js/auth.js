function saveAuth(email, password) {
const token = "Basic " + btoa(`${email}:${password}`);
localStorage.setItem(
window.MB_CONFIG.STORAGE_KEY,
JSON.stringify({ token, username: email })
);
}

function getAuth() {
return JSON.parse(localStorage.getItem(window.MB_CONFIG.STORAGE_KEY) || "null");
}

function clearAuth() {
localStorage.removeItem(window.MB_CONFIG.STORAGE_KEY);
}

function logout() {
clearAuth();
window.location.href = "/login.html";
}

window.logout = logout;

const loginForm = document.getElementById("loginForm");

if (loginForm) {
loginForm.addEventListener("submit", async (e) => {
e.preventDefault();

const email = document.getElementById("username").value.trim();
const password = document.getElementById("password").value;
const btn = document.getElementById("loginBtn");
const alertBox = document.getElementById("loginAlert");

try {
  btn.disabled = true;
  btn.textContent = "Signing in...";

  // Save credentials temporarily
  saveAuth(email, password);

  // Verify login by calling secured API
  const res = await window.apiClient.get("/api/users/get/all");

  const users = res.data;

  const currentUser = users.find((u) => u.username === email);

  if (!currentUser) {
    throw new Error("User not found");
  }

  const role = currentUser.role?.name || currentUser.role;

  if (role === "ADMIN") {
    window.location.href = "/modules/dashboard/admin-dashboard.html";
  } else if (role === "EMPLOYEE") {
    window.location.href = "/modules/dashboard/employee-dashboard.html";
  } else if (role === "CUSTOMER") {
    window.location.href = "/modules/dashboard/customer-dashboard.html";
  } else {
    throw new Error("Invalid role");
  }
} catch (error) {
  clearAuth();

  if (alertBox) {
    alertBox.innerHTML =
      '<div class="alert alert-danger">Login failed. Invalid credentials.</div>';
  }

  console.error("Login error:", error);
} finally {
  btn.disabled = false;
  btn.textContent = "Login";
}

});
}
