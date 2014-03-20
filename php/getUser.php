<?php

$con = mysql_connect("localhost:3306", "root", "");

if(!$con)
	die('fail');

mysql_select_db("calltaxi", $con);

$sql = "SELECT * FROM user_loca";
$ret = mysql_query($sql, $con);

$cas = 0;
$push = array();
while($row = mysql_fetch_array($ret)) {
	$push[$cas] = $row['lati'];
	$cas ++;
	$push[$cas] = $row['long'];
	$cas ++;
	$push[$cas] = $row['log_id'];
	$cas ++;
}

echo json_encode($push);
mysql_close($con);
?>

