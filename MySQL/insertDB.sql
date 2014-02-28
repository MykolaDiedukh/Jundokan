SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `Jundokan` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `Jundokan` ;

-- -----------------------------------------------------
-- Table `Jundokan`.`Organization`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`Organization` (
  `OrgID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `OrgName` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`OrgID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`Degrees`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`Degrees` (
  `DegreeID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `Degree` VARCHAR(16) NOT NULL ,
  PRIMARY KEY (`DegreeID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`Persons`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`Persons` (
  `PersID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `PersFN` VARCHAR(45) NOT NULL ,
  `PersLN` VARCHAR(45) NOT NULL ,
  `PersMN` VARCHAR(45) NULL ,
  `PersSex` BIT NOT NULL ,
  `PersDate` DATE NOT NULL ,
  `PersWeight` FLOAT NOT NULL ,
  `PersAge` INT NOT NULL ,
  `PersPhoto` VARCHAR(64) NULL ,
  `DegreeID` INT UNSIGNED NOT NULL ,
  `OrgID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`PersID`, `DegreeID`, `OrgID`) ,
  INDEX `fk_persons_degrees1_idx` (`DegreeID` ASC) ,
  INDEX `fk_Persons_Organization1_idx` (`OrgID` ASC) ,
  CONSTRAINT `fk_persons_degrees1`
    FOREIGN KEY (`DegreeID` )
    REFERENCES `Jundokan`.`Degrees` (`DegreeID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Persons_Organization1`
    FOREIGN KEY (`OrgID` )
    REFERENCES `Jundokan`.`Organization` (`OrgID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`DocumentParents`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`DocumentParents` (
  `DParentID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `DParent` VARCHAR(182) NULL ,
  `PersID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`DParentID`, `PersID`) ,
  INDEX `fk_DocumentParents_Persons1_idx` (`PersID` ASC) ,
  CONSTRAINT `fk_DocumentParents_Persons1`
    FOREIGN KEY (`PersID` )
    REFERENCES `Jundokan`.`Persons` (`PersID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`NewGames`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`NewGames` (
  `NGID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `NGName` VARCHAR(90) NOT NULL COMMENT 'Назва гри' ,
  `NGCity` VARCHAR(45) NOT NULL COMMENT 'Місце проведення гри' ,
  `NGStatus` BIT NOT NULL COMMENT 'Поточне змагання чи ні' ,
  `NGDBegin` DATE NOT NULL COMMENT 'Дата початоку змагання' ,
  `NGDEnd` DATE NOT NULL COMMENT 'Кінець змагання' ,
  PRIMARY KEY (`NGID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`TypeGames`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`TypeGames` (
  `TGID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `TGName` VARCHAR(45) NOT NULL COMMENT 'Type game' ,
  `TGPattern` BIT NOT NULL ,
  PRIMARY KEY (`TGID`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`FilterGames`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`FilterGames` (
  `FGID` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'type_games_has_table_x' ,
  `NGID` INT UNSIGNED NOT NULL ,
  `TGID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`FGID`, `NGID`, `TGID`) ,
  INDEX `fk_filter_games_new_games1_idx` (`NGID` ASC) ,
  INDEX `fk_filter_games_name_games1_idx` (`TGID` ASC) ,
  CONSTRAINT `fk_filter_games_new_games1`
    FOREIGN KEY (`NGID` )
    REFERENCES `Jundokan`.`NewGames` (`NGID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_filter_games_name_games1`
    FOREIGN KEY (`TGID` )
    REFERENCES `Jundokan`.`TypeGames` (`TGID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`FilterMembers`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`FilterMembers` (
  `FMID` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'persons_has_table_x' ,
  `FMStatus` BIT(1) NOT NULL COMMENT 'Check status' ,
  `PersID` INT UNSIGNED NOT NULL ,
  `FGID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`FMID`, `PersID`, `FGID`) ,
  INDEX `fk_persons_has_table_x_persons1_idx` (`PersID` ASC) ,
  INDEX `fk_persons_has_table_x_type_games_has_table_x1_idx` (`FGID` ASC) ,
  CONSTRAINT `fk_persons_has_table_x_persons1`
    FOREIGN KEY (`PersID` )
    REFERENCES `Jundokan`.`Persons` (`PersID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_persons_has_table_x_type_games_has_table_x1`
    FOREIGN KEY (`FGID` )
    REFERENCES `Jundokan`.`FilterGames` (`FGID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`Protocols`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`Protocols` (
  `ProtID` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `ProtName` VARCHAR(45) NOT NULL COMMENT 'Pattern' ,
  `ProtData` BLOB NOT NULL COMMENT 'Дані протоколу (HashMap)' ,
  `ProtAge` VARCHAR(45) NOT NULL COMMENT 'Вікова категорія (Age catecory)' ,
  `ProtCategory` VARCHAR(45) NOT NULL COMMENT 'category' ,
  `ProtSex` BIT NOT NULL COMMENT 'Sex' ,
  `FGID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`ProtID`, `FGID`) ,
  INDEX `fk_protocols_of_competitions_filter_games1_idx` (`FGID` ASC) ,
  CONSTRAINT `fk_protocols_of_competitions_filter_games1`
    FOREIGN KEY (`FGID` )
    REFERENCES `Jundokan`.`FilterGames` (`FGID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Jundokan`.`Members`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `Jundokan`.`Members` (
  `ProtID` INT UNSIGNED NOT NULL ,
  `FMID` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`ProtID`, `FMID`) ,
  INDEX `fk_table1_Protocols1_idx` (`ProtID` ASC) ,
  CONSTRAINT `fk_table1_Protocols1`
    FOREIGN KEY (`ProtID` )
    REFERENCES `Jundokan`.`Protocols` (`ProtID` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

USE `Jundokan` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
