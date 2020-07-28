function cambiarColorLed(){
    var ledColor = document.getElementById('btn-luz-1').style.backgroundColor;
    if(ledColor == 'gray'){
        document.getElementById('btn-luz-1').style.backgroundColor='blue';
    }else{
        document.getElementById('btn-luz-1').style.backgroundColor='gray';
    }
}