<?php
$type = $_GET['type'];
$id = $_GET['id'];
$pwd = $_GET['pwd'];

$con = mysql_connect("localhost:3306","root","");

if(!$con)
	die('connection fail' . mysql_error());

mysql_select_db("calltaxi", $con);

if(0 == $type) {
	$sql = "SELECT * FROM user_info where id = '$id' and pwd = '$pwd'";
	$ret = mysql_query($sql, $con);
	$cnt = mysql_num_rows($ret);

	if(0 == $cnt)
		echo "no";
	else
		echo "yes";
}
else {
	$sql = "SELECT * FROM driver_info where id = '$id' and pwd = '$pwd'";
	$ret = mysql_query($sql, $con);
	$cnt = mysql_num_rows($ret);

	if(0 == $cnt)
		echo "no";
	else
		echo "yes";
}

mysql_close($con);
?>

