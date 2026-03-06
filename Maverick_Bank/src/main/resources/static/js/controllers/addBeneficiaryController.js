document.getElementById("beneficiaryForm")
    .addEventListener("submit", function(event) {

        event.preventDefault();

        var beneficiaryData = {

            beneficiaryName: document.getElementById("beneficiaryName").value,
            accountNumber: document.getElementById("accountNumber").value,
            bankName: document.getElementById("bankName").value,
            branchName: document.getElementById("branchName").value,
            ifscCode: document.getElementById("ifscCode").value,
            accountId: parseInt(document.getElementById("accountId").value)

        };

        addBeneficiary(

            beneficiaryData,

            function(response) {

                document.getElementById("message").innerHTML =
                    "<div class='alert alert-success'>Beneficiary added successfully!</div>";

            },

            function(error) {

                document.getElementById("message").innerHTML =
                    "<div class='alert alert-danger'>Error adding beneficiary</div>";
            }

        );

    });