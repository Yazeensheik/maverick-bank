window.onload = loadApplications;

function loadApplications(){

fetch("http://localhost:8080/api/loan-applications/all")

.then(response => response.json())

.then(data => {

let table = document.getElementById("loanApplications");

table.innerHTML = "";

data.forEach(app => {

let row = `
<tr>
<td>${app.applicationId}</td>
<td>${app.loanId}</td>
<td>${app.amount}</td>
<td>${app.purpose}</td>
<td>${app.status}</td>
<td>

<button class="btn btn-success"
onclick="approveLoan(${app.applicationId})">
Approve
</button>

<button class="btn btn-danger"
onclick="rejectLoan(${app.applicationId})">
Reject
</button>

</td>
</tr>
`;

table.innerHTML += row;

});

});

}

function approveLoan(id){

fetch("http://localhost:8080/api/loan-applications/" + id + "/approve",{
method:"PUT"
})

.then(response => response.text())

.then(data => {

alert(data);
loadApplications();

});

}


function rejectLoan(id){

fetch("http://localhost:8080/api/loan-applications/" + id + "/reject",{
method:"PUT"
})

.then(response => response.text())

.then(data => {

alert(data);
loadApplications();

});

}