package archives;

// General Libraries
import connections.Conn;

import java.sql.Connection;
import java.sql.Statement;

public class DBClean {
    public Connection conn;
    public Statement stmt;

    public DBClean(){
        Conn c;
        try{
            c = new Conn();
            c.conn.setAutoCommit(false);

            String sp1 = "call UMS_Schema_Creation();";
            String trigger1 = """
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
                                        
                    /* 3. STUDENT */
                    DROP TRIGGER IF EXISTS trigger_student;
                                        
                    DELIMITER $$
                                        
                    CREATE TRIGGER trigger_student
                    BEFORE INSERT ON STUDENT
                    FOR EACH ROW
                    BEGIN
                    SET NEW.EMAIL = CONCAT(NEW.FIRST_NAME, '.', NEW.LAST_NAME, '@hft-europa.de');
                    SET NEW.USER_ID = CONCAT(RIGHT(YEAR(CURRENT_DATE()), 1),IF(MONTH(CURRENT_DATE()) <= 6, '1', '2'),LEFT(NEW.LAST_NAME, 2),LEFT(NEW.FIRST_NAME, 2));
                    SET NEW.PSWD = CONCAT(LEFT(UUID(), 8),LEFT(UUID(), 8));
                    END;
                    $$
                                        
                    DELIMITER ;
                                        
                    /* 4. FEES */
                    DROP TRIGGER IF EXISTS trigger_fees_insert;
                                        
                    DELIMITER $$
                                        
                    CREATE TRIGGER trigger_fees_insert
                    AFTER INSERT ON STUDENT
                    FOR EACH ROW
                    BEGIN
                    INSERT INTO FEES (STUDENT_ID, TUITION_FEES, SEMESTER_FEES, D_TICKET, RESEARCH_VARIABLE, FEES_PAID)
                    VALUES (NEW.IMMA_ID, CASE WHEN UPPER(NEW.INTERNATIONAL_STUDENT) = 'YES' THEN 1500 ELSE 0 END, 200, 0, 0, 'NO');
                    END;
                    $$
                                        
                    DELIMITER ;
                    """;
            String sp2 = "call UML_DML_Statements();";

            // Stored Proc 1
            c.stmt.executeUpdate(sp1);
            c.conn.commit();

            // Trigger 1
            c.stmt.executeUpdate(trigger1);
            c.conn.commit();

            // Stored Proc 2
            c.stmt.executeUpdate(sp2);
            c.conn.commit();

            System.out.println("Alles gut!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DBClean();
    }
}
