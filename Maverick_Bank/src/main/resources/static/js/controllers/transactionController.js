document.addEventListener("DOMContentLoaded", function () {

    const loadBtn = document.getElementById("loadTransactions");

    loadBtn.addEventListener("click", loadTransactions);

});

async function loadTransactions() {
	
    const accountId = document.getElementById("accountId").value;

    if (!accountId) {
        alert("Please enter Account ID");
        return;
    }

    try {
		
		const balanceResponse = await fetch(`http://localhost:8080/api/transactions/balance/${accountId}`);
		
		const balance = await balanceResponse.json();
		
		document.getElementById("balanceDisplay").innerText = balance;

        const response = await fetch(`http://localhost:8080/api/transactions/${accountId}`);

        const transactions = await response.json();

        const tableBody = document.getElementById("transactionTable");

        tableBody.innerHTML = "";

        transactions.forEach(t => {

            const row = `
                <tr>
                    <td>${t.transactionId}</td>
                    <td>${t.amount}</td>
                    <td>${t.transactionType}</td>
                    <td>${t.accountId}</td>
                </tr>
            `;

            tableBody.innerHTML += row;

        });

    } catch (error) {

        console.error("Error loading transactions:", error);

    }

}


//TRANSFER
const transferForm = document.getElementById("transferForm");

if (transferForm) {

    transferForm.addEventListener("submit", async function (e) {

        e.preventDefault();

        const transaction = {

            amount: document.getElementById("amount").value,
            transactionType: "TRANSFER",
            accountId: document.getElementById("fromAccountId").value,
            referenceNumber: "TXN-" + Date.now()

        };

        try {

            const response = await fetch("http://localhost:8080/api/transactions/transfer", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(transaction)

            });

            if (response.ok) {

                alert("Transfer Successful");

            } else {

                alert("Transfer Failed: Due to Insufficient funds");

            }

        } catch (error) {

            console.error("Transfer error:", error);

        }

    });

}




//DEPOSIT
const depositForm = document.getElementById("depositForm");

if (depositForm) {

    depositForm.addEventListener("submit", async function(e){

        e.preventDefault();

        const transaction = {

            amount: document.getElementById("depositAmount").value,
            transactionType: "DEPOSIT",
            accountId: document.getElementById("depositAccountId").value,
            referenceNumber: "DEP-" + Date.now()

        };

        try {

            const response = await fetch("http://localhost:8080/api/transactions/deposit", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(transaction)

            });

            if(response.ok){

                alert("Deposit Successful");

            }else{

                alert("Deposit Failed");

            }

        } catch(error){

            console.error(error);

        }

    });

}



//WITHDRAW
const withdrawForm = document.getElementById("withdrawForm");

if (withdrawForm) {

    withdrawForm.addEventListener("submit", async function(e){

        e.preventDefault();

        const transaction = {

            amount: document.getElementById("withdrawAmount").value,
            transactionType: "WITHDRAW",
            accountId: document.getElementById("withdrawAccountId").value,
            referenceNumber: "WDR-" + Date.now()

        };

        try {

            const response = await fetch("http://localhost:8080/api/transactions/withdraw", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(transaction)

            });

            if(response.ok){

                alert("Withdraw Successful");

            }else{

                alert("Withdraw Failed");

            }

        } catch(error){

            console.error(error);

        }

    });

}




//STATEMENT GENERATION
document.addEventListener("DOMContentLoaded", function(){

    const btn = document.getElementById("generateStatement");

    if(btn){
        btn.addEventListener("click", generateStatement);
    }

});


async function generateStatement(){

    const accountId = document.getElementById("statementAccountId").value;
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    if(!accountId || !startDate || !endDate){

        alert("Please fill all fields");
        return;

    }

    const requestBody = {

        accountId: accountId,
        startDate: startDate,
        endDate: endDate

    };

    try{

        const response = await fetch(
        "http://localhost:8080/api/statements/generate",
        {

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify(requestBody)

        });

        const transactions = await response.json();

        const table = document.getElementById("statementTable");

        table.innerHTML = "";

        transactions.forEach(t=>{

            const row = `
            <tr>
            <td>${t.transactionId}</td>
            <td>${t.amount}</td>
            <td>${t.transactionType}</td>
            <td>${t.transactionDate}</td>
            <td>${t.referenceNumber}</td>
            <td>${t.accountId}</td>
            </tr>
            `;

            table.innerHTML += row;

        });

    }
    catch(error){

        console.error("Statement Error:",error);

    }

}
