<?php
require('conexion.php');
header('Content-Type: application/json');
$id = $_GET['id'];

$select_query = "SELECT * FROM p_base2 WHERE id=?";

$stmt = mysqli_prepare($conn, $select_query);
mysqli_stmt_bind_param($stmt, "s", $id);

mysqli_stmt_execute($stmt);
$resultado = mysqli_stmt_get_result($stmt);

if (mysqli_num_rows($resultado) > 0) {
    $rows = array();
    while ($row = mysqli_fetch_assoc($resultado)) {
        $rows[] = $row;
    }
    $json = json_encode($rows);
    echo $json;
} else {
    echo json_encode(array("mensaje" => "No se encontraron resultados"));
}

mysqli_stmt_close($stmt);
mysqli_close($conn);
?>
