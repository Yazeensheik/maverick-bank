window.userService = {
  createCustomerUser(payload) {
    return window.apiClient.post('/api/users/add/customer', payload);
  },
  createEmployeeUser(payload) {
    return window.apiClient.post('/api/users/add/employee', payload);
  },
  getAllUsers() {
    return window.apiClient.get('/api/users/get/all');
  },
  deactivateUser(id) {
    return window.apiClient.put(`/api/users/deactivate/${id}`);
  },
  deleteUser(id) {
    return window.apiClient.delete(`/api/users/delete/${id}`);
  }
};
