/* 1. DDL Statements */

DROP TABLE IF EXISTS ANCESTORS;

CREATE TABLE ANCESTORS(
	pNr INT PRIMARY KEY,
	personName VARCHAR(255),
	gender CHAR(1),
	birthYear INT,
	birthMonth INT,
	motherNr INT,
	fatherNr INT,
	FOREIGN KEY (motherNr) REFERENCES ANCESTORS(pNr)
	ON DELETE SET NULL ON UPDATE CASCADE,
	FOREIGN KEY (fatherNr) REFERENCES ANCESTORS(pNr)
	ON DELETE SET NULL ON UPDATE CASCADE
);

/* 2. DML Statements */

INSERT INTO Ancestors VALUES
(1, "Marie Curie", "f", 1867, 11, NULL, NULL),
(2, "Pierre Curie", "m", 1859, 5, NULL, NULL),
(3, "Irene Joliot-Curie", "f", 1897, 9, 1, 2),
(4, "Eve Curie", "f", 1904, 12, 1, 2),
(5, "Henry Labouisse", "m", 1904, 2, NULL, NULL),
(6, "Frederic Joliot-Curie", "m", 1900, 3, NULL, NULL),
(7, "Anne Peretz", "f", 1937, 7, 4, 5),
(8, "Helene Langevin-Joliot", "f", 1927, 9, 3, 6);

/* 3. Recursive Function */
WITH RECURSIVE DESCENDENTS (pNr, personName, birthYear, birthMonth) AS (
	SELECT pNr, personName, birthYear, birthMonth
	FROM ANCESTORS
	WHERE motherNr IN
	(
		SELECT pNr
		FROM ANCESTORS
		WHERE personName = 'Marie Curie'
	)
	UNION ALL
	(
		SELECT A.pNr, A.personName, A.birthYear, A.birthMonth
		FROM ANCESTORS A, DESCENDENTS D
		WHERE A.motherNr = D.pNr
	)
)
SELECT personName, birthYear
FROM DESCENDENTS
WHERE birthMonth = 9;

/* Notes */
/*

1. Do not use joins in the recursive functions
2. Write the select statement immediately after the recursive function.
3. Union All condition helps check the descendents, otherwise the function only gives the first row as result
*/