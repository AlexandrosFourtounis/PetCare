var info;
$(document).ready(function () {
    getProfileInfo();
    $("#applyChangesBtn").on("click", function () {
        applyChanges();
    });
});

function applyChanges() {
    var updatedInfo = {
        firstname: $("#firstname").val(),
        lastname: $("#lastname").val(),
        birthdate: $("#birthdate").val(),
        gender: $("input[name='gender']:checked").val(),
        country: $("#country").val(),
        city: $("#city").val(),
        address: $("#address").val(),
        personalpage: $("#personalpage").val(),
        job: $("#job").val(),
        telephone: $("#telephone").val(),
        property: $("#property").val(),
        catprice: $("#catprice").val(),
        dogprice: $("#dogprice").val(),
        propertydescription: $("#propertydescription").val(),

    };

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                // Success, handle the response as needed
                console.log("Profile updated successfully!");
            } else {
                // Error updating profile
                console.error("Error updating profile:", xhr.statusText);
            }
        }
    };

    xhr.open('POST', '../UpdateProfileInfo');  // Adjust the URL as needed
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(updatedInfo));
}

function getProfileInfo() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log(xhr.responseText);
                var info = JSON.parse(xhr.responseText);

                $("#username").val(info.username);
                $("#email").val(info.email);
                $("#firstname").val(info.firstname);
                $("#lastname").val(info.lastname);
                $("#birthdate").val(info.birthdate);
                $("input[name='gender'][value='" + info.gender + "']").prop("checked", true);
                $("#country").val(info.country.substring(0, 2).toUpperCase());
                $("#city").val(info.city);
                $("#address").val(info.address);
                $("#personalpage").val(info.personalpage);
                $("#job").val(info.job);
                $("#telephone").val(info.telephone);
                $("#property").val(info.property);
                $("#catprice").val(info.catprice);
                $("#dogprice").val(info.dogprice);
                $("#propertydescription").val(info.propertydescription);

            } else {
                $("#error").html("Error retrieving profile info. Status code: " + xhr.status);
            }
        }
    };
    xhr.open('GET', '../GetProfileInfo');
    xhr.send();
}

