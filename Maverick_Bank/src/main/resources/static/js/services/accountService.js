function createAccount(accountData, successCallback, errorCallback) {

    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/api/v1/accounts/create", true);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 201) {

                var response = JSON.parse(xhr.responseText);
                successCallback(response);

            } else {

                errorCallback(xhr.responseText);
            }
        }
    };

    xhr.send(JSON.stringify(accountData));
}

function getAccountsByCustomer(customerId, successCallback, errorCallback) {

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "/api/v1/accounts/customer/" + customerId, true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                var response = JSON.parse(xhr.responseText);
                successCallback(response);

            } else {

                errorCallback(xhr.responseText);
            }
        }
    };

    xhr.send();
}