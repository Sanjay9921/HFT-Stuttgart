-- Tip
-- Copy the code and then right click on the thin client

-- 1. use the main database
use koch_universitydb;

-- 2. Show the existing triggers
show triggers;

-- 3. Add gradeAverage column to student table
ALTER TABLE Student
ADD COLUMN gradeAverage DOUBLE(3,1);

-- 4. Update gradeAverage column with initial values
UPDATE Student S
SET S.gradeAverage = (
  SELECT AVG(T.grade) 
  FROM Takes T
  where S.matNr = T.matNr
);

-- 5. Trigger
-- 5.1 Delete trigger if exists
DROP TRIGGER IF EXISTS trigger_student_after_insert;
DROP TRIGGER IF EXISTS trigger_student_after_update;
DROP TRIGGER IF EXISTS trigger_student_after_delete;

-- 5.2 Insert Operation
CREATE TRIGGER trigger_student_after_insert
AFTER INSERT ON Takes
FOR EACH ROW
UPDATE Student S
SET S.gradeAverage = (
  SELECT AVG(T.grade) 
  FROM Takes T
  where T.matNr = NEW.matNr 
)
where s.matNr = NEW.matNr;

-- 5.3 Updates Operation
CREATE TRIGGER trigger_student_after_update
AFTER UPDATE ON Takes
FOR EACH ROW
UPDATE Student S
SET S.gradeAverage = (
  SELECT AVG(T.grade) 
  FROM Takes T
  where T.matNr = NEW.matNr 
)
where s.matNr = NEW.matNr;

-- 5.4 Delete Operation
CREATE TRIGGER trigger_student_after_delete
AFTER DELETE ON Takes
FOR EACH ROW
UPDATE Student S
SET S.gradeAverage = (
  SELECT AVG(T.grade) 
  FROM Takes T
  WHERE T.matNr = OLD.matNr
)
WHERE S.matNr = OLD.matNr;


-- 5.5 Show trigger to test
-- show triggers;

-- 6. Test
update Takes T
set T.grade = 2.3
where T.classNr = 'BSY-SS93';

select * from Student;
select * from Takes;
