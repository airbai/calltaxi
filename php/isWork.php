<?php
$id = $_GET['id'];

$con = mysql_connect("localhost:3306", "root", "");

if(!$con)
	die('fail');

mysql_select_db("calltaxi", $con);

$sql = "SELECT * FROM driver_loca where id = '$id'";
$ret = mysql_query($sql ,$con);

$cnt = mysql_num_rows($ret);

if(0 == $cnt)
	echo "no";
else
	echo "yes";

mysql_close($con);
?>
