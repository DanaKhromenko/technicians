CREATE SCHEMA IF NOT EXISTS `technicians_schema` DEFAULT CHARACTER SET utf8;
USE `technicians_schema`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `portfolio_projects_tools`;
DROP TABLE IF EXISTS `technician_portfolio_project`;
DROP TABLE IF EXISTS `portfolio_projects`;
DROP TABLE IF EXISTS `tools`;
DROP TABLE IF EXISTS `technicians`;
DROP TABLE IF EXISTS `employers`;
DROP TABLE IF EXISTS `users`;

-- ------------------------- --
-- Table structure for Users --
-- ------------------------- --
CREATE TABLE `users` (
  `id` BIGINT(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci UNIQUE NOT NULL,
  `password` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `country` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `city` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `phone` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci UNIQUE,
  `is_deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ------------------------------- --
-- Table structure for Technicians --
-- ------------------------------- --
CREATE TABLE `technicians` (
  `user_id` BIGINT(0) UNSIGNED NOT NULL,
  `open_to_work` BIT(1) NOT NULL DEFAULT b'0',
  `desired_position` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `desired_annual_salary` BIGINT UNSIGNED,
  `annual_salary_currency` VARCHAR(3) CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`user_id`) USING BTREE,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------- --
-- Table structure for Employers --
-- ----------------------------- --
CREATE TABLE `employers` (
  `user_id` BIGINT(0) UNSIGNED NOT NULL,
  `is_hiring` BIT(1) NOT NULL DEFAULT b'0',
  `company_name` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `current_position` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`user_id`) USING BTREE,
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ------------------------- --
-- Table structure for Tools --
-- ------------------------- --
CREATE TABLE `tools` (
  `id` BIGINT(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci UNIQUE NOT NULL,
  `is_deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- -------------------------------------- --
-- Table structure for Portfolio Projects --
-- -------------------------------------- --
CREATE TABLE `portfolio_projects` (
  `id` BIGINT(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `short_project_description` VARCHAR(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `source_code_url` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `interactive_result_url` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `picture_url` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `is_deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ------------------------------------------------------------------------ --
-- Table structure for relations between Technicians and Portfolio Projects --
-- ------------------------------------------------------------------------ --
CREATE TABLE `technician_portfolio_project` (
    `technician_id` BIGINT(0) UNSIGNED NOT NULL,
    `portfolio_project_id` BIGINT(0) UNSIGNED NOT NULL,
    `detailed_project_description` VARCHAR(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`technician_id`, `portfolio_project_id`),
    FOREIGN KEY (`technician_id`) REFERENCES `technicians` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    FOREIGN KEY (`portfolio_project_id`) REFERENCES `portfolio_projects` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- -------------------------------------------- --
-- Table structure for relations between Portfolio Projects and Tools --
-- -------------------------------------------- --
CREATE TABLE `portfolio_projects_tools` (
  `portfolio_project_id` BIGINT(0) UNSIGNED NOT NULL,
  `tool_id` BIGINT(0) UNSIGNED NOT NULL,
  PRIMARY KEY (`portfolio_project_id`, `tool_id`) USING BTREE,
  INDEX `portfolio_project_id`(`portfolio_project_id`) USING BTREE,
  INDEX `tool_id`(`tool_id`) USING BTREE,
  CONSTRAINT `portfolio_project_id` FOREIGN KEY (`portfolio_project_id`) REFERENCES `portfolio_projects` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tool_id` FOREIGN KEY (`tool_id`) REFERENCES `tools` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
