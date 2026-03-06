window.AppSession = (() => {
  const KEYS = {
    username: "mb_username",
    password: "mb_password",
    role: "mb_role",
    ids: "mb_ids"
  };

  function setCredentials(username, password, role) {
    sessionStorage.setItem(KEYS.username, username || "");
    sessionStorage.setItem(KEYS.password, password || "");
    sessionStorage.setItem(KEYS.role, (role || "").toUpperCase());
  }

  function getCredentials() {
    return {
      username: sessionStorage.getItem(KEYS.username) || "",
      password: sessionStorage.getItem(KEYS.password) || "",
      role: (sessionStorage.getItem(KEYS.role) || "").toUpperCase()
    };
  }

  function getAuthHeader() {
    const { username, password } = getCredentials();
    if (!username || !password) return {};
    return {
      Authorization: "Basic " + btoa(username + ":" + password)
    };
  }

  function setIds(ids) {
    sessionStorage.setItem(KEYS.ids, JSON.stringify({
      userId: ids.userId || "",
      customerProfileId: ids.customerProfileId || "",
      employeeProfileId: ids.employeeProfileId || "",
      primaryAccountId: ids.primaryAccountId || ""
    }));
  }

  function getIds() {
    try {
      return JSON.parse(sessionStorage.getItem(KEYS.ids) || "{}");
    } catch (e) {
      return {};
    }
  }

  function clear() {
    Object.values(KEYS).forEach((key) => sessionStorage.removeItem(key));
  }

  function isLoggedIn() {
    const { username, password } = getCredentials();
    return Boolean(username && password);
  }

  return { setCredentials, getCredentials, getAuthHeader, setIds, getIds, clear, isLoggedIn };
})();
