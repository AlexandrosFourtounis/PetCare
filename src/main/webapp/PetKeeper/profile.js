var info;

var reviews;
$(document).ready(function () {
    getProfileInfo();
    renderReviews();
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
        catkeeper: $("#catkeeper").prop("checked"),
        dogkeeper: $("#dogkeeper").prop("checked")

    };

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {

                console.log("Profile updated successfully!");
                $("#error").html("Succesfully updated the profile!");
            } else {
                $("#error").html("Error updating profile info. ");
                console.log("Response text: " + xhr.responseText);
                console.error("Error updating profile:", xhr.statusText);
            }
        }
    };

    xhr.open('POST', '../UpdateProfileInfo');
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
                $("#catkeeper").prop("checked", info.catkeeper);
                $("#dogkeeper").prop("checked", info.dogkeeper);
            } else {
                $("#error").html("Error retrieving profile info. Status code: " + xhr.status);

            }
        }
    };
    xhr.open('GET', '../GetProfileInfo');
    xhr.send();
}

function createTableFromJSON(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>Review ID</th><th scope='col'>Owner ID</th><th scope='col'>Review Text</th><th scope='col'>Review Score</th></tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].review_id + "</th>";
        html += "<td>" + "hidden" + "</td>";
        html += "<td>" + data[i].reviewText + "</td>";
        html += "<td>" + data[i].reviewScore + "</td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    return html;
}


function renderReviews() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            reviews = JSON.parse(xhr.responseText);
            $("#reviews").html(createTableFromJSON(reviews));

        } else if (xhr.status !== 200) {
            $("#reviews").html("error retrieving reviews.");
        }
    };
    xhr.open('GET', '../GetReviews');
    xhr.send();
}

