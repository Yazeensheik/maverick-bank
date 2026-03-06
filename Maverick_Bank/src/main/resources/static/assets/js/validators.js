window.Validators = (() => {
  const email = (value) => /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/.test(value || "");
  const phone = (value) => /^[0-9]{10}$/.test(value || "");
  const amount = (value) => !isNaN(value) && Number(value) > 0;
  const accountNumber = (value) => /^[0-9A-Za-z]{8,20}$/.test(value || "");
  const password = (value) => /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{6,}$/.test(value || "");
  const ifsc = (value) => /^[A-Za-z0-9]{5,15}$/.test(value || "");
  return { email, phone, amount, accountNumber, password, ifsc };
})();
