document.getElementById("loginForm").addEventListener("submit", function(e){

e.preventDefault();

let username = document.getElementById("username").value;
let password = document.getElementById("password").value;

fetch("/api/users/login",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify({
username:username,
password:password
})

})

.then(response => response.json())

.then(data => {

console.log(data);

// STORE TOKEN
localStorage.setItem("token", data.token);
localStorage.setItem("username", data.username);

let role = data.role.name;

if(role === "ADMIN"){
window.location.href="/modules/dashboard/admin-dashboard.html";
}

else if(role === "CUSTOMER"){
window.location.href="/modules/dashboard/customer-dashboard.html";
}

else if(role === "EMPLOYEE"){
window.location.href="/modules/dashboard/employee-dashboard.html";
}

})

.catch(error=>{
document.getElementById("error").innerText="Login failed";
});

});