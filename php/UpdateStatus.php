<?php
header("Content-type: text/html; charset=utf-8"); 

$log_id = $_GET["log_id"];

$con = mysql_connect("localhost:3306", "root" ,"");
mysql_set_charset('utf8', $con); 
if(!$con)
	die("fail");

mysql_select_db("calltaxi",$con);

$sql = "SELECT * FROM log where id = '$log_id'";
$ret = mysql_query($sql, $con);
$row = mysql_fetch_array($ret);
$driver = $row['driver_id'];

if("" == $driver) {
	echo "no";
	exit();
}
$sql = "SELECT * FROM driver_info where id = '$driver'";
$ret = mysql_query($sql, $con);
$row = mysql_fetch_array($ret);
$license = $row['license'];
echo $license;

mysql_close($con);

?>
