
function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}

function getUser() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(JSON.parse(xhr.responseText)));
          //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("User not exists or incorrect password");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'GetKeeper?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}


function getAllPetKeepers() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#petKeeperContent").html(createTableFromJSONkeepers(JSON.parse(xhr.responseText)));
        } else if (xhr.status !== 200) {
            $("#petKeeperContent").html("Error retrieving all pet keepers");
        }
    };

    xhr.open('GET', 'KeepersList');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}


function createTableFromJSONkeepers(data) {
    var html = "<table><tr><th>First Name</th><th>Last Name</th><th>Country</th><th>City</th><th>Telephone</th><th>Property Description</th><th>Property</th></tr>";
    for (const x in data) {
        var petKeeper = data[x];
        html += "<tr><td>" + petKeeper.firstname + "</td><td>" + petKeeper.lastname + "</td><td>" + petKeeper.country + "</td><td>" + petKeeper.city + "</td><td>" + petKeeper.telephone + "</td><td>" + petKeeper.propertydescription + "</td><td>" + petKeeper.property + "</td></tr>";
    }
    html += "</table>";
    return html;
}

function showRegistrationForm(){
   window.open('html/register.html', '_blank');
}