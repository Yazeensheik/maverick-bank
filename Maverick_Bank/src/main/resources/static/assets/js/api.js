window.apiClient = axios.create({
  baseURL: window.MB_CONFIG.API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

window.apiClient.interceptors.request.use((config) => {
  const auth = JSON.parse(localStorage.getItem(window.MB_CONFIG.STORAGE_KEY) || 'null');
  if (auth?.token) {
    config.headers.Authorization = auth.token;
  }
  return config;
});
