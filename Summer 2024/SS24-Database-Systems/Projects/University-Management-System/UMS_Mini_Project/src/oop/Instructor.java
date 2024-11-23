package oop;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.regex.Pattern;

// Custom Libraries
import connections.Conn;

public class Instructor implements Person{

    // Instance Variables
    private String firstName;
    private String lastName;
    private String contact;
    private String dob;
    private String address;
    private String hftCabin;
    private String empID;
    private String courseName;
    private String deptName;

    // Non-param constructor
    public Instructor(){}

    // Parameterized constructor
    public Instructor(String firstName, String lastName, String contact, String dateOfBirth, String address,
                   String hftCabin, String courseName, String deptName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.dob = dateOfBirth;
        this.address = address;
        this.hftCabin = hftCabin;
        this.empID = getRandomEmpID();
        this.courseName = courseName;
        this.deptName = deptName;
    }

    // Getters

    @Override
    public String getFirstName() { return this.firstName; }

    @Override
    public String getLastName() { return this.lastName; }

    @Override
    public String getContact() { return this.contact; }

    @Override
    public String getDOB() { return this.dob; }

    @Override
    public String getAddress() { return this.address; }

    public String getEmpID() { return this.empID; }

    public String getHftCabin() { return this.hftCabin; }

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

    public void setHftCabin(String hftCabin){
        this.hftCabin = hftCabin;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setDeptName(String deptName){
        this.deptName = deptName;
    }

    // Custom Functions

    public String getRandomEmpID(){
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return String.valueOf(n);
    }

    @Override
    public boolean checkDOB(int threshold){

        // Check if the instructor is more than 25 years to be able to enroll

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

    public int insertIntoDB(Conn c, int threshold){
        if(!firstName.isEmpty() && !lastName.isEmpty() && !contact.isEmpty() && !dob.isEmpty() && !address.isEmpty() && !hftCabin.isEmpty() && !empID.isEmpty()) {
            try {
                String query = """
                        INSERT INTO INSTRUCTOR (FIRST_NAME, LAST_NAME, CONTACT, DOB, HOME_ADDRESS, HFT_CABIN, EMP_ID) VALUES ('"""
                        + firstName + "','"
                        + lastName + "','"
                        + contact + "','"
                        + dob + "','"
                        + address + "','"
                        + hftCabin + "','"
                        + empID + "')";

                c.stmt.executeUpdate(query);

                if (!checkDOB(threshold) || !checkContact(contact)) {
                    return -1; // values failed error
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

            return 1; // Insertion successful
        }

        return 0; // Values are missing
    }

    public int updateContactDB(Conn c, String empID, String newContact, String newAddress){
        try{
            if(!newContact.isEmpty()){
                String query = """
                                UPDATE INSTRUCTOR
                                SET CONTACT = '
                                """ + newContact + "'"
                        +
                        """
                                WHERE EMP_ID =
                                """
                        + empID + ";";
                c.stmt.executeUpdate(query);

                if(!checkContact(newContact)){
                    return -1; // rollback in the outside function
                }
                else{
                    c.conn.commit();
                }
            }

            if(!newAddress.isEmpty()){
                String query = """
                                UPDATE INSTRUCTOR
                                SET HOME_ADDRESS = '
                                """ + newAddress + "'"
                        +
                        """
                                WHERE EMP_ID =
                                """
                        + empID + ";";
                c.stmt.executeUpdate(query);

                c.conn.commit();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return 1; // worked successfully
    }
}
