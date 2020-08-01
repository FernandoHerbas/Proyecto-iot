const URL = "../../back/leds.php";

function cambiarColorLed(idLed){
    var ledColor = document.getElementById(idLed).style.backgroundColor;
    if(ledColor == '' || ledColor == 'gray'){
        document.getElementById(idLed).style.backgroundColor='blue';
        enviarDatosAlServer('a');
    }else if(ledColor == 'blue'){
        document.getElementById(idLed).style.backgroundColor='gray';
        enviarDatosAlServer('b');
    }
}

function enviarDatosAlServer($valor){
    $.ajax({
        url: '../back/leds.php',
        data:{ dato: $valor},
        type: 'POST',
        success : function(respuesta) {
           console.log(respuesta);
        }
    });
}