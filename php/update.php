<?php
$log_id = $_GET["log_id"];
$to = $_GET["to"];

$con = mysql_connect("localhost:3306", "root", "");

if(!$con)
	die('fail');

mysql_select_db("calltaxi", $con);

$now = date("Y-m-d H:m:s");

$sql = "UPDATE log SET status = '$to', ed_time = '$now' WHERE id = '$log_id'";
echo $sql;
mysql_query($sql, $con);
mysql_close($con);
?>
