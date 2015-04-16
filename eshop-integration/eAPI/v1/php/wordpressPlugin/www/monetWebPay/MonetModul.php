<?php
require_once('struct.php');

abstract class UniMonetModul {
	var $name;
	var $baseConfig;
	public $config;
	public $subMethod = null;
	
	public function __construct($name, $configSetting, $subMethod) { 
		$this->name = $name;
		$this->subMethod = $subMethod;
		if ($configSetting!==null) {
			$this->baseConfig = $configSetting->uniModulConfig;
		}
	}
}

class UniModulFactory {
	function getConfigInfo($name, $subMethod=null) {
		echo $name;
		$uniModul = $this->createUniModul($name, null, $subMethod);
		echo $name.'xxxx'.$uniModul;
		return $uniModul->getConfigFields();
	}
	
	function createUniModul($name, $configSetting, $subMethod=null) {
		if (!ctype_alnum($name)) {
			user_error('Neplatny UniModul: '.$name);
			return null;
		}
		$modul = "Uni".$name;
		echo '$modul '.$modul;
		require_once($modul.".php");
		$unimod = new $modul($configSetting, $subMethod);
		return $unimod;
	}
}

function create_initialize_object($className, $data) {
	$object = new $className();
	foreach($data as $name => $value) {
		$object->{$name} = $value;
	}
	return $object;
}

?>