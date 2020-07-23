<?php
    //inicio sesiones
    session_start();
    require_once('conectarServidor.php');
    $conexion = iniciarConexion("root","42737740","pruebasDomotica","localhost");

    if(empty($_POST["user"]) || empty($_POST["password"]))  
    {  
       echo '<script>alert("Ingrese los datos.")</script>';  
    } 

    $user = strip_tags($_POST['user']);
    $password   = mysqli_real_escape_string($conexion,$_POST['password']);  
    $password   = password_hash($password, PASSWORD_DEFAULT);  
?>