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

function gpt() {
    let myForm = document.getElementById('queryform');
    let formData = new FormData(myForm);


    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            var textarea = document.getElementById("results");
            textarea.value = xhr.responseText;
//            $('#ajaxContent').html(xhr.responseText);
        } else if (xhr.status !== 200) {
            document.getElementById('result').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>";
            //$('#register').show();
        }
    };
    xhr.open('POST', '../HandleGPT');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
}
;

function defaultGPT(a) {
    var jsonData;
    switch (a) {
        case 1:
            jsonData = {query: "How to take care of a dog?"};
            break;
        case 2:
            jsonData = {query: "How to take care of a cat?"};
            break;
        case 3:
            jsonData = {query: "Give me information about Labrador"};
            break;
        case 4:
            jsonData = {query: "Give me information about ginger cat"};
            break;
        default:
            console.log("error getting input");
            return; // Exit the function in case of an error
    }

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            var textarea = document.getElementById("results");
            textarea.value = xhr.responseText;
        } else if (xhr.status !== 200) {
            document.getElementById('result').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>";
        }
    };
    xhr.open('POST', '../HandleGPT');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(jsonData));
}
