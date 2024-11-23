package instructor;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// Plugins
import com.toedter.calendar.JDateChooser;

// Importing Custom Classes
import connections.Conn;
import connections.DeptToCourseMapping;
import oop.Instructor;

public class AddInstructor extends JFrame implements ActionListener{
    JLabel lblHeading, lblDepartment, lblCourse,
            lblFirstName, lblLastName, lblContact,
            lblDOB, lblAddressStreet, lblAddressBuilding, lblAddressPostCode, lblAddressCity, lblBuilding, lblRoomNumber;
    TextField tfFirstName, tfLastName, tfContact,
            tfAddressBuilding, tfAddressStreet, tfAddressPostCode, tfAddressCity, tfBuilding, tfRoomNumber;
    JDateChooser dateChooserDOB;
    JComboBox cbDepartment;
    JComboBox<String> cbCourse;
    JButton btnSubmit, btnCancel;

    DeptToCourseMapping dcm;

    public AddInstructor(){

        /* 1. Form Heading */

        // 1. Heading
        lblHeading = new JLabel("Form: Add a New Instructor");
        lblHeading.setBounds(270, 30, 500, 50);
        lblHeading.setFont(new Font("serif",Font.BOLD,30));
        this.add(lblHeading);

        /* 2. Personal Details */

        JLabel lblPersonalDetails = new JLabel("Personal Details ");
        lblPersonalDetails.setBounds(350, 100, 250, 30);
        lblPersonalDetails.setFont(new Font("serif", Font.ITALIC, 20));
        this.add(lblPersonalDetails);

        // 2.1 First Name
        lblFirstName = new JLabel("First Name: ");
        lblFirstName.setBounds(50, 150, 150, 30);
        lblFirstName.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblFirstName);

        tfFirstName = new TextField();
        tfFirstName.setBounds(200, 150, 150, 30);
        this.add(tfFirstName);

