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
						<td>
						        <button class="btn btn-danger btn-sm"
						            onclick="removeAccount(${account.accountId})">
						            Delete
						        </button>
						    </td>
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

function removeAccount(accountId) {

    if (!confirm("Are you sure you want to delete this account?")) {
        return;
    }

    deleteAccount(

        accountId,

        function() {

            document.getElementById("row-" + accountId).remove();

            alert("Account deleted successfully");

        },

        function(error) {

            alert("Error deleting account");
        }

    );
}