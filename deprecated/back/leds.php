<?php
    $valor = $_POST['dato'];

   // aquí el String que queremos enviar
    //var_dump($valor);
    switch($valor){
        case 'led1':
            echo "led1-toggle";
        break;
        case 'led2':
            echo "led2-toggle";
        break;
        case 'LM35':
                echo "aaaaaaaaaaaaaaaah";
        break;
    }
?>