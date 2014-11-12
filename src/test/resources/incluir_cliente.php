<?php include_once('../config/settings.inc.php');	?>
<?php

	$json = file_get_contents('php://input');
	$obj = json_decode($json);

	$mysql = mysql_connect(_DB_SERVER_, _DB_USER_, _DB_PASSWD_, _DB_NAME_);
	if (!$mysql) {
		die('Sem conexão ' . mysql_error());
	}
	mysql_set_charset('utf8',$mysql);
	
	
		$result = mysql_query("SELECT c.id_customer, a.id_address FROM " ._DB_NAME_ . ".ps_customer c INNER JOIN " ._DB_NAME_ . ".ps_address a ON c.id_customer = a.id_customer WHERE email = '" . $obj->{'email'} . "'");
	
		if( $result ) {
			$row = mysql_fetch_array($result, MYSQL_NUM);
			
			if( mysql_num_rows( $result ) > 0 ) {			
				$id_customer = $row[0];
				$id_address = $row[1];
			} else { 			
				
				mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_customer (id_shop_group,id_shop,firstname, email,birthday) VALUES 
							(6,0,'".$obj->{'nome'} ."','" . $obj->{'email'} . "','". $obj->{'nascimento'} . "')");
							
				$id_customer = mysql_insert_id();
				
				$result = mysql_query("SELECT id_state from " . _DB_NAME_ . ".ps_state where iso_code='" . $obj->{'estado'} . "' AND id_country=58");
				if($result) {
				
					$row = mysql_fetch_array($result, MYSQL_NUM);
					$id_state = $row[0];
					
					mysql_query("INSERT INTO " ._DB_NAME_ . ".ps_address (id_customer,alias,lastname,	firstname,	address1,	address2,	id_state , postcode,	city,	phone,	phone_mobile,active) VALUES (" . $id_customer . ",'TABLET','" . $obj->{'sobrenome'} . "','".$obj->{'nome'} ."','" . $obj->{'endereco'} . "','" . $obj->{'bairro'} . "'," . $id_state . ",'" .$obj->{'cep'} . "','" . $obj->{'cidade'} . "'," . $obj->{'telefone'} . "," . $obj->{'celular'} . ",1)");						 
					$id_address = mysql_insert_id();					
					
				}
				
				
			
			}
			
		}	
				
		$xml = "<?xml version='1.0' encoding='UTF-8'?><prestashop xmlns:xlink='http://www.w3.org/1999/xlink'><cliente><id>" . $id_customer . "</id><address>" . $id_address . "</address></cliente></prestashop>";
		
		echo json_encode(simplexml_load_string($xml));		
	
		mysql_close($mysql);
	
?>