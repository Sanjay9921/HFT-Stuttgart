--Ex 3 Part B
CREATE TRIGGER VIRUS
AFTER INSERT ON Student
FOR EACH ROW
DELETE FROM Student S
WHERE S.matNr = NEW.matNr;