<?PHP
$hostname_localhost="localhost";
$database_localhost="datos";//nombre bd
$username_localhost="root";
$password_localhost="";

$json=array();
/*
serial
pulso 
oxigeno
fecha
*/
 if(isset($_GET["serial"]) && isset($_GET["pulso"]) && isset($_GET["oxigeno"]) && isset($_GET["fecha"]) ){
  
  $serial=$_GET['serial'];
  $pulso=$_GET['pulso'];
  $oxigeno=$_GET['oxigeno'];
  $fecha=$_GET['fecha'];

  $conexion = new mysqli($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

  $insert = "INSERT INTO tabla_datos(serial, pulso, oxigeno, fecha) VALUES ('{$serial}','{$pulso}','{$oxigeno}','{$fecha}')";
  
 
  if($conexion->query($insert)===TRUE){
   
   
   $resultado = $conexion->query("SELECT * FROM tabla_datos WHERE serial = '{$serial}'");
   //$resultado=mysqli_query($conexion, $consulta);
  
   if($registro=mysqli_fetch_array($resultado)){
    $json['array_datos'][]=$registro;
   }
   mysqli_close($conexion);
   echo json_encode($json);
   
  }else{
   $resulta["serial"]=0;
   $resulta["pulso"]="NO registra";
   $resulta["oxigeno"]="NO registra";
   $resulta["fecha"]="NO registra";
   $json['array_datos'][]=$resulta;
   echo json_encode($json);
  }
 }else{
  $resulta["serial"]=0;
  $resulta["pulso"]="WS NO retorna";
  $resulta["oxigeno"]="WS NO retorna";
  $resulta["fecha"]="WS NO retorna";
  $json['array_datos'][]=$resulta;
  echo json_encode($json);
 }
?>