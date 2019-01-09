DROP DATABASE IF EXISTS `eicon` ;
CREATE DATABASE IF NOT EXISTS `eicon` ;
##----------------------------------------------------------------------------------------##
DROP table IF EXISTS `eicon`.`Customer`;

CREATE TABLE IF NOT EXISTS `eicon`.`Customer` (
   id int(11) PRIMARY KEY,
   name VARCHAR(50)
   );


INSERT INTO `eicon`.`Customer`
(`id`,
`name`)
VALUES
(1, "Cliente 01"),(2, "Cliente 02"),(3, "Cliente 03"),(4, "Cliente 04"),(5, "Cliente 05"),
(6, "Cliente 06"),(7, "Cliente 07"),(8, "Cliente 08"),(9, "Cliente 09"),(10, "Cliente 10");

#SELECT `Customer`.`id`,`Customer`.`name`
#FROM `eicon`.`Customer`;

##----------------------------------------------------------------------------------------##
DROP table IF EXISTS `eicon`.`Requests`;

CREATE TABLE IF NOT EXISTS `eicon`.`Requests` (
  `id` INT NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `description` VARCHAR(50) NULL,
  `customer_id` INT NULL,
  `quantity` INT NULL,
  `unitary_value` DOUBLE NULL,
  `amount` DOUBLE NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

#insert into `eicon`.`Requests`(`id`,`date`,`description`,`customer_id`,`quantity`,`unitary_value`,`amount`)
#VALUES (1, '2019-01-07', 'Teste 01', 1, 2, 20, 40);

#SELECT `eicon`.`Requests`.*
#FROM `eicon`.`Requests`;

##----------------------------------------------------------------------------------------##