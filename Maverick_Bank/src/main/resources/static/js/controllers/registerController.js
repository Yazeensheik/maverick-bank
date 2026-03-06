document.getElementById("registerForm").addEventListener("submit", function(e){

e.preventDefault();

let email = document.getElementById("email").value;
let password = document.getElementById("password").value;
let role = document.getElementById("role").value;

let url = "";

// decide endpoint based on role
if(role === "customer"){
    url = "/api/users/add/customer";
}
else if(role === "employee"){
    url = "/api/users/add/employee";
}
else{
    document.getElementById("message").innerText = "Please select a role";
    return;
}

fetch(url,{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify({
email: email,
password: password
})

})

.then(response => response.json())

.then(data => {

document.getElementById("message").innerText = "Registration successful!";

setTimeout(() => {
window.location.href="/login.html";
},1500)

})

.catch(error=>{
document.getElementById("message").innerText="Registration failed";
});

});