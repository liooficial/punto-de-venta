<?php
$servername = "localhost";
$database = "id18305821_2030961jmc";
$username = "id18305821_2030961ujmc";
$password = "halojulio.A13";

$conn = new mysqli($servername, $username, $password, $database);

if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}
?>
