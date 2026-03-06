const userService = {

getAccounts: async function(){

    const response = await fetch("/api/users");

    return response.json();
}

};