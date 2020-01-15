<?php
$user_name = "root";
$user_pass="";
$host_name="localhost";
$db_name="volleysample";

$con=mysqli_connect($host_name,$user_name,$user_pass,$db_name);

if($con){
	$name = $_POST["name"];
	$id = $_POST["id"];
	$sql = "insert into data(name,id) values('$name','$id')";
	

	if(mysqli_query($con,$sql)){
		echo json_encode(array('response'=>'Data Uploaded Successfully'));
	}
	else{
		echo json_encode(array('response'=>'Data Upload Failed'));
	}
}
else{
	echo json_encode(array('response'=>'Data Upload Failed'));
}
mysqli_close($con);
?>