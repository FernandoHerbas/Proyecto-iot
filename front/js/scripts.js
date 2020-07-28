function cambiarColorLed(idLed){
    var ledColor = document.getElementById(idLed).style.backgroundColor;
    if(ledColor == 'gray'){
        document.getElementById(idLed).style.backgroundColor='blue';
    }else{
        document.getElementById(idLed).style.backgroundColor='gray';
    }
}