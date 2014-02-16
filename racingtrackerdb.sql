SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `RacingTrackerDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `RacingTrackerDB` ;

-- -----------------------------------------------------
-- Table `RacingTrackerDB`.`tracks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingTrackerDB`.`tracks` (
  `id_tracks` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tracks`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `RacingTrackerDB`.`locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `RacingTrackerDB`.`locations` (
  `id_locations` INT NOT NULL AUTO_INCREMENT,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `altitude` DOUBLE NULL,
  `time` BIGINT NOT NULL,
  `bearing` FLOAT NULL,
  `speed` FLOAT NULL,
  `accuracy` FLOAT NULL,
  `fk_track` INT NOT NULL,
  PRIMARY KEY (`id_locations`),
  INDEX `track_idx` (`fk_track` ASC),
  CONSTRAINT `track`
    FOREIGN KEY (`fk_track`)
    REFERENCES `RacingTrackerDB`.`tracks` (`id_tracks`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
