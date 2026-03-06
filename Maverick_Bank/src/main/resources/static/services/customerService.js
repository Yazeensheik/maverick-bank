window.customerService = {
  createCustomer(payload) {
    return window.apiClient.post('/api/customer-profile/add', payload);
  },
  getAllCustomers() {
    return window.apiClient.get('/api/customer-profile/get/all');
  },
  deleteCustomer(id) {
    return window.apiClient.delete(`/api/customer-profile/delete/${id}`);
  }
};
