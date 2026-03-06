window.EmployeeApi = (() => {
  const allAccounts = () => Api.get("/api/v1/accounts/getAll");
  const allCustomerProfiles = () => Api.get("/api/customer-profile/get/all");
  const allEmployeeProfiles = () => Api.get("/api/employee-profile/get/all");
  const loanApplications = () => Api.get("/api/loan-applications");
  const decideLoan = (id, payload) => Api.put(`/api/loan-applications/${id}/decision`, payload);
  return { allAccounts, allCustomerProfiles, allEmployeeProfiles, loanApplications, decideLoan };
})();
