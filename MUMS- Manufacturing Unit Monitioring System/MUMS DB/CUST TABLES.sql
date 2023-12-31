CREATE TABLE CUST(
	CNUM INT PRIMARY KEY AUTO_INCREMENT, 
	SNUM CHAR(4),
	CNAME VARCHAR(25) NOT NULL,
	CITY varchar(25) NOT NULL,
	RATING TINYINT NOT NULL,
	EX1 FLOAT,
	EX2 VARCHAR(25),
	UNIQUE (CNAME, CITY),
	CONSTRAINT FK_SALESMAN_CUST FOREIGN KEY(SNUM)
	REFERENCES SALESMAN (SNUM) 
);
SELECT * FROM CUST ORDER BY CNUM;

TRUNCATE TABLE CUST;
DROP TABLE CUST;



INSERT INTO CUST(CNAME, CITY, RATING) VALUES
('AKSHAY JAGTAP','BOMB',8),
('DEVI DINESH','DELH',9),
('ANIKET PATTIWAR','PUNE',6),
('DISHI KANADE ','CHEN',4),
('DIVYANI INGLE','NAGP',7),
('ASHOK PATE','BOMB',5),
('ANUJ GAONKAR','BEED',10),
('ARVIND BIRADAR','DELH',3);


DELIMITER //
CREATE TRIGGER AI_CUST
AFTER INSERT
ON CUST FOR EACH ROW
BEGIN
    INSERT INTO EMP_AUDIT VALUES (NULL, 0, USER(), SYSDATE(), CONCAT(NEW.CNAME, ' CUSTOMER ADDED SUCESSFULLY'));
END; //
DELIMITER ;
DROP TRIGGER AI_CUST;


DELIMITER //
CREATE TRIGGER AU_CUST
AFTER UPDATE
ON CUST FOR EACH ROW
BEGIN
    IF (OLD.SNUM != NEW.SNUM ) THEN
		INSERT INTO EMP_AUDIT VALUES (NULL, NEW.CNUM, USER(), SYSDATE(), CONCAT('CUSTOMER ALLOCATED SALESMAN ', NEW.SNUM,' SUCESSFULLY'));
	ELSEIF (OLD.CNAME != NEW.CNAME) THEN
		INSERT INTO EMP_AUDIT VALUES (NULL, NEW.CNUM, USER(), SYSDATE(), CONCAT('CUSTOMER NAME CHANGED TO ', NEW.CNAME,' FROM ', OLD.CNAME, 'SUCESSFULLY'));
	ELSEIF (OLD.CITY != NEW.CITY) THEN
		INSERT INTO EMP_AUDIT VALUES (NULL, NEW.CNUM, USER(), SYSDATE(), CONCAT('CUSTOMER CITY CHANGED TO ', NEW.CITY,' FROM ', OLD.CITY, 'SUCESSFULLY'));
	ELSEIF (OLD.RATING != NEW.RATING) THEN
		INSERT INTO EMP_AUDIT VALUES (NULL, NEW.CNUM, USER(), SYSDATE(), CONCAT('CUSTOMER RATING CHANGED TO ', NEW.RATING,' FROM ', OLD.RATING, 'SUCESSFULLY'));
	END IF;
END; //
DELIMITER ;
DROP TRIGGER AU_CUST;



DELIMITER //
CREATE PROCEDURE CUST_SALESMAN_ALLOCATION (CNO INT)
BEGIN
	DECLARE TEMP_CITY CHAR(4);
    DECLARE SNO CHAR(4);
    SELECT CITY INTO TEMP_CITY FROM CUST WHERE CNUM=CNO; 
    SELECT SNUM INTO SNO FROM SALESMAN WHERE LOC=TEMP_CITY;
    UPDATE CUST SET SNUM=SNO WHERE CNUM=CNO;
    INSERT INTO EMP_AUDIT VALUES (NULL, SNO, USER(), SYSDATE(), CONCAT('SALESMAN ALLOCATED TO CUSTOMER ', CNO ,' SUCESSFULLY'));
END; //
DELIMITER ;
DROP PROCEDURE CUST_SALESMAN_ALLOCATION;

CALL CUST_SALESMAN_ALLOCATION(1);
CALL CUST_SALESMAN_ALLOCATION(2);
CALL CUST_SALESMAN_ALLOCATION(3);
CALL CUST_SALESMAN_ALLOCATION(4);
CALL CUST_SALESMAN_ALLOCATION(5);
CALL CUST_SALESMAN_ALLOCATION(6);
CALL CUST_SALESMAN_ALLOCATION(7);
CALL CUST_SALESMAN_ALLOCATION(8);

SELECT CITY  FROM CUST WHERE CNUM=1; 