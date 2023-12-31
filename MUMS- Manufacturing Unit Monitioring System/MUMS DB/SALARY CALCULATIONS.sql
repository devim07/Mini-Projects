/*PROCEDURE FOR CALCULATING TOTAL SALARY=BASIC+HRA FOR ALL EMPLOYEES*/
DELIMITER //
CREATE FUNCTION TOTSALCAL(EMPNO CHAR(4), H FLOAT)
RETURNS FLOAT
DETERMINISTIC
BEGIN
	DECLARE SALARY FLOAT;
    DECLARE SALPDAY FLOAT;
    DECLARE HOLI TINYINT;
    DECLARE COM FLOAT;
    DECLARE TOT FLOAT;
    SELECT BASIC_SAL, HOLIDAYS INTO SALARY, HOLI FROM EMP
    WHERE ENUM=EMPNO;
    SET SALPDAY=SALARY/30;
	SET TOT=SALARY-(SALPDAY*HOLI)+(H*SALARY);
    RETURN TOT;
END; //
DELIMITER ;
DROP FUNCTION TOTSALCAL;


DELIMITER //
CREATE PROCEDURE SALES_COMMISSION()
BEGIN
	DECLARE COM FLOAT;
    DECLARE ORD_TOT FLOAT;
    DECLARE SNO CHAR(4);
    DECLARE HRA_TEMP FLOAT;
    DECLARE FINAL_COMM FLOAT;
    DECLARE H INT DEFAULT 0;
    DECLARE CU CURSOR FOR 
    SELECT SALESMAN.SNUM, COMM, HRA, SUM(AMT) FROM ORDERS,EMP, SALESMAN
    WHERE ORDERS.SNUM=SALESMAN.SNUM AND SALESMAN.SNUM=EMP.ENUM
    AND MONTH(ORDER_DATE)=MONTH(SYSDATE()) AND YEAR(ORDER_DATE)=YEAR(SYSDATE())
    GROUP BY SALESMAN.SNUM;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET H=1;
    OPEN CU ;
		CU_LOOP:LOOP
			FETCH CU INTO SNO, COM, HRA_TEMP, ORD_TOT;
            IF H=1 THEN
				LEAVE CU_LOOP;
			END IF;
            UPDATE EMP SET TOTSAL=TOTSALCAL(SNO, HRA_TEMP)+(COM*ORD_TOT) WHERE ENUM=SNO;
            INSERT INTO EMP_AUDIT VALUES (NULL,SNO, USER(), SYSDATE(), CONCAT(SNO,' MONTHLY SAL+COMMISSION UPDATED IN TOTAL SALARY'));
        END LOOP CU_LOOP;
    CLOSE CU;
END; //
DELIMITER ;
DROP PROCEDURE SALES_COMMISSION;
CALL  SALES_COMMISSION;



DELIMITER //
CREATE PROCEDURE MONTHLY_SAL()
BEGIN
	DECLARE ENO CHAR(4);
    DECLARE TEMP_HRA FLOAT;
    DECLARE FINAL_SAL FLOAT;
    DECLARE H INT DEFAULT 0;
    DECLARE CU CURSOR FOR 
    SELECT ENUM, HRA FROM EMP WHERE ENUM NOT LIKE 'S%' OR ENUM='S001';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET H=1;
    OPEN CU ;
		CU_LOOP:LOOP
			FETCH CU INTO ENO, TEMP_HRA;
            IF H=1 THEN
				LEAVE CU_LOOP;
			END IF;
            SET FINAL_SAL=TOTSALCAL(ENO, TEMP_HRA);
            UPDATE EMP SET TOTSAL=FINAL_SAL WHERE ENUM=ENO;
            INSERT INTO EMP_AUDIT VALUES (NULL, ENO, USER(), SYSDATE(), CONCAT(ENO,' MONTHLY SAL UPDATED IN TOTAL SALARY'));
        END LOOP CU_LOOP;
    CLOSE CU;
    CALL SALES_COMMISSION();
END; //
DELIMITER ;
DROP PROCEDURE MONTHLY_SAL;

CALL MONTHLY_SAL;