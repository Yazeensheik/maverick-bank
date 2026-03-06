window.AdminApi = (() => {
  const users = () => Api.get("/api/admin/users");
  const createCustomerUser = (payload) => Api.post("/api/admin/users/customer", payload);
  const createEmployeeUser = (payload) => Api.post("/api/admin/users/employee", payload);
  const deactivateUser = (id) => Api.put(`/api/admin/users/${id}/deactivate`);
  const loans = () => Api.get("/api/admin/loans");
  const customerProfiles = () => Api.get("/api/customer-profile/get/all");
  const employeeProfiles = () => Api.get("/api/employee-profile/get/all");
  return { users, createCustomerUser, createEmployeeUser, deactivateUser, loans, customerProfiles, employeeProfiles };
})();
