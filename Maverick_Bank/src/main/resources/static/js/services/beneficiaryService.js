function addBeneficiary(data, successCallback, errorCallback) {

    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/api/v1/beneficiaries/add", true);

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

    xhr.send(JSON.stringify(data));
}

function getAllBeneficiaries(successCallback, errorCallback) {

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "/api/v1/beneficiaries/getAll", true);

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

function deleteBeneficiary(id, successCallback, errorCallback) {

    var xhr = new XMLHttpRequest();

    xhr.open("DELETE", "/api/v1/beneficiaries/delete/" + id, true);

    xhr.onreadystatechange = function() {

        if (xhr.readyState === 4) {

            if (xhr.status === 200) {

                successCallback();

            } else {

                errorCallback(xhr.responseText);
            }
        }
    };

    xhr.send();
}