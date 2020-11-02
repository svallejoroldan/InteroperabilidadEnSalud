<?PHP
$hostname_localhost="localhost";
$database_localhost="id14752472_datos";//nombre bd
$username_localhost="id14752472_admin_user";
$password_localhost="c{P!*4>YZ?uv+1>H";

if(isset($_POST["serial"]) && isset($_POST["pulso"]) && isset($_POST["oxigeno"]) && isset($_POST["fecha"])){

  $serial=$_POST['serial'];
  $pulso=$_POST['pulso'];
  $oxigeno=$_POST['oxigeno'];
  $fecha=$_POST['fecha'];

  $conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

  $insert = "INSERT INTO tabla_datos(serial, pulso, oxigeno, fecha) VALUES ('$serial','$pulso','$oxigeno','$fecha')";
  
  $result = mysqli_query($conexion,$insert);
  if($result){
    echo "datos enviados";
  }else{  
    echo "error en el envio de datos";
  }
  mysqli_close($conexion);
}
?>

