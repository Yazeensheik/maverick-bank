window.employeeService = {
  createEmployee(payload) {
    return window.apiClient.post('/api/employee-profile/add', payload);
  },
  getAllEmployees() {
    return window.apiClient.get('/api/employee-profile/get/all');
  },
  deleteEmployee(id) {
    return window.apiClient.delete(`/api/employee-profile/delete/${id}`);
  }
};
