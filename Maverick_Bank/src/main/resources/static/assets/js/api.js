window.Api = (() => {
  const instance = axios.create({
    baseURL: APP_CONFIG.API_BASE_URL,
    timeout: 15000
  });

  instance.interceptors.request.use((config) => {
    config.headers = {
      ...(config.headers || {}),
      ...AppSession.getAuthHeader()
    };
    return config;
  });

  function unwrapError(error) {
    if (error.response?.data?.message) return error.response.data.message;
    if (typeof error.response?.data === "string") return error.response.data;
    return error.message || "Unexpected API error";
  }

  async function get(url, config = {}) {
    try { return (await instance.get(url, config)).data; }
    catch (error) { throw new Error(unwrapError(error)); }
  }

  async function post(url, payload, config = {}) {
    try { return (await instance.post(url, payload, config)).data; }
    catch (error) { throw new Error(unwrapError(error)); }
  }

  async function put(url, payload, config = {}) {
    try { return (await instance.put(url, payload, config)).data; }
    catch (error) { throw new Error(unwrapError(error)); }
  }

  async function del(url, config = {}) {
    try { return (await instance.delete(url, config)).data; }
    catch (error) { throw new Error(unwrapError(error)); }
  }

  return { instance, get, post, put, del };
})();
