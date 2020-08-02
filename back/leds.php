<?php
    include 'lib/PhpSerial.php';
    $valor = $_POST['dato'];
    $serial = new PhpSerial;
    // indicamos que puerto serie queremos usar
   $serial->deviceSet("COM4");
   // ahora la velocidad de transmisión de Arduino
   $serial->confBaudRate(9600);
   $serial->deviceOpen();

   // aquí el String que queremos enviar
    var_dump($valor);
    switch($valor){
        case 'a':
            echo "hola";
            $serial->sendMessage("a");
        break;
        case 'b':
            echo "chau";
            $serial->sendMessage("b");
        break;
    }
   
    $serial->deviceClose();
?>