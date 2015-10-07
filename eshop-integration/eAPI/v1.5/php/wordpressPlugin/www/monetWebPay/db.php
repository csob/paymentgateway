<?php
function sqlconnect() {
	global $mysql;
	$dbconn = mysql_connect($mysql['host'], $mysql['user'], $mysql['password']);
	mysql_select_db($mysql['name']) or die(mysql_error());
	mysql_query("SET NAMES 'UTF-8'");
}

function sqlexecute($sql) {
	$res = mysql_query($sql) or trigger_error(mysql_error() . "<br/>SQL: ".$sql, E_USER_ERROR);
	return $res;
}

function sqlquery($sql) {
	$res = sqlexecute($sql);

	$ar = array();
	while($row = mysql_fetch_array($res)) {
		$ar[] = $row;
	}

	return $ar;
}

function toSql($text) {
	if (is_null($text)) {
		return "null";
	} else {
		return "'".mysql_real_escape_string($text)."'";
	}
}
?>