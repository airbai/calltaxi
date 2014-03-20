<?php
$log_id = $_GET['log_id'];
$driver_id = $_GET['driver_id'];

$con = mysql_connect("localhost:3306", "root", "");
if(!$con)
	die('fail');

mysql_select_db("calltaxi", $con);

$sql = "UPDATE log SET status = '1', driver_id = '$driver_id' WHERE id = '$log_id'";

echo $sql;
mysql_query($sql ,$con);
mysql_close($con);
?>
