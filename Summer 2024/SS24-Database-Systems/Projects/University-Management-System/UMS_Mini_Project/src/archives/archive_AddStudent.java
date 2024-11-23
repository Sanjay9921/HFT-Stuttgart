package archives;

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
import java.sql.*;
import java.time.LocalDate;

// Plugins
import com.toedter.calendar.JDateChooser;

// Importing Custom Classes
import connections.Conn;

public class archive_AddStudent extends JFrame implements ActionListener{
    JLabel lblHeading, lblDepartment, lblCourse,
            lblFirstName, lblLastName, lblContact,
            lblDOB, lblAddress, lblXII, lblInternational;
    TextField tfFirstName, tfLastName, tfContact,
            tfAddress, tfXII;
    JDateChooser dateChooserDOB;
    JComboBox cbDepartment, cbInternational;
    JComboBox<String> cbCourse;
    JButton btnSubmit, btnCancel;

    public archive_AddStudent(){

        /* 1. Form Heading */

        // 1. Heading
        lblHeading = new JLabel("Form: Add a New Student");
        lblHeading.setBounds(270, 30, 500, 50);
        lblHeading.setFont(new Font("serif",Font.BOLD,30));
        this.add(lblHeading);

        /* 2. Form Details */

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
        lblAddress = new JLabel("Home Address: ");
        lblAddress.setBounds(50, 250, 150, 30);
        lblAddress.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblAddress);

        tfAddress = new TextField();
        tfAddress.setBounds(200, 250, 550, 30);
        this.add(tfAddress);

        // 2.6 Grade XII Marks
        lblXII = new JLabel("Grade XII: ");
        lblXII.setBounds(50, 300, 150, 30);
        lblXII.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblXII);

        tfXII = new TextField();
        tfXII.setBounds(200, 300, 150, 30);
        this.add(tfXII);

        // 2.7 International Student Status
        lblInternational = new JLabel("International ?: ");
        lblInternational.setBounds(400, 300, 150, 30);
        lblInternational.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblInternational);

        String[] internationalStatus = {"Yes", "No"};
        cbInternational = new JComboBox(internationalStatus);
        cbInternational.setBounds(600, 300, 150, 30);
        cbInternational.setBackground(Color.WHITE);
        this.add(cbInternational);

        // 2.8 Department
        lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(50,400,150,30);
        lblDepartment.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblDepartment);

        String[] dept = {"Computer Science", "Mathematics","Business","Building Physics"};
        cbDepartment = new JComboBox(dept);
        cbDepartment.setBounds(200,400,150,30);
        cbDepartment.setBackground(Color.WHITE);
        this.add(cbDepartment);

        // 2.9 Course
        lblCourse = new JLabel("Course: ");
        lblCourse.setBounds(400,400,150,30);
        lblCourse.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblCourse);

        cbCourse = new JComboBox<>();
        cbCourse.setBounds(500,400,250,30);
        cbCourse.setBackground(Color.WHITE);
        this.add(cbCourse);

        // // Map courses with relevant departments
        HashMap<String, String[]> deptToCourse = new HashMap<>();
        deptToCourse.put("Computer Science", new String[]{
                "Bachelor of Computer Science",
                "Bachelor of Augmented Reality",
                "Bachelor's degree in digitalization and information management",
                "Bachelor Surveying and Geoinformatics",
                "Master Photogrammetry and Geoinformatics",
                "Master Software Technology",
                "Master Digital Processes and Technologies"
        });
        deptToCourse.put("Mathematics", new String[]{
                "Bachelor Applied Mathematics and AI",
                "Master Mathematics"
        });
        deptToCourse.put("Business", new String[]{
                "Bachelor of Business Administration",
                "Business Administration International Business course",
                "Bachelor of Infrastructure Management",
                "Bachelor of Business Informatics ",
                "Bachelor of Business Psychology",
                "Master General Management",
                "Master Environmentally Oriented Logistics",
                "Master's degree in business psychology"
        });
        deptToCourse.put("Building Physics", new String[]{
                "Bachelor of Building Physics",
                "Bachelor of Climate Engineering",
                "Master Building Physics",
                "Master in Sustainable Energy Competence"
        });
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

        /* 3. Buttons */

        // 3.1 Submit
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(250,550,120,30);
        btnSubmit.setBackground(Color.BLACK);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(this);
        this.add(btnSubmit);

        // 3.2 Cancel
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(450,550,120,30);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        this.add(btnCancel);

        /* 4. JFrame Configurations */
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
            String address = tfAddress.getText();
            String gradeXII = tfXII.getText();
            String international = (String) cbInternational.getSelectedItem();

            // Matriculation Number
            Random rnd = new Random();
            int n = 100000 + rnd.nextInt(900000);
            String matID = String.valueOf(n);

            String dept = (String) cbDepartment.getSelectedItem();
            String course = (String) cbCourse.getSelectedItem();

            try{
                String query = """
                        INSERT INTO STUDENT (FIRST_NAME, LAST_NAME, CONTACT, DOB, HOME_ADDRESS, GRADE_XII, INTERNATIONAL_STUDENT, IMMA_ID) VALUES ('"""
                        + firstName + "','"
                        + lastName + "','"
                        + contact + "','"
                        + dob + "','"
                        + address + "','"
                        + gradeXII + "','"
                        + international + "','"
                        + matID + "')";

                Conn c = new Conn();
                c.stmt.executeUpdate(query);

                String deptID="", courseID="";

                // Get Course ID
                query = "select dept_id, course_id from COURSE where upper(course_name) = upper('" + course + "');";
                ResultSet rs = c.stmt.executeQuery(query);
                while(rs.next()){
                    deptID = rs.getString("dept_id");
                    courseID = rs.getString("course_id");
                }

                System.out.println(deptID + " " + courseID);

                // Todays date
                String today = String.valueOf(LocalDate.now());

                query = "INSERT INTO ENROLLMENT (IMMA_ID, DEPT_ID, COURSE_ID, IMMA_DATE, IMMA_STATUS) VALUES ('"
                        + matID + "','"
                        + deptID + "','"
                        + courseID + "','"
                        + today + "','ADMITTED');";
                c.stmt.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "New Student Successfully added!");
                this.setVisible(true);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new archive_AddStudent();
    }
}
