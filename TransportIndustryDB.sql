CREATE DATABASE IF NOT EXISTS transport_industry;
USE transport_industry;
CREATE TABLE IF NOT EXISTS vehicle(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`distance` DOUBLE NOT NULL,
`speed` DOUBLE NOT NULL,
`trip_cost` DOUBLE NOT NULL,
`weight` INT NOT NULL,
`transport_type` ENUM ('AIR','ROAD','RAIL','WATER') NOT NULL);