<?php
    //inicio sesiones
    session_start();
    require_once('conectarServidor.php');
    $conexion = iniciarConexion("root","42737740","pruebasDomotica","localhost");

    if(empty($_POST["user"]) || empty($_POST["password"]))  
    {  
       echo '<script>alert("Ingrese los datos.")</script>';  
    } 

    $user       = mysqli_real_escape_string($conexion,$_POST['user']);
    $password   = mysqli_real_escape_string($conexion,$_POST['password']);  
  //  $password   = password_hash($password, PASSWORD_DEFAULT);  

    $query = "SELECT * from usuarios WHERE usuario='$user'";
    $resultado = $conexion->query($query);

    if($resultado->num_rows > 0){
        $_SESSION['logged']='yes';
        $fila = $resultado->fetch_array(MYSQLI_ASSOC);
        $passwordDb = $fila['contraseña'];
        if(password_verify($password,$passwordDb)){
            echo $id    = $fila['id'];
            echo "<br>";
            echo $mail  = $fila['mail'] ;
            echo "<br>";
            $_SESSION['user']   = $user; 
            $_SESSION['userId'] = $id;
            $_SESSION['mail']   = $mail;
        }
        else
            die("Las contraseña es invalida.");
        $conexion->close();
    }
    else{
        $_SESSION['logged']='no';
        echo "Problem";
    }
?>