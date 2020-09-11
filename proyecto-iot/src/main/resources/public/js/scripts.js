const socket = new WebSocket("ws://localhost:9000/Socket");

socket.onopen = function(openEvent) {
            console.log("WebSocket OPEN: " + JSON.stringify(openEvent, null, 4));
        };

socket.onclose = function (closeEvent) {
            console.log("WebSocket CLOSE: " + JSON.stringify(closeEvent, null, 4));
        };

socket.onerror = function (errorEvent) {
            console.log("WebSocket ERROR: " + JSON.stringify(errorEvent, null, 4));
        };

socket.onmessage = function (messageEvent) {
            var wsMsg = messageEvent.data;
            console.log("WebSocket MESSAGE: " + wsMsg);
           /* if (wsMsg.indexOf("error") > 0) {
                document.getElementById("incomingMsgOutput").value += "error: " + wsMsg.error + "\r\n";
            } else {
                document.getElementById("incomingMsgOutput").value += "message: " + wsMsg + "\r\n";
            }*/
        };

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

function enviarDatosAlServer(valor){
        if (socket.readyState != socket.OPEN) {
                console.error("webSocket is not open: " + socket.readyState);
                return;
            }
        socket.send(valor);
}