<?php
$user_name = "root";
$user_pass="";
$host_name="localhost";
$db_name="volleysample";

$con=mysqli_connect($host_name,$user_name,$user_pass,$db_name);

if(mysqli_connect_errno()){
    echo json_encode(array('response'=>'Failed to connect to Mysql'));
    die();
}
if($con){
        $id=$_POST["id"];
        $sql="SELECT * FROM data WHERE id='1221'";
        $retrieved_data = array();
        $response = mysqli_query($con,$sql);
        if(mysqli_num_rows($response)>0){
        while($row = mysqli_fetch_array($response)){
            
            $data=['name'=>$row["name"],'id'=>$row["id"]];

            array_push($retrieved_data, $data);
        }
        echo json_encode(['retrieved_data'=>$retrieved_data]);
        }
        else{
        echo json_encode(array('response'=>'No data'));
        }
}
mysqli_close($con);

?>