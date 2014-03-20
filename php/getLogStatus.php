<?php
$log_id = $_GET['log_id'];

$con = mysql_connect("localhost:3306", "root", "");
if(!$con)
	die('fail');

mysql_select_db("calltaxi", $con);

$sql = "SELECT * FROM log WHERE id = '$log_id'";
$ret = mysql_query($sql, $con);
$row = mysql_fetch_array($ret);
$val = $row['status'];

if(-1 == $val)
	echo "cancel";
else if(1 == $val)
	echo "still";
else 
	echo "finish";
mysql_close($con);
?>
