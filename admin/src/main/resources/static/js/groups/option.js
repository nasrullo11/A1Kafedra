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
function chekInputSelect() {
    console.log("galdiku chekInputga");
    var date = new Date();
    var optionYear = date.getFullYear();
    document.getElementById('po0').innerHTML = optionYear - 11;
    document.getElementById('po1').innerHTML = optionYear - 10;
    document.getElementById('po2').innerHTML = optionYear - 9;
    document.getElementById('po3').innerHTML = optionYear - 8;
    document.getElementById('po4').innerHTML = optionYear - 7;
    document.getElementById('po5').innerHTML = optionYear - 6;
    document.getElementById('po6').innerHTML = optionYear - 5;
    document.getElementById('po7').innerHTML = optionYear - 4;
    document.getElementById('po8').innerHTML = optionYear - 3;
    document.getElementById('po9').innerHTML = optionYear - 2;
    document.getElementById('po10').innerHTML = optionYear - 1;
    document.getElementById('po11').innerHTML = optionYear;
}
chekInputSelect();
function checkBtn() {
    if (status1 == "true" && status3 == "true"){

    }

}
