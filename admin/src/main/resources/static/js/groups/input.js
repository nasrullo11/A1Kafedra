var status1 = "false";
var status2 = "false";
var status3 = "false";
function checkInputName() {
    var checkinput1 = document.getElementById('checkinput1').value;
    if (checkinput1.length >= 3) {
        $('#messageName').html("Guruh nomi tasdiqlandi").css('color', 'green');
        // disabledButton.disabled = true;
        var status1 = "true";

    }
    else {
        $('#messageName').html("Guruh nomi kamida  3 ta simvoldan kam bo'lmaslgi kerak").css('color', 'red');
    }
}

// function checkInputYear() {
//     var checkinput2 = document.getElementById('checkinput2').value;
//     if (parseInt(checkinput2) > 2010) {
//         $('#messageYear').html("kirgan yili tasdiqlandi").css('color', 'green');
//         // disabledButton.disabled = true;
//         var status2 = "true";
//
//     }
//     else {
//         $('#messageYear').html("Iltimos kirgan yilini kiriting").css('color', 'red');
//     }
// }
function checkInputNumber() {
    var checkinput3 = document.getElementById('checkinput3').value;
    if (checkinput3.length > 0 && checkinput3.length <= 50) {
        $('#messageNumber').html("Tabalar ").css('color', 'green');
        // disabledButton.disabled = true;
        var status3 = "true";

    }
    else {
        $('#messageNumber').html("Guruh nomi kamida  3 ta simvoldan kam bo'lmaslgi kerak").css('color', 'red');
    }
}