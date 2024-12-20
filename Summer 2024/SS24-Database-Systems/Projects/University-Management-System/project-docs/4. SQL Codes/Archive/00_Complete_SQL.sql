/* DDL STATEMENTS */

/* 1. DROP TABLES */

/*1.1*/
DROP TABLE IF EXISTS GRADES;

/*1.2*/
DROP TABLE IF EXISTS FEES;
DROP TABLE IF EXISTS ENROLLMENT;
/* DROP TABLE IF EXISTS CREDENTIALS; */

/*1.3*/
DROP TABLE IF EXISTS COURSE;
DROP table IF EXISTS DEPARTMENT;

/*1.4*/
DROP TABLE IF EXISTS INSTRUCTOR;
DROP TABLE IF EXISTS STUDENT;
DROP TABLE IF EXISTS FINANCE;

/*1.5*/
DROP TABLE IF EXISTS ADMINISTRATOR;
/* DROP TABLE IF EXISTS ACCESS_CONTROL; */

/* 2. SCHEMA CREATION */

/*2.1*/
CREATE TABLE ADMINISTRATOR(
	USER_ID VARCHAR(16) PRIMARY KEY,
	PSWD VARCHAR(16),
	FIRST_NAME VARCHAR(255),
	LAST_NAME VARCHAR(255),
	CONTACT VARCHAR(17),
	EMAIL VARCHAR(255) /* Trigger to generate regex email -> firstname.lastname@hft-europa.com*/
);

/* STATIC TABLE */
/* 
  CREATE TABLE ACCESS_CONTROL(
	CATEGORY_ID INT AUTO_INCREMENT PRIMARY KEY,
	CATEGORY_NAME VARCHAR(5) CHECK (
		CATEGORY_NAME IN (
			'ADM', 'INS', 'STU', 'FIN'
		)
	),
	ACCESS_TABLE_LIST VARCHAR(255) CHECK (
		ACCESS_TABLE_LIST IN (
			'ADMINISTRATOR', 'ACCESS_CONTROL', 'STUDENT', 'FINANCE',
            'DEPARTMENT', 'COURSE', 'FEES', 'ENROLLMENT', 'CREDENTIALS'
        )
	)
); */

/*2.2*/
CREATE TABLE INSTRUCTOR(
	USER_ID VARCHAR(16) PRIMARY KEY,
	PSWD VARCHAR(16),
	FIRST_NAME VARCHAR(255),
	LAST_NAME VARCHAR(255),
	CONTACT VARCHAR(15),
	EMAIL VARCHAR(50), /* Trigger to generate regex email -> firstname.lastname@hft-europa.com*/
	DOB DATE,
	ADDRESS VARCHAR(255),
	BUILDING_NO INT(5), /* Bau */
	ROOM_NO VARCHAR(100)
);

CREATE TABLE STUDENT(
	USER_ID VARCHAR(16) PRIMARY KEY,
	PSWD VARCHAR(16),
	FIRST_NAME VARCHAR(255),
	LAST_NAME VARCHAR(255),
	CONTACT VARCHAR(15),
	EMAIL VARCHAR(50), /* Trigger to generate regex email -> 
	[last digit of current year][current semester]
	[first two chars of last name][first two chars of first name]
	[COURSE_CODE]
	@hft-europa.com*/
	DOB DATE,
    ADDRESS VARCHAR(255)
);

CREATE TABLE FINANCE(
	USER_ID VARCHAR(16) PRIMARY KEY,
	PSWD VARCHAR(16),
	FIRST_NAME VARCHAR(255),
	LAST_NAME VARCHAR(255),
	CONTACT VARCHAR(15),
	EMAIL VARCHAR(50), /* Trigger to generate regex email -> firstname.lastname@hft-europa.com*/
	DOB DATE,
    ADDRESS VARCHAR(255),
    BUILDING_NO INT(5), /* Bau */
    ROOM_NO VARCHAR(100)
);