        // 2.2 Last Name
        lblLastName = new JLabel("Last Name: ");
        lblLastName.setBounds(400, 150, 150, 30);
        lblLastName.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblLastName);

        tfLastName = new TextField();
        tfLastName.setBounds(600, 150, 150, 30);
        this.add(tfLastName);

        // 2.3 Contact
        lblContact = new JLabel("Contact: ");
        lblContact.setBounds(50, 200, 150, 30);
        lblContact.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblContact);

        tfContact = new TextField();
        tfContact.setBounds(200, 200, 150, 30);
        this.add(tfContact);

        // 2.4 Date of Birth
        lblDOB = new JLabel("Date of Birth");
        lblDOB.setBounds(400, 200, 150, 30);
        lblDOB.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblDOB);

        dateChooserDOB = new JDateChooser();
        dateChooserDOB.setBounds(600, 200, 150, 30);
        this.add(dateChooserDOB);

        // 2.5 Address

        // // 2.5.1 Street
        lblAddressStreet = new JLabel("Street: ");
        lblAddressStreet.setBounds(50, 250, 150, 30);
        lblAddressStreet.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblAddressStreet);

        tfAddressStreet = new TextField();
        tfAddressStreet.setBounds(200, 250, 150, 30);
        this.add(tfAddressStreet);

        // // 2.5.2 Building Number
        lblAddressBuilding = new JLabel("Building: ");
        lblAddressBuilding.setBounds(400, 250, 150, 30);
        lblAddressBuilding.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblAddressBuilding);

        tfAddressBuilding = new TextField();
        tfAddressBuilding.setBounds(600, 250, 150, 30);
        this.add(tfAddressBuilding);

        // // 2.5.3 Post Code
        lblAddressPostCode = new JLabel("Post Code: ");
        lblAddressPostCode.setBounds(50, 300, 150, 30);
        lblAddressPostCode.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblAddressPostCode);

        tfAddressPostCode = new TextField();
        tfAddressPostCode.setBounds(200, 300, 150, 30);
        this.add(tfAddressPostCode);

        // // 2.5.4 City
        lblAddressCity = new JLabel("City: ");
        lblAddressCity.setBounds(400, 300, 150, 30);
        lblAddressCity.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblAddressCity);

        tfAddressCity = new TextField();
        tfAddressCity.setBounds(600, 300, 150, 30);
        this.add(tfAddressCity);

        /* 3. Hochschule Details */

        JLabel lblHFWDetails = new JLabel("Hochschule Details");
        lblHFWDetails.setBounds(350, 350, 250, 30);
        lblHFWDetails.setFont(new Font("serif", Font.ITALIC, 20));
        this.add(lblHFWDetails);

        // 3.1 Hochschule Cabin
        lblBuilding = new JLabel("HFW Building: ");
        lblBuilding.setBounds(50, 400, 150, 30);
        lblBuilding.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblBuilding);

        tfBuilding = new TextField();
        tfBuilding.setBounds(200, 400, 150, 30);
        this.add(tfBuilding);

        // 3.2 Room Number
        lblRoomNumber = new JLabel("Room: #");
        lblRoomNumber.setBounds(400, 400, 150, 30);
        lblRoomNumber.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblRoomNumber);

        tfRoomNumber = new TextField();
        tfRoomNumber.setBounds(600, 400, 150, 30);
        this.add(tfRoomNumber);

        // 3.3 Department
        lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(50,450,150,30);
        lblDepartment.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblDepartment);

        dcm = new DeptToCourseMapping(); // Dynamic Department to Course Mapping
        String[] dept = dcm.getDeptList();
        cbDepartment = new JComboBox(dept);
        cbDepartment.setBounds(200,450,150,30);
        cbDepartment.setBackground(Color.WHITE);
        this.add(cbDepartment);

        // 3.3 Course
        lblCourse = new JLabel("Course: ");
        lblCourse.setBounds(400,450,150,30);
        lblCourse.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblCourse);

        cbCourse = new JComboBox<>();
        cbCourse.setBounds(500,450,250,30);
        cbCourse.setBackground(Color.WHITE);
        this.add(cbCourse);

        // Dynamic Mapping of Departments to Courses
        HashMap<String, List<String>> deptToCourse = dcm.getDeptToCourseMapping();

        cbDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deptStr = (String) cbDepartment.getSelectedItem();
                cbCourse.removeAllItems();
                for (String course : deptToCourse.get(deptStr)) {
                    cbCourse.addItem(course);
                }
            }
        });

        cbDepartment.setSelectedIndex(0); // Default Selection to 1st Option

        /* 4. Buttons */

        // 4.1 Submit
        btnSubmit = new JButton("SUBMIT");
        btnSubmit.setBounds(250,550,120,30);
        btnSubmit.setBackground(Color.BLACK);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(this);
        this.add(btnSubmit);

        // 4.2 Cancel
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(450,550,120,30);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        this.add(btnCancel);

        /* 5. JFrame Configurations */
        // this.getContentPane().setBackground(new Color(166,164,252));
        this.setSize(900, 700);
        this.setLocation(350, 50);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == btnSubmit){
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String contact = tfContact.getText();
            String dob = ((JTextField) dateChooserDOB.getDateEditor().getUiComponent()).getText();

            // Address
            String addressStreet = tfAddressStreet.getText();
            String addressBuilding = tfAddressBuilding.getText();
            String addressPostCode = tfAddressPostCode.getText();
            String addressCity = tfAddressCity.getText();
            String address = addressBuilding + ", " + addressStreet + ", " + addressPostCode + ", " + addressCity;

            String building = tfBuilding.getText();
            String roomNumber = tfRoomNumber.getText();
            String hftCabin = building + "/" + roomNumber;

            String dept = (String) cbDepartment.getSelectedItem();
            String course = (String) cbCourse.getSelectedItem();

            // 2. Create connection from the instructor template

            Instructor newInstructor = new Instructor(firstName, lastName, contact, dob, address, hftCabin, course, dept);
            Conn c = new Conn();

            // 3. Transactions - COMMIT and ROLLBACK

            try{
                c.conn.setAutoCommit(false);

                // Check if the new Instructor is more than 25 years old
                int result = newInstructor.insertIntoDB(c, 25);
                if(result==1) {
                    // COMMIT
                    c.conn.commit();

                    String query = """
                            update COURSE
                            set instructor_id=
                            (
                                select emp_id from INSTRUCTOR where first_name='"""
                            + firstName +
                            """
                                    ' and last_name='"""
                            + lastName + "')" + "where upper(course_name) = upper('" + course + "');";

                    c.stmt.executeUpdate(query);
                    c.conn.commit();

                    JOptionPane.showMessageDialog(null, "New Instructor Successfully added!");
                    this.setVisible(false);
                }
                else if (result == 0) {
                    JOptionPane.showMessageDialog(null, "ERROR: All values must be entered!");
                }
                else{
                    // ROLLBACK
                    // Instructor is less than or equal to 25 years and is NOT eligible
                    c.conn.rollback();
                    JOptionPane.showMessageDialog(null, "ERROR: Age>=25 and/or Contact must follow german phone standards!");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            // Cancel Button
            // Do nothing
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddInstructor();
    }
}
