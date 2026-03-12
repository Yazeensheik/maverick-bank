window.apiClient = axios.create({
  baseURL: window.MB_CONFIG.API_BASE_URL,
  headers: { 'Content-Type': 'application/json' }
});

window.apiClient.interceptors.request.use((config) => {
  const session = JSON.parse(localStorage.getItem(window.MB_CONFIG.STORAGE_KEY) || 'null');
  if (session?.token) {
    config.headers.Authorization = session.token;
  }
  return config;
});
