package jdbc.test.project;

import java.sql.*;

/**
 * @author Dorothee Koch
 * @version March 2023
 * This class is to be used as an example in class.
 */
public class Student {
    public static void main(String[] args) throws SQLException {
        /* We assume the existence of a class KeyboardInput
            providing class methods for reading various data
            types, here a String. */
        // Username and password for database login
        String user = KeyboardInput.readString("MySQL login: ");
        String password = KeyboardInput.readPassword("Password: ");
        System.out.println();

        /* The class DriverManager handles all open
            connections. The function getConnection takes one
            String argument of the form
            jdbc:<databasesystem>:<drivertype>:<username>/<password>.
            The : are separators used in JDBC.  */
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://193.196.143.168/dk4s_41kusa1mst", user, password
        );

        // The matNr of the student whose classes we want
        int myMatNr = KeyboardInput.readInt("Type a matNr: ");

        // Create a statement in the connection conn
        String query = "select classNr, grade from Takes where matNr = " + myMatNr;

        /* Execute the query for the created statement
            and return the resulting set of tuples.
            A result set is similar to a cursor in
            embedded SQL or an iterator in SQLJ. */
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(query);

        String classNr; // Attribute of relation Takes
        int grade;      // Attribute of relation Takes

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

        conn.close(); // Close the open database connection
    }
}