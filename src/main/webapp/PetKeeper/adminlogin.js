$(document).ready(function () {
    isLoggedIn();
});


function isLoggedIn() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#ajaxContent").html("Welcome again" + xhr.responseText);

        } else if (xhr.status !== 200) {
            $("#choices").load("buttons.html");

        }
    };
    xhr.open('GET', '../LoginAdmin');
    xhr.send();
}

function loginPOST() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = "admin.html";
            $("#ajaxContent").html("Successful Login");
            const responseData = JSON.parse(xhr.responseText);

        } else if (xhr.status !== 200) {
            $("#error").html("Wrong Credentials");
            console.log('Request failed. Returned status of ' + xhr.status);
        }
    };
    var data = $('#loginForm').serialize();
    console.log(data.username);
    xhr.open('POST', '../LoginAdmin');
    xhr.setRequestHeader
            ('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}
function logout() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#ajaxContent").html("Successful Logout");
            window.location.href = "../index.html";
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    xhr.open('POST', '../Logout');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}

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
