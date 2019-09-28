-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `service` DEFAULT CHARACTER SET utf8;
USE `service` ;

-- -----------------------------------------------------
-- Table `service`.`t_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_category`
(
    `id`   INT(11)     NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_product_owner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_product_owner`
(
    `id`           INT(11)     NOT NULL AUTO_INCREMENT,
    `first_name`   VARCHAR(45) NULL DEFAULT NULL,
    `last_name`    VARCHAR(45) NULL DEFAULT NULL,
    `company_name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
AUTO_INCREMENT = 122
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_item`
(
    `id`               INT(11)       NOT NULL AUTO_INCREMENT,
    `count`            INT(11)       NULL DEFAULT NULL,
    `price`            DECIMAL(6, 2) NULL DEFAULT NULL,
    `title`            VARCHAR(45)   NULL DEFAULT NULL,
    `date_add`         DATETIME      NULL DEFAULT NULL,
    `product_owner_id` INT(11)       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX              `fk_t_item_t_product_owner1_idx` (`product_owner_id` ASC) VISIBLE,
    CONSTRAINT `fk_t_item_t_product_owner1`
        FOREIGN KEY (`product_owner_id`)
            REFERENCES `service`.`t_product_owner` (`id`)
)
    ENGINE = InnoDB
AUTO_INCREMENT = 81
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_category_has_t_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_category_has_t_item`
(
    `category_id` INT(11) NOT NULL,
    `item_id`     INT(11) NOT NULL,
    PRIMARY KEY (`category_id`, `item_id`),
    INDEX         `fk_t_category_has_t_item_t_item1_idx` (`item_id` ASC) VISIBLE,
    INDEX         `fk_t_category_has_t_item_t_category1_idx` (`category_id` ASC) VISIBLE,
    CONSTRAINT `fk_t_category_has_t_item_t_category1`
        FOREIGN KEY (`category_id`)
            REFERENCES `service`.`t_category` (`id`),
    CONSTRAINT `fk_t_category_has_t_item_t_item1`
        FOREIGN KEY (`item_id`)
            REFERENCES `service`.`t_item` (`id`)
)
    ENGINE = InnoDB
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_type_contact`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_type_contact`
(
    `id`   INT(11)     NOT NULL,
    `type` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `type_UNIQUE` (`type` ASC) VISIBLE
)
    ENGINE = InnoDB
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_email`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_email`
(
    `id`               INT(11)     NOT NULL AUTO_INCREMENT,
    `email`            VARCHAR(45) NULL DEFAULT NULL,
    `type_contact_id`  INT(11)     NOT NULL,
    `product_owner_id` INT(11)     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX              `fk_t_email_type_contact1_idx` (`type_contact_id` ASC) VISIBLE,
    INDEX              `fk_t_emails_t_product_owner1_idx` (`product_owner_id` ASC) VISIBLE,
    CONSTRAINT `fk_t_email_type_contact1`
        FOREIGN KEY (`type_contact_id`)
            REFERENCES `service`.`t_type_contact` (`id`),
    CONSTRAINT `fk_t_emails_t_product_owner1`
        FOREIGN KEY (`product_owner_id`)
            REFERENCES `service`.`t_product_owner` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_type_operation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_type_operation`
(
    `id`   INT(11)     NOT NULL,
    `type` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_operation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_operation`
(
    `id`                INT(11)       NOT NULL AUTO_INCREMENT,
    `count`             INT(11)       NULL DEFAULT NULL,
    `price`             DECIMAL(6, 2) NULL DEFAULT NULL,
    `date_operation`    DATETIME      NULL DEFAULT NULL,
    `type_operation_id` INT(11)       NOT NULL,
    `item_id`           INT(11)       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX               `fk_t_operation_t_type_operation1_idx` (`type_operation_id` ASC) VISIBLE,
    INDEX               `fk_t_operation_t_item1_idx` (`item_id` ASC) VISIBLE,
    CONSTRAINT `fk_t_operation_t_item`
        FOREIGN KEY (`item_id`)
            REFERENCES `service`.`t_item` (`id`),
    CONSTRAINT `fk_t_operation_t_type_operation1`
        FOREIGN KEY (`type_operation_id`)
            REFERENCES `service`.`t_type_operation` (`id`)
)
    ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER
SET = utf8;


-- -----------------------------------------------------
-- Table `service`.`t_phone`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `service`.`t_phone`
(
    `id`               INT(11)     NOT NULL AUTO_INCREMENT,
    `phone`            VARCHAR(45) NULL DEFAULT NULL,
    `type_contact_id`  INT(11)     NULL DEFAULT NULL,
    `product_owner_id` INT(11)     NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX              `fk_t_phone_type_contact_idx` (`type_contact_id` ASC) VISIBLE,
    INDEX              `fk_t_phones_t_product_owner1_idx` (`product_owner_id` ASC) VISIBLE,
    CONSTRAINT `fk_t_phone_type_contact`
        FOREIGN KEY (`type_contact_id`)
            REFERENCES `service`.`t_type_contact` (`id`),
    CONSTRAINT `fk_t_phones_t_product_owner1`
        FOREIGN KEY (`product_owner_id`)
            REFERENCES `service`.`t_product_owner` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
AUTO_INCREMENT = 88
DEFAULT CHARACTER
SET = utf8;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


-- Справочник операций
insert into service.t_type_operation(id, type)
values ('1', 'ADD');
insert into service.t_type_operation(id, type)
values ('2', 'WITHDRAW');

-- Справочник контактов
insert into service.t_type_contact(id, type)
values ('1', 'WORK');
insert into service.t_type_contact(id, type)
values ('2', 'PRIVATE');
