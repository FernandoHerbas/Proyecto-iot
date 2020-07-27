<?php
    require_once('conectarServidor.php');
    $conexion = iniciarConexion("root","42737740","pruebasDomotica","localhost");

    if(empty($_POST["user"]) || empty($_POST["mail"]) || empty($_POST["password"]) || empty($_POST["rPassword"]))  
    {  
       echo '<script>alert("Los campos son obligatorios.")</script>';  
    }  

    //Me traigo los datos del formulario de html
    $user       = strip_tags($_POST['user']);
    $mail       = strip_tags($_POST['mail']);
    $password   = mysqli_real_escape_string($conexion,$_POST['password']);  
    $password   = password_hash($password, PASSWORD_DEFAULT);  
    $dia        = date("Y-m-d H:i:s");
    $passwordLength = mysqli_real_escape_string($conexion, $_POST['password']);  
    $rPassword  = mysqli_real_escape_string($conexion, $_POST['rPassword']);

    //Aca inserto los datos en la BD, pero antes verifico ciertas cosas
    if(strlen($passwordLength)<8){
        die("La contraseña debe tener al menos 8 caracteres.");
    }
    if(usuarioNoEstaRegistrado($conexion,$user) && mailNoEstaRegistrado($conexion,$mail) && lasContraseniasCoinciden($conexion,$password,$rPassword)){
        $query = "INSERT INTO `usuarios` (`id`, `fecha`, `usuario`, `contraseña`, `mail`) VALUES (NULL, '$dia', '$user', '$password', '$mail')";
        $datosDeInsercion = $conexion->query($query);
        if(!$datosDeInsercion){
            echo "<h3>No se ha podido enviar los datos.</h3><hr><br>";
        }
    }

    function usuarioNoEstaRegistrado($conexion,$user){
        $query     = "SELECT * FROM usuarios WHERE  usuario = '$user'";
        $resultado = $conexion->query($query);
        while($fila = $resultado->fetch_array(MYSQLI_ASSOC)){
            if($fila['usuario'] == $user)
                die("El usuario se encuentra registrado.");
        }
        return true;
    }
    function mailNoEstaRegistrado($conexion,$mail){
        $query     = "SELECT * FROM usuarios WHERE  mail = '$mail'"; 
        $resultado = $conexion->query($query);
        while($fila = $resultado->fetch_array(MYSQLI_ASSOC)){
            if($fila['mail'] == $mail)
                die("El mail se encuentra registrado en otro usuario.");
        }
        return true;
    }
    function lasContraseniasCoinciden($conexion,$password,$rPassword){
        if(!password_verify($rPassword,$password)){
            die("Las contraseñas no coinciden.");
        }
        return true;
    }
    /* cerrar la conexión */
    $conexion->close();

?>