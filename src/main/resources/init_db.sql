CREATE SCHEMA IF NOT EXISTS `technicians_schema` DEFAULT CHARACTER SET utf8;
USE `technicians_schema`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `portfolio_projects_tools`;
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
  `phone` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci UNIQUE NOT NULL,
  `picture` BLOB,
  `is_deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO users (email, password, name, description, country, city, phone)
VALUES
  ('danadiadius@gmail.com', 'a8f148f432bfcac194a3c691084c09dad4bcc0fb81b14cf90ffa00e6c61bea0e', 'Dana Khromenko',
   'Skilled software developer with 1.5 years of hands-on experience with Java, Golang, AWS, Python, and 7+ years of relevant 1C development experience. Bachelor''s degree in Software Engineering and additional education in Java Development. Proficient in SQL and NoSQL DB, Git, microservice architecture, REST APIs, testing. Experienced in configuration as code with expertise in Terraform, CI/CD pipelines, Jenkins, deployment using Docker, and troubleshooting. Skilled in knowledge sharing.',
   'Ukraine', 'Kyiv', '+380(63)6789-468'),
  ('vadim.khromenko98@gmail.com', '9c5e7ced20ec5524d5a65bf9bdaa2c86605a77336b2982f7ea547e38066090c9', 'Vadym Khromenko',
   'Web developer who completed an internship as a Frontend Trainee. Completed Frontend development bootcamp in 2022. Received a Bachelor''s Degree in Software Engineering in 2020. During this time, have implemented many projects using JavaScript, HTML5, CSS, SCSS.',
   'Ukraine', 'kyiv', '+380(50)858-1118'),
  ('johndoe@example.com', 'password1', 'John Doe', 'IT Recruiter with 5 years of experience', 'USA', 'New York', '123-456-7890'),
  ('janedoe@example.com', 'password2', 'Jane Doe', 'Team Lead with expertise in machine learning', 'USA', 'San Francisco', '234-567-8901'),
  ('bobsmith@example.com', 'password3', 'Bob Smith', 'Director of IT outsourcing company', 'USA', 'Los Angeles', '345-678-9012'),
  ('jimjohnson@example.com', 'password4', 'Jim Johnson', 'Frontend developer with expertise in React and Angular', 'USA', 'Seattle', '456-789-0123'),
  ('marybrown@example.com', 'password5', 'Mary Brown', 'Intern Full-stack developer', 'USA', 'Boston', '567-890-1234');

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

INSERT INTO technicians (user_id, open_to_work, desired_position, desired_annual_salary, annual_salary_currency)
VALUES
  (1, b'1', 'Java Developer', 36000, 'USD'),
  (2, b'1', 'Frontend Developer', 25000, 'USD'),
  (4, b'1', 'Team Lead', 100000, 'USD'),
  (6, b'0', 'Frontend Developer', 0, 'USD'),
  (7, b'0', 'Data Analyst', 12000, 'USD');

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

INSERT INTO employers (user_id, is_hiring, company_name, current_position)
VALUES
  (3, b'1', 'The Solution', 'IT Recruiter'),
  (5, b'0', 'BIT', 'Director');

-- ------------------------- --
-- Table structure for Tools --
-- ------------------------- --
CREATE TABLE `tools` (
  `id` BIGINT(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci UNIQUE NOT NULL,
  `is_deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO tools (name)
VALUES
  ('HTML'),
  ('HTML5'),
  ('CSS'),
  ('SCSS'),
  ('BEM'),
  ('JAVASCRIPT'),
  ('TYPESCRIPT'),
  ('REACT'),
  ('ANGULAR'),
  ('JAVA'),
  ('SPRING FRAMEWORK'),
  ('SPRING BOOT'),
  ('SPRING SECURITY'),
  ('SPRING MVC'),
  ('MAVEN'),
  ('GRADLE'),
  ('JDBC'),
  ('JPA'),
  ('HIBERNATE'),
  ('SERVLETS'),
  ('GOLANG'),
  ('PYTHON'),
  ('MYSQL'),
  ('TOKEN'),
  ('FILTERING'),
  ('AWS'),
  ('AWS LAMBDA'),
  ('AWS CLOUDWATCH'),
  ('DYNAMO DB'),
  ('GIT'),
  ('GIT HUB');

