<?php
$id = $_GET["id"];
$lati = $_GET["lati"];
$long =$_GET["long"];

$con = mysql_connect("localhost", "root", "");

if(!$con)
	die('connection fail' . mysql_error());

mysql_select_db("calltaxi", $con);

$now = date("Y-m-d H:m:s");
$sql = "INSERT INTO log (user_id,st_time ,status) VALUES ('$id','$now','0')";
mysql_query($sql, $con);
$log_id = mysql_insert_id();
echo $log_id;

$sql = "INSERT INTO user_loca VALUES ('$id', '$lati' , '$long', '$log_id')";
$ret = mysql_query($sql, $con);

mysql_close($con);
?>
