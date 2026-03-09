document.getElementById("registerForm").addEventListener("submit", function(e){

e.preventDefault();

let email = document.getElementById("email").value;
let password = document.getElementById("password").value;
let role = document.getElementById("role").value;

let url = "";

if(role === "CUSTOMER"){
    url = "/api/users/add/customer";
}
else if(role === "EMPLOYEE"){
    url = "/api/users/add/employee";
}
else{
    document.getElementById("message").innerText = "Please select a role";
    return;
}

console.log("Sending request to:", url);

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

.then(response => {

if(!response.ok){
throw new Error("Registration failed");
}

return response.text();

})

.then(data => {

document.getElementById("message").innerText = "Registration successful!";

setTimeout(()=>{
window.location.href="/login.html";
},1500)

})

.catch(error=>{
console.error(error);
document.getElementById("message").innerText="Registration failed";
});

});