var bookings;
function createTableFromJSON(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>Booking ID</th><th scope='col'>Owner ID</th><th scope='col'>Pet ID</th><th scope='col'>Keeper ID</th><th scope='col'>Status</th><th scope='col'>Price</th></tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].booking_id + "</th>";
        html += "<td>" + "hidden" + "</td>";
        html += "<td>" + data[i].pet_id + "</td>";
        html += "<td>" + data[i].keeper_id + "</td>";
        html += "<td>" + data[i].status + "</td>";
        html += "<td>" + data[i].price + "</td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    return html;
}
var accepted;

function createTableFromJSONa(data) {
    var html = "<table class='table'><thead><tr><th scope='col'>Booking ID</th><th scope='col'>Pet ID</th><th scope='col'>Status</th><th scope='col'>Message</th><th scope='col'>Send</th><th scope='col'>Other User's Response</th></tr></thead><tbody>";

    for (var i = 0; i < data.length; i++) {
        html += "<tr>";
        html += "<th scope='row'>" + data[i].booking_id + "</th>";

        html += "<td>" + data[i].pet_id + "</td>";

        html += "<td>" + data[i].status + "</td>";

        html += "<td>" + "<textarea id='message_" + i + "'></textarea>" + "</td>";
        html += "<td>" + "<button onclick='sendMessage(" + i + ")'>Send</button>" + "</td>";
        html += "<td id='response_" + i + "'></td>";
        html += "</tr>";
    }

    html += "</tbody></table>";
    return html;
}

// Function to send a message for the row with index 'index'
function sendMessage(index) {
    var message = document.getElementById('message_' + index).value;
    // Use the message as needed, for example, send it to the server
    console.log("Message for row " + index + ": " + message);
    var response = "Received: " + message;
    document.getElementById('response_' + index).innerHTML = response;
    // You may want to make an AJAX request to send the message to the server
}

$(document).ready(function () {
    renderBookings();
    renderAcceptedBookings();

});

function rendermsgs(bookingIds) {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            var messages = JSON.parse(xhr.responseText);

            $("messages").html(createTableFromJSONa(messages));

        } else if (xhr.status !== 200) {

            console.error("Failed to fetch messages");
        }
    };

    xhr.open('GET', '../GetMSGS');
    xhr.send();
}

function renderBookings() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            bookings = JSON.parse(xhr.responseText);
            $("#bookingsContainer").html(createTableFromJSON(bookings));

        } else if (xhr.status !== 200) {
            $("#bookingsContainer").html("error retrieving bookings.");
        }
    };
    xhr.open('GET', '../GetBookings');
    xhr.send();
}

function renderAcceptedBookings() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            accepted = JSON.parse(xhr.responseText);
            $("#messages").html(createTableFromJSONa(accepted));
            var bookingIds = accepted.map(function (booking) {
                return booking.booking_id;
            });
//            rendermsgs(bookingIds);
        } else if (xhr.status !== 200) {
            $("#messages").html("error retrieving bookings.");
        }
    };
    xhr.open('GET', '../GetFinishedBookings');
    xhr.send();
}
