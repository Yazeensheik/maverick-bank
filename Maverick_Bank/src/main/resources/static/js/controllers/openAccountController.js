document.getElementById("openAccountForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();

        var accountData = {

            accountNumber: document.getElementById("accountNumber").value,
            accountType: document.getElementById("accountType").value,
            balance: parseFloat(document.getElementById("balance").value),
            status: document.getElementById("status").value,
            customerProfileId: parseInt(document.getElementById("customerProfileId").value),
            accountId: 1
        };

        createAccount(

            accountData,

            function(response) {

                document.getElementById("message").innerHTML =
                    "<div class='alert alert-success'>Account created successfully!</div>";

            },

            function(error) {

                document.getElementById("message").innerHTML =
                    "<div class='alert alert-danger'>Error creating account</div>";
            }

        );

    });