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