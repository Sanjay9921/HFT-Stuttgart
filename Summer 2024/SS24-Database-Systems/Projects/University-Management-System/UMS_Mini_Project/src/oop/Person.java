package oop;

// Interface for a person object
// Useful to be used as a template to add more users

public interface Person {

    // Getters
    public String getFirstName();
    public String getLastName();
    public String getContact();
    public String getDOB();
    public String getAddress();

    // Setters
    public void setFirstName(String firstName);
    public void setLastName(String lastName);
    public void setContact(String contact);
    public void setDOB(String dob);
    public void setAddress(String address);

    // Custom Functions
    public boolean checkDOB(int threshold);

    public boolean checkContact(String contact);
}
