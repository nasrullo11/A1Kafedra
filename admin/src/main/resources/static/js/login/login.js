function showHidePassword(x){
        var username = document.querySelector('#username');
        var password = document.querySelector('#password');
        if(password.type == 'password'){
            password.type = 'text';
        } else {
            password.type = 'password';
        }
        x.classList.toggle("fa-eye-slash");
    }
    function showHidePasswordfirst(x){
        var username = document.querySelector('#username');
        var password = document.querySelector('#passwordId');
        if(password.type == 'password'){
            password.type = 'text';
        } else {
            password.type = 'password';
        }
        x.classList.toggle("fa-eye-slash");
    }
    function showHidePasswordsecond(x){
        var username = document.querySelector('#username');
        var password = document.querySelector('#confirm_passwordId');
        if(password.type == 'password'){
            password.type = 'text';
        } else {
            password.type = 'password';
        }
        x.classList.toggle("fa-eye-slash");
    }
    var d = new Date();
    document.getElementById("yeart").innerHTML = d.getFullYear();









