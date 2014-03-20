<?php
$id = $_GET['id'];
$lati = $_GET['lati'];
$long = $_GET['long'];

$con = mysql_connect("localhost:3306", "root" ,"");
if(!$con)
	die("fail");

mysql_select_db("calltaxi",$con);

$sql = "UPDATE driver_loca SET lati = '$lati', longfuck = '$long' WHERE id = '$id'";
echo $sql;

mysql_query($sql ,$con);
mysql_close($con);
?>
