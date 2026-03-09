function selectLoan(loanId){

document.getElementById("loanId").value = loanId;

}

function getLoan(){

let id = document.getElementById("searchId").value;

fetch("http://localhost:8080/api/loan-applications/" + id)

.then(response => response.json())

.then(data => {

let table = document.getElementById("loanTable");

table.innerHTML = `
<tr>
<td>${data.loanId}</td>
<td>${data.amount}</td>
<td>${data.purpose}</td>
<td>${data.status}</td>
</tr>
`;

});

}
window.onload = loadLoans;

function loadLoans(){

fetch("http://localhost:8080/api/loan-applications/loans")
.then(response => response.json())
.then(data => {

let table = document.getElementById("loanList");

table.innerHTML="";

data.forEach(loan => {

	let row = `
	<tr>
	<td>${loan.id}</td>
	<td>${loan.loanType}</td>
	<td>${loan.interestRate}%</td>
	<td>
	<button class="btn btn-success"
	onclick="selectLoan(${loan.id})">
	Apply
	</button>
	</td>
	</tr>
	`;
table.innerHTML += row;

});

});

}

function applyLoan(){

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

alert("Loan Applied Successfully!");

/* Automatically show application ID */

document.getElementById("searchId").value = data.applicationId;

})

.catch(error => {
console.error(error);
});

}