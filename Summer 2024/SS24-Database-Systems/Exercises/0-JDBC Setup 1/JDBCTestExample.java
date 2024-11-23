import java.sql.*;

/**
 * @author Dorothee Koch
 * @version March 2023
 * This class is to be used as an example in class.
 */
public class JDBCTestExample {

    /***************************************************
     *  G E T     U S E R     C O N N E C T I O N
     ***************************************************/

    public static Connection getUserConnection() throws SQLException {
        // Ask the user for name and password, create the DB connection
        String user = KeyboardInput.readString("MySQL login: ");
        String password = KeyboardInput.readPassword("Password: ");
        System.out.println();

        /* The class DriverManager handles all open
            connections. The function getConnection takes one
            String argument of the form
            jdbc:<databasesystem>:<drivertype>:<username>/<password>.
            The : are separators used in JDBC. */
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://193.196.143.168/koch_universitydb",
                user, password
        );
        return conn;
    }

    /***************************************************
     *  R E A D    S T U D E N T    G R A D E S
     ***************************************************/

    /**
     * List the classes and the assigned grades
     * of the student with myMatNr
     */
    public static void readStudentGrades(Connection conn, int myMatNr) throws SQLException {
        String classNr; // Attribute of relation Takes
        int grade;      // Attribute of relation Takes

        String query = "SELECT classNr, grade FROM Takes WHERE matNr = ?";

        System.out.println("Query: " + query);

        // Create a statement in the connection conn
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, myMatNr);

        /* Execute the query for the created statement
            and return the resulting set of tuples.
            A result set is similar to a cursor in
            embedded SQL or an iterator in SQLJ. */
        ResultSet result = stmt.executeQuery();

        System.out.println("classNr:  grade:");
        /* next() is null when no objects
            are left. Otherwise goes to the
            next result object. */
        while (result.next()) {
            classNr = result.getString(1);
            /* Read position 1 in the current result tuple
                and return it as a String */
            grade = result.getInt(2);
            /* Read position 2 in the current result tuple
                and return it as an integer */
            System.out.println(classNr + ": " + grade);
        }

    }

    /***************************************************
     *  M A T N R     E X I S T S
     ***************************************************/

    public static boolean matNrExists(Connection conn, int testMatNr) throws SQLException {
        String query = "SELECT * FROM Student WHERE matNr = ?";

        // Create a statement in the connection conn
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, testMatNr);

        ResultSet result = stmt.executeQuery();
        /* Execute the query for the created statement
            and return the resulting set of tuples. */

        return result.next(); // true if the matNr existed, false otherwise
    }

    /***************************************************
     *  E N T E R   N E W   S T U D E N T
     ***************************************************/

    /**
     * Check that matNr does not
     * exist, insert new student or ask for new matNr
     */
    public static void enterNewStudent(Connection conn, int newMatNr) throws SQLException {
        /* Check first if this matNr does not exist already.
            This would better be handled by an exception (for
            instance NonExistingMatNrException), which then should
            be caught and handled in main! */
        while (matNrExists(conn, newMatNr)) {
            System.out.println("This matNr is already taken. "
                    + "Please supply a different one!");
            newMatNr = KeyboardInput.readInt("Type the matNr: ");
        }

        /* Now we are sure a valid new matNr has been provided.
            Read now the name of the new student. */
        /* It's not good style to tear apart the user dialogue
            between main and this method as I've done here.
            I'll change it later! */
        String sName = KeyboardInput.readString("Type the name: ");

        // Now enter the new student into the table.
        String query = "INSERT INTO Student (matNr, sName) VALUES (?, ?)";

        System.out.println("Query: " + query);

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, newMatNr);
        stmt.setString(2, sName);
        stmt.executeUpdate();
    }

    /***************************************************
     *  D E L E T E    S T U D E N T
     ***************************************************/

    /**
     * Delete student with matNr delMatNr from table Student
     */
    public static void deleteStudent(Connection conn, int delMatNr) throws SQLException {
        String query = "DELETE FROM Student WHERE matNr = ?";

        System.out.println("Query: " + query);

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, delMatNr);
        stmt.executeUpdate();
    }

    /***************************************************
     *  M A I N
     ***************************************************/

    public static void main(String[] args) throws SQLException {
        // Ask user identification and get DB connection for user
        Connection conn = getUserConnection();

        boolean cont = true;
        while (cont) { // Offer menue while user wants to continue
            System.out.println("Menue: Enter a letter for an action:");
            System.out.println("r for reading grades");
            System.out.println("n for inserting a new student");
            System.out.println("d for deleting a student");
            System.out.println("x for exiting the program.");

            char choice = KeyboardInput.readChar();

            switch (choice) {
                case 'r' -> {
                    System.out.println("Get a list of grades of a student.");
                    // The matNr of the student whose classes we want
                    int myMatNr = KeyboardInput.readInt("Type the matNr: ");
                    // Read matNr of a student, list the grades
                    readStudentGrades(conn, myMatNr);
                }
                case 'n' -> {
                    System.out.println("You can now insert a new student.");
                    int newMatNr = KeyboardInput.readInt("Type the matNr: ");
                    // Enter a new Student with matNr and sName
                    enterNewStudent(conn, newMatNr);
                }
                case 'd' -> {
                    System.out.println("You can now delete a student.");
                    int delMatNr = KeyboardInput.readInt("Type the matNr: ");
                    // Delete a student chosen by matNr
                    deleteStudent(conn, delMatNr);
                }
                case 'x' -> cont = false; // User wants to stop
            }
            System.out.println();
        }
        conn.close();
    }
}
