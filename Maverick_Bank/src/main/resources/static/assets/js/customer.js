window.CustomerApi = (() => {
  const accountsByCustomer = (customerProfileId) => Api.get(`/api/v1/accounts/customer/${customerProfileId}`);
  const accountById = (id) => Api.get(`/api/v1/accounts/getById/${id}`);
  const createAccount = (payload) => Api.post("/api/v1/accounts/create", payload);
  const updateAccount = (id, payload) => Api.put(`/api/v1/accounts/update/${id}`, payload);
  const closeAccount = (id) => Api.del(`/api/v1/accounts/delete/${id}`);
  const transactions = (accountId) => Api.get(`/api/transactions/${accountId}`);
  const deposit = (payload) => Api.post("/api/transactions/deposit", payload);
  const withdraw = (payload) => Api.post("/api/transactions/withdraw", payload);
  const transfer = (payload) => Api.post("/api/transactions/transfer", payload);
  const beneficiaries = () => Api.get("/api/v1/beneficiaries/getAll");
  const addBeneficiary = (payload) => Api.post("/api/v1/beneficiaries/add", payload);
  const deleteBeneficiary = (id) => Api.del(`/api/v1/beneficiaries/delete/${id}`);
  const generateStatement = (payload) => Api.post("/api/statements/generate", payload);
  const availableLoans = () => Api.get("/api/admin/loans");
  const applyForLoan = (userId, payload) => Api.post(`/api/loan-applications/user/${userId}`, payload);
  const getLoanApplication = (id) => Api.get(`/api/loan-applications/${id}`);
  const customerLoans = () => Api.get("/api/customer-loans");
  const customerLoanById = (id) => Api.get(`/api/customer-loans/${id}`);
  const customerProfile = (id) => Api.get(`/api/customer-profile/get/${id}`);
  return {
    accountsByCustomer, accountById, createAccount, updateAccount, closeAccount,
    transactions, deposit, withdraw, transfer,
    beneficiaries, addBeneficiary, deleteBeneficiary,
    generateStatement, availableLoans, applyForLoan, getLoanApplication,
    customerLoans, customerLoanById, customerProfile
  };
})();
