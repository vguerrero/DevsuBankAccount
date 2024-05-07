-- bank.account definition

CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `client_id` bigint NOT NULL,
  `number` varchar(255) DEFAULT NULL,
  `state` bit(1) NOT NULL,
  `type` enum('CORRIENTE','AHORRO') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dbfiubqahb32ns85k023gr6nn` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- bank.atransaction definition

CREATE TABLE `atransaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_number` varchar(255) DEFAULT NULL,
  `balance` double NOT NULL,
  `client_id` bigint NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `transaction_type` enum('DEPOSITO','RETIRO') DEFAULT NULL,
  `value` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;