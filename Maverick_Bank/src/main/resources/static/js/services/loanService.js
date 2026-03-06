const loanService = {

getAllLoans: async function(){

    const response = await fetch("/api/loans");

    return response.json();
},

applyLoan: async function(data){

    const response = await fetch("/api/loan-applications",{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify(data)
    });

    return response.json();
}

};