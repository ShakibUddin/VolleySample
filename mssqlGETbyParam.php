<?php
$serverName = "DESKTOP-98S3HTF\SHAKIBUDDINSQL";
$connectionInfo = array( "Database"=>"student", "UID"=>"", "PWD"=>"");
$conn = sqlsrv_connect( $serverName, $connectionInfo );
if( $conn === false ) {
    die( print_r( sqlsrv_errors(), true));
    echo json_encode(array('response'=>'Failed to connect to Mssql'));
}
$pass = $_POST["pass"];
$sql = "SELECT * FROM data WHERE pass='$pass'";
$retrieved_data = array();
$stmt = sqlsrv_query( $conn, $sql );
if( $stmt === false) {
    die( print_r( sqlsrv_errors(), true) );
    echo json_encode(array('response'=>'Failed to connect to Mssql'));
}

while( $row = sqlsrv_fetch_array( $stmt, SQLSRV_FETCH_ASSOC) ) {
      $data=['name'=>$row["name"],'pass'=>$row["pass"]];
      array_push($retrieved_data, $data);
}
echo json_encode(['retrieved_data'=>$retrieved_data]);
sqlsrv_free_stmt( $stmt);
?>