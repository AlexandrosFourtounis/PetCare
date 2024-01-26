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
$(document).ready(function () {
    renderBookings();
});

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