-- -------------------------------------- --
-- Table structure for Portfolio Projects --
-- -------------------------------------- --
CREATE TABLE `portfolio_projects` (
  `id` BIGINT(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `technician_id` BIGINT(0) UNSIGNED NOT NULL,
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` VARCHAR(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci,
  `picture` BLOB,
  `is_deleted` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`) USING BTREE,
  FOREIGN KEY (`technician_id`) REFERENCES `technicians` (`user_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO portfolio_projects (technician_id, name, url, description)
VALUES
  (1, 'Technicians', 'https://github.com/DanaKhromenko/technicians', 'This project aims to assist IT professionals in showcasing their skills and knowledge to potential employers.'),
  (1, 'Taxi Service', 'https://github.com/DanaKhromenko/jv-web-security/tree/hw-web-security-solution', 'The taxi service web application, where drivers and cars can be managed. The project is built using a three-tier architecture and implements CRUD operations and HttpServlet, allowing for efficient data management and request handling. Additionally, the project uses a MySQL database and features a custom injector for enhanced performance and scalability.'),
  (1, 'Cinema', 'https://github.com/DanaKhromenko/jv-spring-stateless/tree/hw-spring-stateless-solution', 'The Cinema Ticket Application. Web application written with Spring, where users, orders, movies, movie sessions etc. can be managed.'),
  (2, 'Technicians', 'https://github.com/DanaKhromenko/technicians', 'This project empowers IT professionals to showcase their skills by providing recruiters and potential employers with access to their projects, highlighting their abilities in a tangible way. On my end, the project features a three-tier architecture implemented using: JavaScript, HTML, CSS, MySQL, Java'),
  (2, 'Miami', 'https://github.com/VadimKhromenko/layout_miami/tree/develop-khromenko', 'Miami Condo Kings landing page. Welcome to Miami Best Real Estate, where we are committed to helping you find your dream home in Miami. Our experienced team of real estate professionals specialize in buying and selling properties across the city, and we pride ourselves on delivering exceptional service and personalized attention to our clients. From luxury waterfront properties to charming suburban homes, we have a wide range of options to fit your needs and budget. Browse our listings and let us help you find your perfect Miami home.');

-- -------------------------------------------- --
-- Table structure for relations between Technicians, Portfolio Projects and Tools --
-- -------------------------------------------- --
CREATE TABLE `portfolio_projects_tools` (
  `portfolio_project_id` BIGINT(0) UNSIGNED NOT NULL,
  `tool_id` BIGINT(0) UNSIGNED NOT NULL,
  PRIMARY KEY (`portfolio_project_id`, `tool_id`),
  FOREIGN KEY (`portfolio_project_id`) REFERENCES `portfolio_projects` (`id`),
  FOREIGN KEY (`tool_id`) REFERENCES tools (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO portfolio_projects_tools (portfolio_project_id, tool_id)
VALUES
  (1, 3),
  (1, 10),
  (1, 11),
  (1, 12),
  (1, 13),
  (1, 14),
  (1, 15),
  (1, 17),
  (1, 18),
  (1, 20),
  (1, 23),
  (1, 25),
  (1, 30),
  (1, 31),
  (2, 1),
  (2, 3),
  (2, 10),
  (2, 15),
  (2, 17),
  (2, 20),
  (2, 23),
  (2, 25),
  (2, 30),
  (2, 31),
  (3, 1),
  (3, 3),
  (3, 10),
  (3, 11),
  (3, 13),
  (3, 14),
  (3, 15),
  (3, 17),
  (3, 20),
  (3, 23),
  (3, 24),
  (3, 25),
  (3, 30),
  (3, 31),
  (4, 1),
  (4, 2),
  (4, 3),
  (4, 4),
  (4, 5),
  (4, 6),
  (4, 15),
  (4, 23),
  (4, 30),
  (4, 31),
  (5, 1),
  (5, 2),
  (5, 3),
  (5, 4),
  (5, 5),
  (5, 6),
  (5, 15),
  (5, 30),
  (5, 31);

SET FOREIGN_KEY_CHECKS = 1;
