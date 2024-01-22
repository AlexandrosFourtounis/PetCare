/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//$(document).ready(function () {
//    isLoggedIn();
//});


function isLoggedIn() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#ajaxContent").html("Welcome again" + xhr.responseText);

        } else if (xhr.status !== 200) {
            window.location.href = 'Project_csd5031/../../index.html';
        }
    };
    xhr.open('GET', '../../Login');
    xhr.send();
}

function logout() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#ajaxContent").html("Successful Logout");
            setTimeout(function () {
                window.location.href = 'Project_csd5031/../../index.html';
            }, 1000);

        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }
    };
    xhr.open('POST', '../Logout');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send();
}