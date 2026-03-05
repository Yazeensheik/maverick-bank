function loadAccounts() {

    var customerId = document.getElementById("customerId").value;

    getAccountsByCustomer(

        customerId,

        function(accounts) {

            var tableBody = document.getElementById("accountTableBody");

            tableBody.innerHTML = "";

            accounts.forEach(function(account) {

                var row = `
                    <tr>
                        <td>${account.accountId}</td>
                        <td>${account.accountNumber}</td>
                        <td>${account.accountType}</td>
                        <td>${account.balance}</td>
                        <td>${account.status}</td>
                    </tr>
                `;

                tableBody.innerHTML += row;

            });

        },

        function(error) {

            alert("Error loading accounts");
        }

    );
}