/*2.3*/
CREATE TABLE DEPARTMENT(
	DEPT_ID INT PRIMARY KEY,
	DEAN_ID VARCHAR(16),
	DEPT_NAME VARCHAR(255),
	FOREIGN KEY (DEAN_ID) REFERENCES INSTRUCTOR(USER_ID)
		ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE COURSE(
	COURSE_ID INT PRIMARY KEY,
	DEPT_ID INT,
	INSTRUCTOR_ID VARCHAR(16),
	COURSE_NAME VARCHAR(255),
	COURSE_CODE VARCHAR(10), /* Master Software Technology -> mst */
	CREDITS INT,
	BUILDING_NO INT,
	ROOM_NO INT,
	FOREIGN KEY (DEPT_ID) REFERENCES DEPARTMENT(DEPT_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (INSTRUCTOR_ID) REFERENCES INSTRUCTOR(USER_ID)
	ON DELETE SET NULL ON UPDATE CASCADE 
);

/*2.4*/
CREATE TABLE FEES(
	STAFF_ID VARCHAR(16),
	STUDENT_ID VARCHAR(16),
	TUITION_FEES INT,
	SEMESTER_FEES INT,
	FINES INT,
	FEES_PAID VARCHAR(1) CHECK ( 
		FEES_PAID IN (
			'Y','N'
		)
	),
	FOREIGN KEY (STAFF_ID) REFERENCES FINANCE(USER_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(USER_ID)
	ON DELETE SET NULL ON UPDATE CASCADE 
);

CREATE TABLE ENROLLMENT(
	IMMA_ID VARCHAR(16) PRIMARY KEY,
	STUDENT_ID VARCHAR(16),
	DEPT_ID INT,
	COURSE_ID INT,
	IMMA_DATE DATE,
	IMMA_STATUS VARCHAR(100),
	CURRENT_SEMESTER INT CHECK ( /* restricted list */
		CURRENT_SEMESTER IN (
			1,2,3,4,5
		)
	),
	FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(USER_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (DEPT_ID) REFERENCES DEPARTMENT(DEPT_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
	ON DELETE SET NULL ON UPDATE CASCADE
);

/* CREATE TABLE CREDENTIALS(
	USER_ID VARCHAR(16),
	CATEGORY_ID INT,
	USERNAME VARCHAR(255),
	PASSWORD VARCHAR(255)
); */

/*2.5*/
CREATE TABLE GRADES(
	IMMA_ID VARCHAR(16),
	COURSE_ID INT,
	SEMESTER INT,
	YEAR INT,
	GRADE FLOAT,
	FOREIGN KEY (IMMA_ID) REFERENCES ENROLLMENT(IMMA_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
	ON DELETE SET NULL ON UPDATE CASCADE
);

/**************************************************************************************************************/

/* TRIGGERS */

/* 1. ADMINISTRATOR */
DROP TRIGGER IF EXISTS trigger_admin;

DELIMITER $$

CREATE TRIGGER trigger_admin
BEFORE INSERT ON ADMINISTRATOR
FOR EACH ROW
BEGIN
SET NEW.PSWD = CONCAT(LEFT(UUID(), 8),LEFT(UUID(), 8));
SET NEW.EMAIL = CONCAT(NEW.FIRST_NAME, '.', NEW.LAST_NAME, '@hft-europa.de');
SET NEW.USER_ID = CONCAT(RIGHT(YEAR(CURRENT_DATE()), 1),IF(MONTH(CURRENT_DATE()) <= 6, '1', '2'),LEFT(NEW.LAST_NAME, 2),LEFT(NEW.FIRST_NAME, 2));
END;
$$

DELIMITER ;

/* 2. INSTRUCTOR */
DROP TRIGGER IF EXISTS trigger_instructor;

DELIMITER $$

CREATE TRIGGER trigger_instructor
BEFORE INSERT ON INSTRUCTOR
FOR EACH ROW
BEGIN
SET NEW.EMAIL = CONCAT(NEW.FIRST_NAME, '.', NEW.LAST_NAME, '@hft-europa.de');
SET NEW.USER_ID = CONCAT(RIGHT(YEAR(CURRENT_DATE()), 1),IF(MONTH(CURRENT_DATE()) <= 6, '1', '2'),LEFT(NEW.LAST_NAME, 2),LEFT(NEW.FIRST_NAME, 2));
SET NEW.PSWD = CONCAT(LEFT(UUID(), 8),LEFT(UUID(), 8));
END;
$$

DELIMITER ;

/* 3. FINANCE */
DROP TRIGGER IF EXISTS trigger_finance;

DELIMITER $$

CREATE TRIGGER trigger_finance
BEFORE INSERT ON FINANCE
FOR EACH ROW
BEGIN
SET NEW.EMAIL = CONCAT(NEW.FIRST_NAME, '.', NEW.LAST_NAME, '@hft-europa.de');
SET NEW.USER_ID = CONCAT(RIGHT(YEAR(CURRENT_DATE()), 1),IF(MONTH(CURRENT_DATE()) <= 6, '1', '2'),LEFT(NEW.LAST_NAME, 2),LEFT(NEW.FIRST_NAME, 2));
SET NEW.PSWD = CONCAT(LEFT(UUID(), 8),LEFT(UUID(), 8));
END;
$$

DELIMITER ;

/* 4. STUDENT */
DROP TRIGGER IF EXISTS trigger_student;

DELIMITER $$

CREATE TRIGGER trigger_student
BEFORE INSERT ON FINANCE
FOR EACH ROW
BEGIN
SET NEW.EMAIL = CONCAT(NEW.FIRST_NAME, '.', NEW.LAST_NAME, '@hft-europa.de');
SET NEW.USER_ID = CONCAT(RIGHT(YEAR(CURRENT_DATE()), 1),IF(MONTH(CURRENT_DATE()) <= 6, '1', '2'),LEFT(NEW.LAST_NAME, 2),LEFT(NEW.FIRST_NAME, 2));
SET NEW.PSWD = CONCAT(LEFT(UUID(), 8),LEFT(UUID(), 8));
END;
$$

DELIMITER ;

/**************************************************************************************************************/

/* DML STATEMENTS */

/*3.1 ADMINISTRATOR */

INSERT INTO ADMINISTRATOR (FIRST_NAME, LAST_NAME, CONTACT) VALUES ('Ryan', 'Gosling', '+49 15511234567');
INSERT INTO ADMINISTRATOR (FIRST_NAME, LAST_NAME, CONTACT) VALUES ('Patrick', 'Bateman', '+49 15512345671');
INSERT INTO ADMINISTRATOR (FIRST_NAME, LAST_NAME, CONTACT) VALUES ('Lou', 'Bloom', '+49 15513456712');

/* 3.2 DEPARTMENT */

INSERT INTO DEPARTMENT (DEPT_ID, DEPT_NAME, DEAN_ID) VALUES (1,'Computer Science', NULL);
INSERT INTO DEPARTMENT (DEPT_ID, DEPT_NAME, DEAN_ID) VALUES (2,'Mathematics', NULL);
INSERT INTO DEPARTMENT (DEPT_ID, DEPT_NAME, DEAN_ID) VALUES (3,'Business', NULL);
INSERT INTO DEPARTMENT (DEPT_ID, DEPT_NAME, DEAN_ID) VALUES (4,'Building Physics', NULL);

/* 3.3 COURSE */

/* Computer Science */
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (11, 1, NULL, 'Bachelor of Computer Science', 'bcs', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (12, 1, NULL, 'Bachelor of Augmented Reality', 'bar', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (13, 1, NULL, "Bachelor's degree in digitalization and information management", 'bdi', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (14, 1, NULL, 'Bachelor Surveying and Geoinformatics', 'bsg', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (15, 1, NULL, 'Master Photogrammetry and Geoinformatics', 'mpg', 90, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (16, 1, NULL, 'Master Software Technology', 'mst', 90, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (17, 1, NULL, 'Master Digital Processes and Technologies', 'mdp', 90, NULL, NULL);

/* Mathematics */
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (21, 2, NULL, 'Bachelor Applied Mathematics and AI', 'bam', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (22, 2, NULL, 'Master Mathematics', 'mam', 90, NULL, NULL);

/* Business */
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (31, 3, NULL, 'Bachelor of Business Administration', 'bba', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (32, 3, NULL, 'Business Administration International Business course', 'bai', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (33, 3, NULL, 'Bachelor of Infrastructure Management', 'bim', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (34, 3, NULL, 'Bachelor of Business Informatics ', 'bbi', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (35, 3, NULL, 'Master General Management', 'mgm', 90, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (36, 3, NULL, 'Master Environmentally Oriented Logistics', 'meo', 90, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (37, 3, NULL, "Master's degree in business psychology", 'mdb', 90, NULL, NULL);

/* Building Physics */
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (41, 4, NULL, 'Bachelor of Building Physics', 'bbp', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (42, 4, NULL, 'Bachelor of Climate Engineering', 'bce', 210, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (43, 4, NULL, 'Master Building Physics', 'mbp', 90, NULL, NULL);
INSERT INTO COURSE (COURSE_ID, DEPT_ID, INSTRUCTOR_ID, COURSE_NAME, COURSE_CODE, CREDITS, BUILDING_NO, ROOM_NO) VALUES (44, 4, NULL, 'Master in Sustainable Energy Competence', 'mse', 90, NULL, NULL);

/**************************************************************************************************************/

