package oop;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.regex.Pattern;

// Custom Libraries
import connections.Conn;

public class Student implements Person{

    // Instance Variables
    private String firstName;
    private String lastName;
    private String contact;
    private String dob;
    private String address;
    private String gradeXII;
    private String internationalStudent;
    private String matID;
    private String courseName;
    private String deptName;

    // Non-param Constructor
    public Student(){}

    // Parameterized constructor
    public Student(String firstName, String lastName, String contact, String dateOfBirth, String address,
                   String gradeXII, String internationalStudent, String courseName, String deptName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.dob = dateOfBirth;
        this.address = address;
        this.gradeXII = gradeXII;
        this.internationalStudent = internationalStudent;
        this.matID = getRandomMatID();
        this.courseName = courseName;
        this.deptName = deptName;
    }

    // Getters

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getContact() {
        return this.contact;
    }

    @Override
    public String getDOB() {
        return this.dob;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    public String getMatID() { return this.matID; }

    public String getCourseName() { return this.courseName; }

    public String getDeptName() { return this.deptName; }

    // Setters

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public void setDOB(String dob) {
        this.dob = dob;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setDeptName(String deptName){
        this.deptName = deptName;
    }

    // Custom Functions

    public String getRandomMatID(){
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return String.valueOf(n);
    }

    @Override
    public boolean checkDOB(int threshold){

        // Check if the student is more than 16 years to be able to enroll

        try {
            // For Sanjay
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            // For Tanay
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
            LocalDate birthDate = LocalDate.parse(this.dob, formatter);
            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(birthDate, currentDate);
            return age.getYears() > threshold;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkContact(String contact){
        // German Phone Number Regex
        String regex = "^(\\+49|0)[1-9][0-9]{1,14}$";

        // Compile
        Pattern pattern = Pattern.compile(regex);

        // Match
        return pattern.matcher(contact).matches();
    }

    public boolean checkIfFeesPaid(String immaID){
        Conn c = new Conn();
        System.out.println("Checking for student: " + immaID);

        try{
            String query = """
                    SELECT FEES_PAID
                    FROM FEES F
                    WHERE F.STUDENT_ID =
                    """ + immaID + ";";

            ResultSet rs = c.stmt.executeQuery(query);
            String result = "";

            while (rs.next()) {
                 result = rs.getString("FEES_PAID");
            }

            System.out.println("Did the student pay the fees: " + result + " ?");
            return result.equals("YES");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public int insertIntoDB(Conn c, int threshold){
        if(!firstName.isEmpty() && !lastName.isEmpty() && !contact.isEmpty() && !dob.isEmpty() && !address.isEmpty() && !gradeXII.isEmpty() && !internationalStudent.isEmpty() && !matID.isEmpty()){
            try{
                String query = """
                        INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, CONTACT, DOB, HOME_ADDRESS, GRADE_XII, INTERNATIONAL_STUDENT, IMMA_ID) VALUES ('"""
                        + firstName + "','"
                        + lastName + "','"
                        + contact + "','"
                        + dob + "','"
                        + address + "','"
                        + gradeXII + "','"
                        + internationalStudent + "','"
                        + matID + "')";

                c.stmt.executeUpdate(query);

                if(!checkDOB(threshold) || !checkContact(contact)){
                    return -1;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return -1;
            }

            return 1; // Insertion successful
        }

        return 0; // Values are missing
    }

    public int updateContactDB(Conn c, String stuID, String newContact, String newAddress){
        try{
            if(!newContact.isEmpty()){
                System.out.println("Empty?" + newContact);
                String query = """
                                UPDATE STUDENT
                                SET CONTACT = '
                                """ + newContact + "'"
                        +
                        """
                                WHERE IMMA_ID =
                                """
                        + stuID + ";";
                c.stmt.executeUpdate(query);

                if(!checkContact(newContact)){
                    return -1; // rollback in the outside function
                }
                else{
                    c.conn.commit();
                }
            }

            if(!newAddress.isEmpty()){
                System.out.println("Empty for " + stuID + " ?" + newAddress);
                String query = """
                                UPDATE STUDENT
                                SET HOME_ADDRESS = '
                                """ + newAddress + "'"
                        +
                        """
                                WHERE IMMA_ID =
                                """
                        + stuID + ";";
                c.stmt.executeUpdate(query);

                c.conn.commit();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    public int updateFeesDB(Conn c, String immaID, String d_ticket, String variables){
        try{
            c.conn.setAutoCommit(false);

            if(!variables.isEmpty() && !d_ticket.isEmpty()) {

                String query = """
                        UPDATE FEES
                        SET RESEARCH_VARIABLE = '
                        """ + variables + "', D_TICKET = '" + d_ticket
                        + "', FEES_PAID = 'YES' WHERE STUDENT_ID = '" + immaID + "';";
                c.stmt.executeUpdate(query);

                if (checkIfFeesPaid(immaID)) {
                    return -1; // rollback in the outside function
                } else {
                    c.conn.commit();
                }
            }
            else if(!d_ticket.isEmpty()){
                String query = """
                               UPDATE FEES
                               SET D_TICKET = '
                               """ + d_ticket + "', FEES_PAID = 'YES' WHERE STUDENT_ID = '" + immaID + "';";
                c.stmt.executeUpdate(query);

                if(checkIfFeesPaid(immaID)){
                    return -1; // rollback in the outside function
                }
                else{
                    c.conn.commit();
                }
            }
            else if(!variables.isEmpty()){

                String query = """
                            UPDATE FEES
                            SET RESEARCH_VARIABLE = '
                            """ + variables + "', FEES_PAID = 'YES' WHERE STUDENT_ID = '" + immaID + "';";
                c.stmt.executeUpdate(query);

                if(checkIfFeesPaid(immaID)){
                    return -1; // rollback in the outside function
                }
                else{
                    c.conn.commit();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1;
    }
}
