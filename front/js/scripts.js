const URL = '../back/leds.php';

function cambiarColorLed(idLed){
    var ledColor = document.getElementById(idLed).style.backgroundColor;
    if(ledColor == '' || ledColor == 'gray'){
        document.getElementById(idLed).style.backgroundColor='blue';
        enviarDatosAlServer(idLed);
    }else if(ledColor == 'blue'){
        document.getElementById(idLed).style.backgroundColor='gray';
        enviarDatosAlServer(idLed);
    }
}

function enviarDatosAlServer($valor){
    $.ajax({
        url: URL,
        data:{ dato: $valor},
        type: 'POST',
        success : function(respuesta) {     //Para debug
           console.log(respuesta);
        }
    });
}