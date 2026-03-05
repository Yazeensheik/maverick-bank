function loadBeneficiaries() {

    getAllBeneficiaries(

        function(list) {

            var tableBody = document.getElementById("beneficiaryTableBody");

            tableBody.innerHTML = "";

            list.forEach(function(b) {

                var row = `
                <tr id="row-${b.accountId}">
                    <td>${b.accountId}</td>
                    <td>${b.beneficiaryName}</td>
                    <td>${b.accountNumber}</td>
                    <td>${b.bankName}</td>
                    <td>${b.branchName}</td>
                    <td>${b.ifscCode}</td>
                    <td>${b.accountId}</td>
                    <td>
                        <button class="btn btn-danger btn-sm"
                            onclick="removeBeneficiary(${b.accountId})">
                            Delete
                        </button>
                    </td>
                </tr>
                `;

                tableBody.innerHTML += row;

            });

        },

        function(error) {

            alert("Error loading beneficiaries");

        }

    );
}

function removeBeneficiary(id) {

    if (!confirm("Delete this beneficiary?")) {
        return;
    }

    deleteBeneficiary(

        id,

        function() {

            document.getElementById("row-" + id).remove();
            alert("Beneficiary deleted successfully");

        },

        function(error) {

            alert("Error deleting beneficiary");

        }

    );
}