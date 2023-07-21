<?php
require('conexion.php');
header('Content-Type: application/json');

// Obtener los datos de la notificación
$sensor = $_POST['sensor'];
$valor = $_POST['valor'];
$ubicacion = $_POST['ubicacion'];
$fechaHora = date("Y-m-d H:i:s"); // Obtener la fecha y hora actual

// Preparar y ejecutar la consulta SQL
$sql = "INSERT INTO notificaciones (sensor, valor, ubicacion, fecha_hora) VALUES ('$sensor', '$valor', '$ubicacion', '$fechaHora')";

if ($conn->query($sql) === TRUE) {
    echo "Nueva notificación insertada correctamente.";
} else {
    echo "Error al insertar la notificación: " . $conn->error;
}

// Cerrar la conexión con la base de datos
mysqli_close($conn);
?>