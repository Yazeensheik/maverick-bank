Maverick Bank - Clean Static v4

What is included:
- Users: create, deactivate, delete
- Customers: create, update, delete
- Employees: create, update, delete
- Accounts: create, update, delete
- Transactions: deposit, withdraw, transfer, balance, history
- Statements: generate by account and date range
- Loans: apply, approve, reject
- Beneficiaries: add and list

Important backend limitation:
- Beneficiary delete is NOT added in this clean version because the current backend GET /api/v1/beneficiaries/getAll response does not return beneficiaryId.
- Without beneficiaryId, the frontend cannot safely call DELETE /api/v1/beneficiaries/delete/{id}.
- If you add beneficiaryId to BeneficiaryDTO and return it from the backend, I can enable delete immediately.

Employee dashboard:
- Users, Customers, Employees, Accounts, Beneficiaries, Transactions, Statements, Loans are shown in the employee sidebar.

How to use:
- Replace your current src/main/resources/static with this folder content.
- Restart the backend.
- Hard refresh the browser with Ctrl + F5.
