var no_owners;
var no_keepers;
var no_cats;
var no_dogs;
var sum_app;
var sum_keepers;
function createTableFromJSON(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>User ID</th><th scope='col'>Type</th><th scope='col'>First Name</th><th scope='col'>username</th></tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].keeper_id + "</th>";
        html += "<td>" + "petkeeper" + "</td>";
        html += "<td>" + data[i].firstname + "</td>";
        html += "<td>" + data[i].username + "</td>";
        html += "<td><button class='delete-btn' data-user-id='" + data[i].keeper_id + "' data-user-type='" + "petkeeper" + "'>Delete</button></td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    no_keepers = i;
    return html;
}

function createTableFromJSONa(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>User ID</th><th scope='col'>Type</th><th scope='col'>First Name</th><th scope='col'>username</th></tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].owner_id + "</th>";
        html += "<td>" + "petowner" + "</td>";
        html += "<td>" + data[i].firstname + "</td>";
        html += "<td>" + data[i].username + "</td>";
        html += "<td><button class='delete-btn' data-user-id='" + data[i].owner_id + "' data-user-type='" + "petowner" + "'>Delete</button></td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    no_owners = i;
    return html;
}
var users;

$(document).ready(function () {
    renderUsers();
    renderOwners();
    getDogs();
    $("#bookingsContainer, #ownersContainer").on("click", ".delete-btn", function () {
        var userId = $(this).data("user-id");
        var userType = $(this).data("user-type");
        deleteUser(userId, userType);
    });
});

function deleteUser(userId, userType) {

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Delete user with ID " + userId + " of type " + userType);
            renderUsers();
            renderOwners();
        } else if (xhr.status !== 200) {
            $("#error").html("error retrieving users.");
        }
    };
    xhr.open('POST', '../DeleteUsers?userId=' + userId + '&userType=' + userType);
    xhr.send();

}

function renderUsers() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //console.log(xhr.responseText);
            users = JSON.parse(xhr.responseText);
            $("#bookingsContainer").html(createTableFromJSON(users));
        } else if (xhr.status !== 200) {
            $("#bookingsContainer").html("error retrieving users.");
        }
    };
    xhr.open('GET', '../GetUsers');
    xhr.send();
}
var owners;
function renderOwners() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //console.log(xhr.responseText);
            owners = JSON.parse(xhr.responseText);
            $("#ownersContainer").html(createTableFromJSONa(owners));
        } else if (xhr.status !== 200) {
            $("#ownersContainer").html("error retrieving users.");
        }
    };
    xhr.open('GET', '../GetUsersOwners');
    xhr.send();
}


function getDogs() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            var temp = JSON.parse(xhr.responseText);
            no_dogs = temp.no_dogs;
            no_cats = temp.no_cats;
            sum_app = temp.sum_app;
            sum_keepers = temp.sum_keepers;
            console.log("no dogs = " + no_dogs + " no_cats == " + no_cats);
            console.log("no keepers = " + no_keepers + " owners == " + no_owners);
            document.getElementById("hidden_dogs").value = no_dogs;
            document.getElementById("hidden_cats").value = no_cats;
            document.getElementById("hidden_keepers").value = no_keepers;
            document.getElementById("hidden_owners").value = no_owners;
            document.getElementById("hidden_sum_app").value = sum_app;
            document.getElementById("hidden_sum_keepers").value = sum_keepers;
        } else if (xhr.status !== 200) {
            $("#ownersContainer").html("error getting dogs.");
        }
    };
    xhr.open('GET', '../GetDogs');
    xhr.send();
}
