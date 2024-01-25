/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
$(document).ready(function () {
    isLoggedIn();
}); */


function isLoggedIn() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#ajaxContent").html("Welcome again" + xhr.responseText);

        } else if (xhr.status !== 200) {
            $("#choices").load("buttons.html");
        }
    };
    xhr.open('GET', '../../Loginowner');
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
function createTableFromJSONkeepers(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>Keeper ID</th><th scope='col'>Email</th><th scope='col'>First Name</th><th scope='col'>Last Name</th><th scope='col'>Birthdate</th><th scope='col'>Gender</th><th scope='col'>Country</th><th scope='col'>City</th><th scope='col'>Address</th><th scope='col'>Personal Page</th><th scope='col'>Job</th><th scope='col'>Telephone</th></tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].keeper_id + "</th>";
        html += "<td>" + data[i].email + "</td>";
        html += "<td>" + data[i].firstname + "</td>";
        html += "<td>" + data[i].lastname + "</td>";
        html += "<td>" + data[i].birthdate + "</td>";
        html += "<td>" + data[i].gender + "</td>";
        html += "<td>" + data[i].country + "</td>";
        html += "<td>" + data[i].city + "</td>";
        html += "<td>" + data[i].address + "</td>";
        html += "<td>" + data[i].personalpage + "</td>";
        html += "<td>" + data[i].job + "</td>";
        html += "<td>" + data[i].telephone + "</td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    return html;
}


function renderAvailablePetKeepers() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            // Assuming you have a div with id "availableKeepersContainer" to display the pet keepers

            var availablePetKeepers = JSON.parse(xhr.responseText);
            // Clear existing content
            $("#seeavailable").html(createTableFromJSONkeepers(availablePetKeepers));

        } else if (xhr.status !== 200) {
            // Handle the error
            document.getElementById('ajaxContent').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>";
        }
    };
    xhr.open('GET', '../../GetAvailablePetKeepers');
    xhr.send();
}


function Pet() {
        // Fetching values from form inputs
        var petBirthYear = document.getElementById("birthyear").value;
        var petWeight = document.getElementById("weight").value;
        var petPhoto = document.getElementById("photo").value;

        // Checking conditions
        if (parseInt(petBirthYear) <= 2000) {
            alert("Error: Invalid birth year. Please enter a birth year greater than 2000.");
            return false; // Prevent form submission
        }

        if (parseFloat(petWeight) <= 0) {
            alert("Error: Invalid weight. Please enter a weight greater than 0.");
            return false; // Prevent form submission
        }

        if (!petPhoto.startsWith("http")) {
            alert("Error: The photo URL should start with 'http'.");
            return false; // Prevent form submission
        }
    }

function registerPet(){
        let myForm = document.getElementById('petForm');
        let formData = new FormData(myForm);

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
    xhr.open('POST', '../../RegisterPet');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
};

function makeBooking() {
    let myForm = document.getElementById('petForm');
    let formData = new FormData(myForm);

    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    var jsonData = JSON.stringify(data);

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);
            $('#ajaxContent').html("Successful Booking. Your Data: " + jsonData);
            $('#petForm').hide();
        } else if (xhr.status !== 200) {
            document.getElementById('ajaxContent').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>";
        }
    };

    xhr.open('POST', '../../MakeBooking'); // Adjust the URL to match your servlet mapping
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
};

function getOwnerBookingsDetails() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var ownerBookings = JSON.parse(xhr.responseText);
                console.log(ownerBookings);
                // Assuming you have a div with id "ownerBookingsDetailsContainer" to display the details
                $("#viewBooking").html(createTableFromJSONbookings(ownerBookings));
            } else {
                console.error('Error retrieving owner bookings details. Status: ' + xhr.status);
            }
        }
    };
    xhr.open('GET', '../GetBookingsOwner');
    xhr.send();
}

function createTableFromJSONbookings(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>Booking ID</th><th scope='col'>Pet ID</th><th scope='col'>Keeper ID</th><th scope='col'>Status</th><th scope='col'>Price</th><th scope='col'>Actions</th></tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].booking_id + "</th>";
        html += "<td>" + data[i].pet_id + "</td>";
        html += "<td>" + data[i].keeper_id + "</td>";
        html += "<td>" + data[i].status + "</td>";
        html += "<td>" + data[i].price + "</td>";
        // Add the "End Booking" button with an onclick event
        html += "<td><button onclick='endBooking(" + data[i].booking_id + ")'>End Booking</button></td>";
        html += "</tr>";
    }
    html += "</tbody></table>";
    return html;
}

function endBooking(bookingId) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Optionally, update the UI or provide feedback upon successful booking end
            alert('Booking ended successfully!');
        } else if (xhr.status !== 200) {
            // Handle the error
            console.error('Error ending booking. Status: ' + xhr.status);
        }
    };

    xhr.open('POST', '../GetBookingsOwner'); // Adjust the URL to match your servlet mapping
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send('bookingId=' + bookingId);
}

