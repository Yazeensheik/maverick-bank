document.getElementById("loanForm").addEventListener("submit", function(e){

e.preventDefault();

let loanData = {
    loanId: document.getElementById("loanId").value,
    amount: document.getElementById("amount").value,
    purpose: document.getElementById("purpose").value
};

fetch("http://localhost:8080/api/loan-applications/apply",{
    method:"POST",
    headers:{
        "Content-Type":"application/json"
    },
    body: JSON.stringify(loanData)
})
.then(response => response.json())
.then(data => {

    document.getElementById("message").innerText = "Loan Applied Successfully";

})
.catch(error => {

    document.getElementById("message").innerText = "Error applying loan";

});

});