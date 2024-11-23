--Ex 3 Part C

/* 1. DDL Statements */
CREATE TABLE TACandidates
(matNr INT(11),
classNr VARCHAR(9),
pName VARCHAR(15),
PRIMARY KEY(matNr, classNr));

/* 2. Triggers */
CREATE TRIGGER findTACandidates
AFTER INSERT ON Takes
FOR EACH ROW
INSERT INTO TACandidates
(SELECT NEW.matNr, NEW.classNr, Class.pName
FROM Class
WHERE Class.classNr = NEW.classNr
AND NEW.grade <= 3);

DELIMITER //

CREATE TRIGGER looping
AFTER INSERT ON TACandidates
FOR EACH ROW
BEGIN
INSERT INTO Student values (NEW.matNr +1, 'Koch');
INSERT INTO Takes values (NEW.matNr + 1, 'DTB-SS93', 1.0);
END//

DELIMITER ;

/* 3. DML Statements */
INSERT INTO Student values (6000, 'Koch');
INSERT INTO Takes VALUES (6000, 'DTB-SS93', 1.0);