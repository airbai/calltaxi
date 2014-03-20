<?php
$id = $_GET['id'];
$type = $_GET['type'];

$con = mysql_connect("localhost:3306", "root", "");
if(!$con)
	die('fail');

mysql_select_db("calltaxi", $con);

if(0 == $type)
	$sql = "DELETE FROM driver_loca where id = '$id'";
else
	$sql = "INSERT INTO driver_loca VALUES('$id', '30' ,'120', '-1')";

mysql_query($sql, $con);

mysql_close($con);
?>
