document.addEventListener("DOMContentLoaded", function(){

const userId = localStorage.getItem("userId");

if(userId){
document.getElementById("userIdDisplay").textContent = userId;
}

});