/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    xhr.open('GET', '../../Login');
    xhr.send();
}

function loginpPOST() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = "../petowner.html";
            $("#ajaxContent").html("Successful Login");
            const responseData = JSON.parse(xhr.responseText);
            $('#ajaxContent').append(createTableFromJSON(responseData));
        } else if (xhr.status !== 200) {
            $("#error").html("Wrong Credentials");
            ('Request failed. Returned status of ' + xhr.status);
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('POST', '../../Loginowner');
    xhr.setRequestHeader
            ('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}
function logout() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.href = "../../index.html";
            $("#ajaxContent").html("Successful Logout");
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    xhr.open('POST', '../../Logout');
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

function registerPet(){
        let myForm = document.getElementById('petForm');
        let formData = new FormData(myForm);
        //formData.append = ('lat', lat);
        //formData.append = ('lon', lon);
        
        const data = {};
        formData.forEach((value, key) => (data[key] = value));
        var jsonData = JSON.stringify(data);
        
        var xhr = new XMLHttpRequest();
        xhr.onload = function (){
            if (xhr.readyState === 4 && xhr.status === 200){
                const responseData = JSON.parse(xhr.responseText);
                $('#ajaxContent').html("Successful Registration. Now please log in!<br> Your Data: " + jsonData);
                //console.log('Success! ' + responseData['success'], 'success');
                $('#petForm').hide();
                //$('#ajaxContent').show();
                
            } else if (xhr.status !== 200){
                document.getElementById('ajaxContent').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>";
                //$('#register').show();
            }  
        };
    xhr.open('POST', '../../Register');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
};