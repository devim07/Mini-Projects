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

DELIMITER //
CREATE PROCEDURE MONTHLY_EXP(MON VARCHAR(15), ELE FLOAT, RENT FLOAT, MISC FLOAT)
BEGIN
	DECLARE EXP FLOAT;
    DECLARE INC FLOAT;
    DECLARE BALANCE FLOAT;
    DECLARE SALARY FLOAT;
    INSERT INTO INCM_EXP_TALLY VALUES ('E', ELE, SYSDATE(), CONCAT(MONTHNAME(SYSDATE()),'-',YEAR(SYSDATE()), ' ELECTRICITY BILL'));
	INSERT INTO INCM_EXP_TALLY VALUES ('E', RENT, SYSDATE(), CONCAT(MONTHNAME(SYSDATE()),'-',YEAR(SYSDATE()), ' RENT EXPENDITURE'));
    INSERT INTO INCM_EXP_TALLY VALUES ('E', MISC, SYSDATE(), CONCAT(MONTHNAME(SYSDATE()),'-',YEAR(SYSDATE()), ' MISCELLANEOUS EXPENDITURE'));
    SELECT SUM(TOTSAL) INTO SALARY FROM EMP;
    INSERT INTO INCM_EXP_TALLY VALUES ('E', SALARY, SYSDATE(), CONCAT(MONTHNAME(SYSDATE()),'-',YEAR(SYSDATE()), ' SALARY OF EMPLOYEES'));
    SELECT SUM(AMT) INTO EXP FROM INCM_EXP_TALLY 
    WHERE HEAD='E' AND MONTHNAME(ENTRY_DATE)=MON AND YEAR(ENTRY_DATE)=YEAR(SYSDATE()) GROUP BY HEAD;
    SELECT SUM(AMT) INTO INC FROM INCM_EXP_TALLY 
    WHERE HEAD='I' AND MONTHNAME(ENTRY_DATE)=MON AND YEAR(ENTRY_DATE)=YEAR(SYSDATE()) GROUP BY HEAD;
    SET BALANCE=INC-EXP;
    IF BALANCE>0 THEN
		INSERT INTO INCM_EXP_TALLY VALUES ('P', BALANCE, SYSDATE(), CONCAT('PROFIT ', MONTHNAME(SYSDATE()),'-',YEAR(SYSDATE())));
	ELSE
        INSERT INTO INCM_EXP_TALLY VALUES ('L', BALANCE, SYSDATE(), CONCAT('LOSS ', MONTHNAME(SYSDATE()),'-',YEAR(SYSDATE())));
    END IF;
END; //
DELIMITER ;

CALL MONTHLY_SAL();