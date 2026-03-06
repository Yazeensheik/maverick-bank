window.Auth = (() => {
  async function probeAnyAuthenticated() {
    return await Api.get("/api/customer-loans");
  }

  async function probeAdmin() {
    return await Api.get("/api/admin/users");
  }

  async function login(username, password, expectedRole) {
    AppSession.setCredentials(username, password, expectedRole);
    if ((expectedRole || "").toUpperCase() === "ADMIN") {
      await probeAdmin();
    } else {
      await probeAnyAuthenticated();
    }
    return true;
  }

  function logout() {
    AppSession.clear();
    window.location.href = "/login.html";
  }

  function routeByRole(role) {
    const map = {
      CUSTOMER: "/dashboards/customer.html",
      EMPLOYEE: "/dashboards/employee.html",
      ADMIN: "/dashboards/admin.html"
    };
    window.location.href = map[(role || "").toUpperCase()] || "/login.html";
  }

  return { login, logout, routeByRole };
})();
