<?php include_once('../config/settings.inc.php');	?>
<?php

	$json = file_get_contents('php://input');
	$obj = json_decode($json,TRUE);
	
	$mysql = mysql_connect(_DB_SERVER_, _DB_USER_, _DB_PASSWD_, _DB_NAME_);
	if (!$mysql) {
		die('Sem conexão ' . mysql_error());
	}
	mysql_set_charset('utf8',$mysql);
	
	$query = "INSERT INTO ". _DB_NAME_ . ".ps_orders ";
	$query .= "( id_shop , id_address_delivery , id_address_invoice , id_customer , total_paid, total_paid_tax_incl, ";
	$query .= "total_paid_tax_excl, total_paid_real, total_products, total_products_wt , current_state , reference)";
	$query .= " VALUES (" . $obj['id_shop'] . "," . $obj['id_address'] . "," . $obj['id_address'] . "," . $obj['id_customer'];
	$query .= "," . $obj['total_venda'] . "," . $obj['total_venda'] . "," . $obj['total_venda_liquida'] . "," . $obj['total_venda'];
	$query .= "," . $obj['total_venda_liquida'] . "," . $obj['total_venda']  . ",16,'" . $obj['id_transacao'] . "')";
	
	mysql_query($query);
	
	$id_order = mysql_insert_id();
	
	$query = "INSERT INTO " ._DB_NAME_ .".vendas (id_order  , email) values (" . $id_order . ",'" . $obj['email'] . "')";
	echo $query;
	mysql_query($query);
	
	foreach($obj['item'] as $key=>$val){ 
	
	
		if( isset($val['tamanho']) && $val['tamanho'] != "" ) {
		
			$result = mysql_query("SELECT id_attribute from " . _DB_NAME_ . ".ps_attribute_lang where name='" . $val['tamanho'] . "'");
			if($result) {		
				$row = mysql_fetch_array($result, MYSQL_NUM);
				$id_attribute = $row[0];
			}
			
			$query = "INSERT INTO ". _DB_NAME_ .".ps_order_detail ( id_order , id_shop , product_id , product_quantity , product_price , total_price_tax_incl , total_price_tax_excl , unit_price_tax_incl, unit_price_tax_excl , product_attribute_id )";
			$query .= " VALUES (" . $id_order . "," . $obj['id_shop'] . "," . $val['product_id'] . "," . $val['quantidade'] . "," . $val['preco']  . "," . $val['preco_venda'] . "," . $val['preco'] * $val['quantidade'] . "," . $val['preco_venda'] / $val['quantidade']. "," . $val['preco'] . "," . $id_attribute . ")";
		
		} else {
		
			$query = "INSERT INTO ". _DB_NAME_ .".ps_order_detail ( id_order , id_shop , product_id , product_quantity , product_price , total_price_tax_incl , total_price_tax_excl , unit_price_tax_incl, unit_price_tax_excl )";
			$query .= " VALUES (" . $id_order . "," . $obj['id_shop'] . "," . $val['product_id'] . "," . $val['quantidade'] . "," . $val['preco']  . "," . $val['preco_venda'] . "," . $val['preco'] * $val['quantidade'] . "," . $val['preco_venda'] / $val['quantidade']. "," . $val['preco'] . ")";
		
		}
				
		mysql_query($query);
		
		$id_order_detail = mysql_insert_id();	
		
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",4," . $val['margen_projetandoo'] / $val['quantidade'] . "," . $val['margen_projetandoo'] . ")");
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",5," . $val['margen_ti'] / $val['quantidade'] . "," . $val['margen_ti'] . ")");
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",6," . $val['margen_paquito'] / $val['quantidade'] . "," . $val['margen_paquito']  . ")");
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",7," . $val['comissao_vendedora'] / $val['quantidade'] . "," . $val['comissao_vendedora'] . ")");
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",8," . $val['margem_lider'] / $val['quantidade'] . "," . $val['margem_lider'] . ")");
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",9," . $val['akatus'] / $val['quantidade'] . "," . $val['akatus'] . ")");
		mysql_query("INSERT INTO " . _DB_NAME_ . ".ps_order_detail_tax ( id_order_detail , id_tax , unit_amount , total_amount ) VALUES ( " . $id_order_detail . ",10," . $val['frete'] / $val['quantidade'] . "," . $val['frete'] . ")");
		
	}
	
	$xml = "<?xml version='1.0' encoding='UTF-8'?><prestashop xmlns:xlink='http://www.w3.org/1999/xlink'><pedido><id>" . $id_order . "</id></pedido></prestashop>";		
	echo json_encode(simplexml_load_string($xml));		
	
	mysql_close($mysql);
?>	

