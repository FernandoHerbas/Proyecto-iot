function cambiarColorLed(idLed){
    var ledColor = document.getElementById(idLed).style.backgroundColor;
    if(ledColor == '' || ledColor == 'gray'){
        document.getElementById(idLed).style.backgroundColor='blue';
        sendPost(idLed + "on");
    }else if(ledColor == 'blue'){
        document.getElementById(idLed).style.backgroundColor='gray';
        sendPost(idLed + "off");
    }
}

function sendPost(valor){
       $.ajax({
           type: "POST",
           url: "cola/led",
           data: JSON.stringify({ value: valor}),
           success: function(resultado){
               console.log(resultado);  
           },
           error(resultado) {
                console.log("error");
                console.log(resultado);
        }
       });
}