-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ellasa
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.13.10.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contrato`
--

DROP TABLE IF EXISTS `contrato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contrato` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inicio` date NOT NULL,
  `termino` date DEFAULT NULL,
  `franqueado_id` bigint(20) NOT NULL,
  `contrato_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qt5cbjivar2h489rtsw73l0d7` (`franqueado_id`),
  KEY `FK_93dcmhuhjpmgstubb29y193tc` (`contrato_id`),
  CONSTRAINT `FK_93dcmhuhjpmgstubb29y193tc` FOREIGN KEY (`contrato_id`) REFERENCES `tipofranquia` (`id`),
  CONSTRAINT `FK_qt5cbjivar2h489rtsw73l0d7` FOREIGN KEY (`franqueado_id`) REFERENCES `franqueado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contrato`
--

LOCK TABLES `contrato` WRITE;
/*!40000 ALTER TABLE `contrato` DISABLE KEYS */;
INSERT INTO `contrato` VALUES (52,'2014-04-08',NULL,141,4),(53,'2014-04-08',NULL,142,4),(54,'2014-04-09',NULL,143,4),(55,'2014-04-09',NULL,144,4),(56,'2014-04-09',NULL,145,4),(57,'2014-04-09',NULL,146,4),(58,'2014-04-09',NULL,147,4),(59,'2014-04-09',NULL,150,4),(60,'2014-04-09',NULL,151,4);
/*!40000 ALTER TABLE `contrato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!50001 DROP VIEW IF EXISTS `estado`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `estado` (
  `uf` tinyint NOT NULL,
  `nome` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `franqueado`
--

DROP TABLE IF EXISTS `franqueado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `franqueado` (
  `codigo` char(10) NOT NULL,
  `bairro` varchar(255) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `complemento` varchar(255) DEFAULT NULL,
  `logradouro` varchar(255) NOT NULL,
  `numero` varchar(255) NOT NULL,
  `nomeCompleto` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL,
  `uf` char(2) NOT NULL,
  `franqueado_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6y5i74a0mofhp1tti96477df0` (`codigo`),
  KEY `FK_egcomdd0tlma7dunsxj67dhp7` (`franqueado_id`),
  CONSTRAINT `FK_egcomdd0tlma7dunsxj67dhp7` FOREIGN KEY (`franqueado_id`) REFERENCES `franqueado` (`id`),
  CONSTRAINT `FK_pgu8eseue7qhmvy56asdpw3w5` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `franqueado`
--

LOCK TABLES `franqueado` WRITE;
/*!40000 ALTER TABLE `franqueado` DISABLE KEYS */;
INSERT INTO `franqueado` VALUES ('TQPRBUTPFP','Bela Vista','28940-000','Sao Pedro da Aldeia','','Trav. Jonas Gomes de Macedo','110','',141,'RJ',NULL),('4RGF6OP4SS','Centro','27909-001','CABO FRIO','loja 7','Rua Francisco Mendes','350','',142,'RJ',NULL),('5RZLCSQX1H','Centro','27910-640','Macaé','5','Rua Conde de Araruama','513','',143,'RJ',NULL),('2COZCBJLVM','Sao Mateus','36025-120','Juiz de Fora','casa','Rua Pedro Scapim','315','',144,'MG',NULL),('NSQYXRRE4T','Visconde de Araujo','27943-670','Macaé','fundos','Rua Brigadeiro Eduardo Gomes','64','',145,'RJ',NULL),('UF7CPRKETU','Portinho','27909-001','Cabo Frio','104 bl 02','Rua Crisolito','191','',146,'RJ',NULL),('EZGBLX26D1','Centro','27910-640','Macaé','loja 05','Rua Velho Campos,','420','',147,'RJ',142),('HWQJKHPD1Z','Santa Terezinha','48010-060','Alagoinhas','casa','Travessa Dr. João Dantas','68','',150,'BA',NULL),('OSX3AVZ5AD','Visconde de Araujo','27943-631','Macaé','casa','R. Leopoldina de Araujo','185','',151,'RJ',NULL);
/*!40000 ALTER TABLE `franqueado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `franqueadopessoafisica`
--

DROP TABLE IF EXISTS `franqueadopessoafisica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `franqueadopessoafisica` (
  `cpf` varchar(255) NOT NULL,
  `dataNascimento` date NOT NULL,
  `nomeCompleto` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dkyjbad83e6x463tndaqjmnil` (`cpf`),
  CONSTRAINT `FK_h6rdwhyxhgnk0x4c7op02qsbl` FOREIGN KEY (`id`) REFERENCES `franqueado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `franqueadopessoafisica`
--

LOCK TABLES `franqueadopessoafisica` WRITE;
/*!40000 ALTER TABLE `franqueadopessoafisica` DISABLE KEYS */;
INSERT INTO `franqueadopessoafisica` VALUES ('072.258.077-06','1977-10-09','Elinaldo Farias Reis',141),('024.776.147-89','1972-03-26','Wladimir de Lacerda Oliveira',143),('096.780.356-00','1988-02-23','Priscila Macedo Ferreira da Silva',144),('819.921.305-15','1979-05-07','Joelma Santos Mota',145),('822.383.167-87','1949-09-02','Francisca de Fatima Oliveira',146),('821.781.865-72','1954-03-05','Maria da Conceição Gonçalves Santos',150),('086.702.296-50','1987-05-18','Silas Carlos Pereira Bernardo',151);
/*!40000 ALTER TABLE `franqueadopessoafisica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `franqueadopessoajuridica`
--

DROP TABLE IF EXISTS `franqueadopessoajuridica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `franqueadopessoajuridica` (
  `cnpj` varchar(255) DEFAULT NULL,
  `nomeFantasia` varchar(255) NOT NULL,
  `razaoSocial` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_ky3lns0my4mk6wqa133ylu0lb` FOREIGN KEY (`id`) REFERENCES `franqueado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `franqueadopessoajuridica`
--

LOCK TABLES `franqueadopessoajuridica` WRITE;
/*!40000 ALTER TABLE `franqueadopessoajuridica` DISABLE KEYS */;
INSERT INTO `franqueadopessoajuridica` VALUES ('17.909.264/0001-65','Bortoletto Cabo Frio','W L Oliveira Cosméticos ME',142),('16.580.097/0001-99','Bortoletto Macaé','F de Fátima Oliveira Cosméticos ME',147);
/*!40000 ALTER TABLE `franqueadopessoajuridica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meiopagamento`
--

DROP TABLE IF EXISTS `meiopagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meiopagamento` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(45) NOT NULL,
  `tipo` char(1) NOT NULL,
  `method` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meiopagamento`
--

LOCK TABLES `meiopagamento` WRITE;
/*!40000 ALTER TABLE `meiopagamento` DISABLE KEYS */;
INSERT INTO `meiopagamento` VALUES (1,'Boleto Bancário','B',0),(2,'Cartão Visa','C',3),(3,'Cartão Master','C',4),(4,'Cartão Amex','C',4),(5,'Cartão Elo','C',6),(6,'Cartão Diners','C',7),(7,'TEF - Itaú','T',1),(8,'TEF - Bradesco','T',2);
/*!40000 ALTER TABLE `meiopagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordempagamento`
--

DROP TABLE IF EXISTS `ordempagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordempagamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `carrinho` varchar(255) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `dataEnvio` datetime DEFAULT NULL,
  `dataGeracao` datetime DEFAULT NULL,
  `descricao` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `valor` decimal(19,2) NOT NULL,
  `contrato_id` bigint(20) NOT NULL,
  `meiopagamento_id` bigint(20) NOT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `documento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_82cevxjp9lobdo8xihablkvjf` (`contrato_id`),
  CONSTRAINT `FK_82cevxjp9lobdo8xihablkvjf` FOREIGN KEY (`contrato_id`) REFERENCES `contrato` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordempagamento`
--

LOCK TABLES `ordempagamento` WRITE;
/*!40000 ALTER TABLE `ordempagamento` DISABLE KEYS */;
INSERT INTO `ordempagamento` VALUES (47,'5M0FHRFSNRPPGIL85JE6','8a549c62-a593-4607-8407-e4bd1420ee45',NULL,'2014-04-08 15:49:24','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,52,1,NULL,'https://www.akatus.com/boleto/OGE1NDljNjItYTU5My00NjA3LTg0MDctZTRiZDE0MjBlZTQ1.html'),(48,'HFMGXOFQDLUSSDVOBWGO','3a420ec6-fe9e-4610-9aee-7c627e8af0d0',NULL,'2014-04-08 17:49:48','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,53,1,NULL,'https://www.akatus.com/boleto/M2E0MjBlYzYtZmU5ZS00NjEwLTlhZWUtN2M2MjdlOGFmMGQw.html'),(49,'ASFNI0XOANVVN2A13GXN','6b2f57e0-1197-4380-b202-c518ae14e89b',NULL,'2014-04-09 08:08:16','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,54,1,NULL,'https://www.akatus.com/boleto/NmIyZjU3ZTAtMTE5Ny00MzgwLWIyMDItYzUxOGFlMTRlODli.html'),(50,'LU0TQL5X3LSGUWYM1PH0','7a355145-b846-4649-8a69-ec97c66a5ad3',NULL,'2014-04-09 08:18:20','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,55,1,NULL,'https://www.akatus.com/boleto/N2EzNTUxNDUtYjg0Ni00NjQ5LThhNjktZWM5N2M2NmE1YWQz.html'),(51,'P0DATHBRVFS6HYF46HXR','a473abd6-d9af-44dd-b985-fe2b20d0da65',NULL,'2014-04-09 08:36:47','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,56,1,NULL,'https://www.akatus.com/boleto/YTQ3M2FiZDYtZDlhZi00NGRkLWI5ODUtZmUyYjIwZDBkYTY1.html'),(52,'YZVOMXIYZ1WF5SSSRTRV','91885a15-1af9-44ef-af46-e01246719453',NULL,'2014-04-09 08:42:02','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,57,1,NULL,'https://www.akatus.com/boleto/OTE4ODVhMTUtMWFmOS00NGVmLWFmNDYtZTAxMjQ2NzE5NDUz.html'),(53,'ML2UUHSLJRTPGQEHYXM8','563f11c5-b849-4439-8fad-c002b89307ef',NULL,'2014-04-09 08:49:06','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,58,7,NULL,'https://www.akatus.com/tef/NTYzZjExYzUtYjg0OS00NDM5LThmYWQtYzAwMmI4OTMwN2Vm.html'),(54,'DC71AGOWGHNKPMGSQLWA','8bb831d6-9ba4-42b3-87d0-5f57aec4a982',NULL,'2014-04-09 09:12:48','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,59,8,NULL,'https://www.akatus.com/tef/OGJiODMxZDYtOWJhNC00MmIzLTg3ZDAtNWY1N2FlYzRhOTgy.html'),(55,'KCTEEUIFR7XHW9GYYR6O','45a6a5e8-5e7a-46d4-af73-2ff80ad3f2d6',NULL,'2014-04-09 09:41:30','ASSINATURA DE CONTRATO','Aguardando Pagamento',300.00,60,1,NULL,'https://www.akatus.com/boleto/NDVhNmE1ZTgtNWU3YS00NmQ0LWFmNzMtMmZmODBhZDNmMmQ2.html');
/*!40000 ALTER TABLE `ordempagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordempagamentocartaocredito`
--

DROP TABLE IF EXISTS `ordempagamentocartaocredito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordempagamentocartaocredito` (
  `codigosegura` varchar(255) NOT NULL,
  `cpf` varchar(255) NOT NULL,
  `dataExpiracao` date NOT NULL,
  `numero` varchar(255) NOT NULL,
  `portador` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_ji1c9a5tu0rajev4r9xrj5nv6` FOREIGN KEY (`id`) REFERENCES `ordempagamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordempagamentocartaocredito`
--

LOCK TABLES `ordempagamentocartaocredito` WRITE;
/*!40000 ALTER TABLE `ordempagamentocartaocredito` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordempagamentocartaocredito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefone`
--

DROP TABLE IF EXISTS `telefone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telefone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ddd` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `tipo` varchar(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3yacicwiy3asaprk1ohq947ok` (`usuario_id`),
  CONSTRAINT `FK_3yacicwiy3asaprk1ohq947ok` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefone`
--

LOCK TABLES `telefone` WRITE;
/*!40000 ALTER TABLE `telefone` DISABLE KEYS */;
INSERT INTO `telefone` VALUES (159,'22','99988-3940',NULL,'C'),(160,'21','97990-9220',NULL,'R'),(161,'22','981246595',NULL,'C'),(162,'22','26454224',NULL,'R'),(163,'22','988477538',NULL,'C'),(164,'22','27727538',NULL,'R'),(165,'32','91748282',NULL,'C'),(166,'22','999784545',NULL,'R'),(167,'22','999950455',NULL,'C'),(168,'22','27727538',NULL,'R'),(169,'22','998869673',NULL,'C'),(170,'22','26454224',NULL,'R'),(171,'22','981246595',NULL,'C'),(172,'22','27722575',NULL,'R'),(177,'75','92171262',NULL,'C'),(178,'75','12345678',NULL,'R'),(179,'22','998910553',NULL,'C'),(180,'22','12345678',NULL,'R');
/*!40000 ALTER TABLE `telefone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipofranquia`
--

DROP TABLE IF EXISTS `tipofranquia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipofranquia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipofranquia`
--

LOCK TABLES `tipofranquia` WRITE;
/*!40000 ALTER TABLE `tipofranquia` DISABLE KEYS */;
INSERT INTO `tipofranquia` VALUES (4,'Software + Licensa da Marca','Franquia 1',300.00),(5,'Tablet de 7 + Software + Licensa da Marca','Franquia 2',700.00),(6,'Tablet de 10 + Software + Licensa da Marca','Franquia 3',1000.00);
/*!40000 ALTER TABLE `tipofranquia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bloqueado` bit(1) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `celular_id` bigint(20) DEFAULT NULL,
  `residencial_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4tdehxj7dh8ghfc68kbwbsbll` (`email`),
  KEY `FK_htdt5951s4g5tg7c15n0k76nw` (`celular_id`),
  KEY `FK_avcmbltcx5jvq2ewgddt7pm97` (`residencial_id`),
  CONSTRAINT `FK_avcmbltcx5jvq2ewgddt7pm97` FOREIGN KEY (`residencial_id`) REFERENCES `telefone` (`id`),
  CONSTRAINT `FK_htdt5951s4g5tg7c15n0k76nw` FOREIGN KEY (`celular_id`) REFERENCES `telefone` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (141,'','elinaldo.mkt@hotmail.com','615d25093f4f6d743a5c406a3937e98f',159,160),(142,'','bortolettocabofrio@gmail.com','45fce3a011a162749fcae87bb0ef4fc8',161,162),(143,'','wladimir_mc@hotmail.com','8bf68018590c84d86b524e98323847af',163,164),(144,'','ricardo.lawall@hotmail.com','47c53550a65b6297392ce7f7bf1a5329',165,166),(145,'','joelmamotalacerda@hotmail.com','75e37b3676a37699e085d33904ea44a6',167,168),(146,'','francisca.defatima@hotmail.com','be38bbf649df53845a048fc94da22ad6',169,170),(147,'','bortolettomacae@gmail.com','525fabbc2d1b842e180c2516b969344a',171,172),(150,'','cdupmacae@gmail.com','b117200b8534742cb3abcb39d28d4467',177,178),(151,'','silasflpbr@hotmail.com','68873d12ef7fe58ff3399872785a87ae',179,180);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `estado`
--

/*!50001 DROP TABLE IF EXISTS `estado`*/;
/*!50001 DROP VIEW IF EXISTS `estado`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `estado` AS select `allinshopp`.`ps_state`.`iso_code` AS `uf`,`allinshopp`.`ps_state`.`name` AS `nome` from `allinshopp`.`ps_state` where (`allinshopp`.`ps_state`.`id_country` = 58) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-09 11:29:32
