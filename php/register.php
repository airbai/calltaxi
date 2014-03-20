<?php
$type = $_GET["type"];
$id = $_GET["id"];
$pwd = $_GET["pwd"];

$con = mysql_connect("localhost:3306","root","");
if(!$con)
	exit;

mysql_select_db("calltaxi",$con);

if(0 == $type) {
	$ret = mysql_query("SELECT * FROM user_info where id = '$id'", $con);
	if(0 != mysql_num_rows($ret)) {
		echo "exist";
		exit;
	}

	$add = "INSERT INTO user_info (id, pwd ,type)
	VALUES
	('$id', '$pwd' ,$type)";

	mysql_query($add, $con);
	echo "add";
}
else {
	$license = $_GET['license'];
	$sql = "SELECT * FROM driver_info where id = '$id'";
	$ret = mysql_query($sql ,$con);
	if(0 != mysql_num_rows($ret)) {
		echo "exist";
		exit;
	}
	
	$add = "INSERT INTO driver_info (id, pwd ,license)
	VALUES
	('$id', '$pwd' ,'$license')";

	mysql_query($add, $con);
	echo "add";
}

mysql_close($con);

?>

