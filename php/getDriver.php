<?php

$con = mysql_connect("localhost:3306","root","");

if(!$con)
	die('connection fail' . mysql_error());

mysql_select_db("calltaxi", $con);

$sql = "SELECT * FROM driver_loca";
$ret = mysql_query($sql, $con);

$cas = 0;
$push = array();
while($row = mysql_fetch_array($ret)) {
	$push[$cas] = $row['lati'];
	$cas ++;
	$push[$cas] = $row['longfuck'];
	$cas ++;
}

echo json_encode($push);
mysql_close($con);
?>

