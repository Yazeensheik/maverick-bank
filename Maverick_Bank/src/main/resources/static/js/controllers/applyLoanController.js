document.getElementById("applyLoanForm").addEventListener("submit", function(e){

e.preventDefault();

let loanType = document.getElementById("loanType").value;
let amount = document.getElementById("amount").value;
let purpose = document.getElementById("purpose").value;

if(!loanType || !amount || !purpose){
alert("Please fill all fields");
return;
}

let token = localStorage.getItem("token");

fetch("http://localhost:8080/api/loan-applications",{

method:"POST",

headers:{
"Content-Type":"application/json",
"Authorization":"Bearer " + token
},

body:JSON.stringify({
loanType:loanType,
amount:amount,
purpose:purpose
})

})

.then(response => {

if(response.status === 401){
alert("Please login again");
window.location.href="/index.html";
return;
}

return response.json();

})

.then(data => {
alert("Loan Application Submitted Successfully");
document.getElementById("applyLoanForm").reset();
})

.catch(error=>{
console.error(error);
});

});