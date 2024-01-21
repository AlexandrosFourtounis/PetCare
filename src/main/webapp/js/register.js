/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    const passwordField = document.getElementById("password");
    const confirmPasswordField = document.getElementById("confirm_password");
    const passwordMatch = document.createElement("span");
    passwordMatch.style.color = "red";

    function checkPassword() {
        let password = passwordField.value;
        let confirmPassword = confirmPasswordField.value;
        if (password !== confirmPassword) {
            passwordMatch.innerHTML = "Passwords don't match!";
        }
        else {
            passwordMatch.innerHTML = "";
        }
    }
    //parent node for the message to be in the same container
    confirmPasswordField.parentNode.appendChild(passwordMatch);
    passwordField.addEventListener("keyup", checkPassword);
    confirmPasswordField.addEventListener("keyup", checkPassword);

    const passwordVisibility = document.getElementById("visibility_button");
    const confirmVisibility = document.getElementById("confirm_visibility_button");

    function seePassword(input) {
        let type = input.getAttribute("type");
        if (type === "password"){
            input.setAttribute("type", "text");
        } else {
            input.setAttribute("type", "password");
        }
    }

    passwordVisibility.addEventListener("click", function() { seePassword(passwordField) });
    confirmVisibility.addEventListener("click", function() { seePassword(confirmPasswordField) });

    const submitForm = document.getElementById("myForm");
    const strengthMessage = document.createElement("span");
    strengthMessage.style.color = "red";
    const word1 = "cat";
    const word2 = "dog";
    const word3 = "gata";
    const word4 = "skulos";

    function checkSecurity (event) {
        let password = passwordField.value;
        let numbers = countNumbers(password);
        let total = password.length;
        let percentage = (numbers / total) * 100;
        let uppercase = /[A-Z]/.test(password);
        let lowercase = /[a-z]/.test(password);
        let number = /[0-9]/.test(password);
        let symbol = /[!#$%&*+\/=?^_{|}~-]/.test(password);

        if (password.includes(word1) || password.includes(word2) || password.includes(word3) || password.includes(word4)){
                strengthMessage.innerHTML = "Password contains prohibited words";
        } else if (percentage >= 50){
            strengthMessage.innerHTML = "Weak Password";
            event.preventDefault();
        } else if ( uppercase && lowercase && number && symbol) {
            strengthMessage.innerHTML = "Strong Password";
        } else {
            strengthMessage.innerHTML = "Medium Password";
        }
    }

    function countNumbers(text) {
        let count = 0;
        for (let i = 0; i < text.length; i++) {
            if (!isNaN(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    passwordField.parentNode.appendChild(strengthMessage);
    passwordField.addEventListener("change", checkSecurity);
    submitForm.addEventListener("submit", checkSecurity);

    const userTypeRadio = document.querySelectorAll('input[name="type"]');
    const extraFields = document.getElementById("extraFields");
    const catAccommodation = document.getElementById("catkeeper");
    const dogAccommodation = document.getElementById("dogkeeper");
    const catPrice = document.getElementById("catprice");
    const dogPrice = document.getElementById("dogprice");
    const hostingType = document.getElementById("property");

    // Function to toggle visibility of extra fields based on user type
    function petKeeper() {
    if (userTypeRadio[0].checked) { // If "Pet Keeper" is selected
        extraFields.style.display = "block";
        hostingType.disabled = false; // Enable the hostingType field for pet keepers
    } else {
        extraFields.style.display = "none";
        hostingType.disabled = true; // Disable the hostingType field for pet owners
    }

    if (hostingType.value === "outdoor") {
        catAccommodation.disabled = true;
        dogAccommodation.checked = true;
    } else {
        catAccommodation.disabled = false;
    }

    if (catAccommodation.checked) {
        catPrice.required = true;
        dogPrice.required = false;
    } else if (dogAccommodation.checked) {
        dogPrice.required = true;
        catPrice.required = false;
    }
}


    //show hide
    for (const radio of userTypeRadio) {
        radio.addEventListener("change", petKeeper);
    }

    hostingType.addEventListener("change", petKeeper);
    catAccommodation.addEventListener("change", petKeeper);
    dogAccommodation.addEventListener("change", petKeeper);

    petKeeper();

    const lat = 35.3397432;
    const lon = 25.1499244;
    
    function RegisterPOST(){
        let myForm = document.getElementById('myForm');
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
                $('#myForm').hide();
                //$('#ajaxContent').show();
                
            } else if (xhr.status !== 200){
                document.getElementById('register').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>";
                //$('#register').show();
            }  
        };
    xhr.open('POST', '../Register');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(jsonData);
};
  
  