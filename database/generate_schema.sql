-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema rasadnik_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rasadnik_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rasadnik_db` DEFAULT CHARACTER SET utf8 ;
USE `rasadnik_db` ;

-- -----------------------------------------------------
-- Table `rasadnik_db`.`plant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`plant` (
  `plant_id` INT NOT NULL AUTO_INCREMENT,
  `scientific_name` VARCHAR(100) NOT NULL,
  `known_as` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `image` MEDIUMBLOB NOT NULL,
  `is_conifer` TINYINT NOT NULL DEFAULT 0,
  `owned` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`plant_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`price_height_ratio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`price_height_ratio` (
  `date_from` DATE NOT NULL,
  `height_min` DECIMAL(10,2) NOT NULL,
  `plant_id` INT NOT NULL,
  `height_max` DECIMAL(10,2) NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`date_from`, `height_min`, `plant_id`),
  CONSTRAINT `fk_price_height_ratio_plant`
    FOREIGN KEY (`plant_id`)
    REFERENCES `rasadnik_db`.`plant` (`plant_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`basis`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`basis` (
  `basis_id` INT NOT NULL AUTO_INCREMENT,
  `planting_date` DATE NOT NULL,
  `active` TINYINT NOT NULL DEFAULT 1,
  `plant_id` INT NOT NULL,
  PRIMARY KEY (`basis_id`),
  INDEX `fk_basis_plant1_idx` (`plant_id` ASC),
  CONSTRAINT `fk_basis_plant1`
    FOREIGN KEY (`plant_id`)
    REFERENCES `rasadnik_db`.`plant` (`plant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`reproduction_cutting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`reproduction_cutting` (
  `date` DATE NOT NULL,
  `basis_id` INT NOT NULL,
  `produces` INT NOT NULL DEFAULT 0,
  `take_a_root` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`basis_id`, `date`),
  CONSTRAINT `fk_reproduction_cutting_basis1`
    FOREIGN KEY (`basis_id`)
    REFERENCES `rasadnik_db`.`basis` (`basis_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`region` (
  `region_id` INT NOT NULL AUTO_INCREMENT,
  `number_of_plants` INT NOT NULL DEFAULT 0,
  `basis_id` INT NOT NULL,
  PRIMARY KEY (`region_id`),
  INDEX `fk_region_basis1_idx` (`basis_id` ASC),
  CONSTRAINT `fk_region_basis1`
    FOREIGN KEY (`basis_id`)
    REFERENCES `rasadnik_db`.`basis` (`basis_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`plant_maintance_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`plant_maintance_activity` (
  `plant_maintance_activity_id` INT NOT NULL AUTO_INCREMENT,
  `activity` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`plant_maintance_activity_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`task` (
  `task_id` INT NOT NULL AUTO_INCREMENT,
  `date_from` DATE NOT NULL,
  `date_to` DATE NULL,
  `done` TINYINT NOT NULL DEFAULT 0,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `region_id` INT NOT NULL,
  `plant_maintance_activity_id` INT NOT NULL,
  INDEX `fk_region_has_maintance_activity_maintance_activity1_idx` (`plant_maintance_activity_id` ASC),
  INDEX `fk_region_has_maintance_activity_region1_idx` (`region_id` ASC),
  PRIMARY KEY (`task_id`),
  CONSTRAINT `fk_region_has_maintance_activity_region1`
    FOREIGN KEY (`region_id`)
    REFERENCES `rasadnik_db`.`region` (`region_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_region_has_maintance_activity_maintance_activity1`
    FOREIGN KEY (`plant_maintance_activity_id`)
    REFERENCES `rasadnik_db`.`plant_maintance_activity` (`plant_maintance_activity_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`employee` (
  `employee_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(75) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`employee_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`pricelist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`pricelist` (
  `pricelist_id` INT NOT NULL AUTO_INCREMENT,
  `date_from` DATE NOT NULL,
  `date_to` DATE NULL,
  `active` TINYINT NOT NULL,
  PRIMARY KEY (`pricelist_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`pricelist_has_plant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`pricelist_has_plant` (
  `pricelist_id` INT NOT NULL,
  `plant_id` INT NOT NULL,
  PRIMARY KEY (`pricelist_id`, `plant_id`),
  INDEX `fk_pricelist_has_plant_plant1_idx` (`plant_id` ASC),
  INDEX `fk_pricelist_has_plant_pricelist1_idx` (`pricelist_id` ASC),
  CONSTRAINT `fk_pricelist_has_plant_pricelist1`
    FOREIGN KEY (`pricelist_id`)
    REFERENCES `rasadnik_db`.`pricelist` (`pricelist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pricelist_has_plant_plant1`
    FOREIGN KEY (`plant_id`)
    REFERENCES `rasadnik_db`.`plant` (`plant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`employee_has_task`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`employee_has_task` (
  `date` DATE NOT NULL,
  `task_id` INT NOT NULL,
  `employee_id` INT NOT NULL,
  `hourly_wage` DECIMAL(10,2) NOT NULL,
  `hours` INT NOT NULL,
  `paid_off` TINYINT NOT NULL,
  PRIMARY KEY (`date`, `task_id`, `employee_id`),
  INDEX `fk_task_has_employee_employee1_idx` (`employee_id` ASC),
  INDEX `fk_task_has_employee_task1_idx` (`task_id` ASC),
  CONSTRAINT `fk_task_has_employee_task1`
    FOREIGN KEY (`task_id`)
    REFERENCES `rasadnik_db`.`task` (`task_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_has_employee_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `rasadnik_db`.`employee` (`employee_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`customer` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(70) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `is_supplier` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`customer_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`purchase` (
  `purchase_id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `paid_off` TINYINT NOT NULL DEFAULT 0,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`purchase_id`),
  INDEX `fk_purchase_customer1_idx` (`customer_id` ASC),
  CONSTRAINT `fk_purchase_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `rasadnik_db`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`sale`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`sale` (
  `sale_id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0,
  `paid_off` TINYINT NOT NULL,
  `customer_id` INT NOT NULL,
  PRIMARY KEY (`sale_id`),
  INDEX `fk_sale_customer1_idx` (`customer_id` ASC),
  CONSTRAINT `fk_sale_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `rasadnik_db`.`customer` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`sale_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`sale_item` (
  `pricelist_id` INT NOT NULL,
  `plant_id` INT NOT NULL,
  `sale_id` INT NOT NULL,
  `count` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`pricelist_id`, `plant_id`, `sale_id`),
  INDEX `fk_pricelist_has_plant_has_sale_sale1_idx` (`sale_id` ASC),
  INDEX `fk_pricelist_has_plant_has_sale_pricelist_has_plant1_idx` (`pricelist_id` ASC, `plant_id` ASC),
  CONSTRAINT `fk_pricelist_has_plant_has_sale_pricelist_has_plant1`
    FOREIGN KEY (`pricelist_id` , `plant_id`)
    REFERENCES `rasadnik_db`.`pricelist_has_plant` (`pricelist_id` , `plant_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pricelist_has_plant_has_sale_sale1`
    FOREIGN KEY (`sale_id`)
    REFERENCES `rasadnik_db`.`sale` (`sale_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`transaction` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `amount` DECIMAL(10,2) NOT NULL,
  `type` TINYINT NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`transaction_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`event` (
  `event_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `date` DATE NOT NULL,
  `done` TINYINT NOT NULL,
  PRIMARY KEY (`event_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`tool`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`tool` (
  `tool_id` INT NOT NULL AUTO_INCREMENT,
  `tool_name` VARCHAR(100) NOT NULL,
  `count` INT NOT NULL DEFAULT 0,
  `is_machine` TINYINT NOT NULL,
  PRIMARY KEY (`tool_id`),
  UNIQUE INDEX `tool_name_UNIQUE` (`tool_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`item_condition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`item_condition` (
  `item_condition_id` INT NOT NULL AUTO_INCREMENT,
  `item_condition` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`item_condition_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`tool_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`tool_item` (
  `tool_item_id` INT NOT NULL AUTO_INCREMENT,
  `next_service_date` DATE NOT NULL,
  `is_deleted` TINYINT NOT NULL DEFAULT 0,
  `tool_id` INT NOT NULL,
  `item_condition_id` INT NOT NULL,
  PRIMARY KEY (`tool_item_id`),
  INDEX `fk_tool_item_tool1_idx` (`tool_id` ASC),
  INDEX `fk_tool_item_condition1_idx` (`item_condition_id` ASC),
  CONSTRAINT `fk_tool_item_tool1`
    FOREIGN KEY (`tool_id`)
    REFERENCES `rasadnik_db`.`tool` (`tool_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tool_item_condition1`
    FOREIGN KEY (`item_condition_id`)
    REFERENCES `rasadnik_db`.`item_condition` (`item_condition_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rasadnik_db`.`tool_maintance_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `rasadnik_db`.`tool_maintance_activity` (
  `date` DATE NOT NULL,
  `tool_item_id` INT NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `up_to_date_service` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`date`, `tool_item_id`),
  INDEX `fk_tool_maintance_activity_tool_item1_idx` (`tool_item_id` ASC),
  CONSTRAINT `fk_tool_maintance_activity_tool_item1`
    FOREIGN KEY (`tool_item_id`)
    REFERENCES `rasadnik_db`.`tool_item` (`tool_item_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
