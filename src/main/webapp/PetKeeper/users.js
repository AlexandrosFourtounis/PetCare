
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
    return html;
}
var users;

$(document).ready(function () {
    renderUsers();
    renderOwners();

    $("#bookingsContainer, #ownersContainer").on("click", ".delete-btn", function () {
        var userId = $(this).data("user-id");
        var userType = $(this).data("user-type");
        deleteUser(userId, userType);
    });
});

function deleteUser(userId, userType) {
    // Implement your logic to delete the user
    // You can use AJAX to send a request to the server to delete the user
    console.log("Delete user with ID " + userId + " of type " + userType);
    // Make an AJAX request to delete the user using userId and userType
    // ...

    // After successful deletion, you may want to re-render the tables
    renderUsers();
    renderOwners();
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
