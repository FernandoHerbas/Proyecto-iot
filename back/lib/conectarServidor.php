<?php
    function iniciarConexion($usuario,$pass,$baseDeDatos,$servidor){
        $obj_conexion = new mysqli($servidor,$usuario,$pass,$baseDeDatos);
        $obj_conexion->set_charset("utf8");
        if(!$obj_conexion)
        {
            die("<h3>No se ha podido conectar PHP - MySQL, verifique sus datos.</h3><hr><br>");
        }
        return $obj_conexion;
    }
?>