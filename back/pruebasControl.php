<?php

    $obj_conexion = iniciarConexion("root","42737740","pruebasDomotica","localhost");
    $obj_conexion->set_charset("utf8");
    $temp = "26.1";
    $serie = "LM35";
    $sql = "INSERT INTO `mediciones` (`id`, `fecha`, `Temperatura`, `serie`) VALUES (NULL,CURRENT_TIMESTAMP, '$temp', '$serie' )";
    $datosDeInsercion = $obj_conexion->query($sql);
    if(!$datosDeInsercion){
        echo "<h3>No se ha podido enviar los datos.</h3><hr><br>";
    }
    function iniciarConexion($usuario,$pass,$baseDeDatos,$servidor){
        $obj_conexion = new mysqli($servidor,$usuario,$pass,$baseDeDatos);
        if(!$obj_conexion)
        {
            echo "<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>";
            exit;
        }
        return $obj_conexion;
    }
?>