package student;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;
import java.sql.*;
import java.time.LocalDate;

// Plugins
import net.proteanit.sql.DbUtils;

// Importing Custom Classes
import connections.Conn;
import oop.Student;

public class UpdateStudent extends JFrame implements ActionListener{
    Choice studIDChoice;
    JLabel lblMainHeading, lblHeading,
            lblDepartment, lblCourse, lblDepartment2, lblCourse2,
            lblFirstName, lblLastName, lblFirstName2, lblLastName2,
            lblInternational, lblInternational2,
            lblEmail, lblEmail2, lblIMMAStatus, lblIMMAStatus2, lblFeeStatus, lblFeeStatus2,
            lblDOB, lblDOB2, lblContact, lblAddress;
    TextField tfContact, tfAddress;
    JButton btnUpdate, btnRemove, btnCancel;

    public UpdateStudent(){

        /* 1. Form Heading */

        // 1.1 Heading
        lblMainHeading = new JLabel("Form: Update an existing Student Record");
        lblMainHeading.setBounds(100, 10, 700, 50);
        lblMainHeading.setFont(new Font("serif",Font.BOLD,30));
        this.add(lblMainHeading);

        // 1.2 Choice
        lblHeading = new JLabel("Choose a Matriculation ID: ");
        lblHeading.setBounds(50,100,170,20);
        this.add(lblHeading);

        studIDChoice = new Choice();
        studIDChoice.setBounds(220,100,150,20);
        this.add(studIDChoice);
        studIDChoice.add("Select...");

        // 1.3 Connection to get a list of choice of emp ids
        try{
            Conn c = new Conn();
            String query = "select distinct imma_id from STUDENT;";
            ResultSet rs = c.stmt.executeQuery(query);
            while(rs.next()){
                studIDChoice.add(rs.getString("imma_id"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        /* 2. Form Details */

        // 2.1 First Name
        lblFirstName = new JLabel("First Name: ");
        lblFirstName.setBounds(50, 150, 150, 30);
        lblFirstName.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblFirstName);

        lblFirstName2 = new JLabel();
        lblFirstName2.setBounds(200, 150, 150, 30);
        lblFirstName2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblFirstName2);

        // 2.2 Last Name
        lblLastName = new JLabel("Last Name: ");
        lblLastName.setBounds(400, 150, 150, 30);
        lblLastName.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblLastName);

        lblLastName2 = new JLabel();
        lblLastName2.setBounds(600, 150, 150, 30);
        lblLastName2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblLastName2);

        // 2.3 Email
        lblEmail = new JLabel("Email: ");
        lblEmail.setBounds(50, 200, 150, 30);
        lblEmail.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblEmail);

        lblEmail2 = new JLabel();
        lblEmail2.setBounds(200, 200, 500, 30);
        lblEmail2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblEmail2);

        // 2.4 Matriculation Status
        lblIMMAStatus = new JLabel("IMMA Status: ");
        lblIMMAStatus.setBounds(50, 250, 150, 30);
        lblIMMAStatus.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblIMMAStatus);

        lblIMMAStatus2 = new JLabel();
        lblIMMAStatus2.setBounds(200, 250, 150, 30);
        lblIMMAStatus2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblIMMAStatus2);

        // 2.5 Fee Status
        lblFeeStatus = new JLabel("Fees Status: ");
        lblFeeStatus.setBounds(400, 250, 150, 30);
        lblFeeStatus.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblFeeStatus);

        lblFeeStatus2 = new JLabel();
        lblFeeStatus2.setBounds(600, 250, 150, 30);
        lblFeeStatus2.setFont(new Font("serif", Font.BOLD, 15));
        this.add(lblFeeStatus2);

        // 2.6 Contact
        lblContact = new JLabel("Contact: ");
        lblContact.setBounds(50, 300, 150, 30);
        lblContact.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblContact);

        tfContact = new TextField();
        tfContact.setBounds(200, 300, 150, 30);
        this.add(tfContact);

        // 2.7 Date of Birth
        lblDOB = new JLabel("Date of Birth");
        lblDOB.setBounds(400, 300, 150, 30);
        lblDOB.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblDOB);

        lblDOB2 = new JLabel();
        lblDOB2.setBounds(600, 300, 150, 30);
        lblDOB2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblDOB2);

        // 2.8 International Student Status
        lblInternational = new JLabel("International ?: ");
        lblInternational.setBounds(400, 100, 150, 30);
        lblInternational.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblInternational);

        lblInternational2 = new JLabel();
        lblInternational2.setBounds(600, 100, 150, 30);
        lblInternational2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblInternational2);

        // 2.5 Address
        lblAddress = new JLabel("Home Address: ");
        lblAddress.setBounds(50, 350, 150, 30);
        lblAddress.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblAddress);

        tfAddress = new TextField();
        tfAddress.setBounds(200, 350, 550, 30);
        this.add(tfAddress);

        // 2.8 Department
        lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(50,400,200,30);
        lblDepartment.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblDepartment);

        lblDepartment2 = new JLabel();
        lblDepartment2.setBounds(200,400,200,30);
        lblDepartment2.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblDepartment2);

        // 2.9 Course
        lblCourse = new JLabel("Course: ");
        lblCourse.setBounds(400,400,150,30);
        lblCourse.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblCourse);

        lblCourse2 = new JLabel();
        lblCourse2.setBounds(500,400,250,30);
        lblCourse2.setFont(new Font("serif",Font.BOLD,20));
        this.add(lblCourse2);

        studIDChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
            try {
                Conn c = new Conn();
                String choiceItem = studIDChoice.getSelectedItem();
                String query = """
                        SELECT DISTINCT
                        S.IMMA_ID, S.FIRST_NAME, S.LAST_NAME, S.CONTACT, S.EMAIL, S.DOB, S.HOME_ADDRESS, S.INTERNATIONAL_STUDENT,
                        C.COURSE_NAME, D.DEPT_NAME,
                        E.IMMA_DATE, E.IMMA_STATUS, F.FEES_PAID
                        FROM
                        STUDENT S
                        LEFT JOIN ENROLLMENT E
                        ON S.IMMA_ID = E.IMMA_ID
                        LEFT JOIN FEES F
                        ON S.IMMA_ID = F.STUDENT_ID
                        LEFT JOIN COURSE C
                        ON E.COURSE_ID = C.COURSE_ID
                        LEFT JOIN DEPARTMENT D
                        ON C.DEPT_ID = D.DEPT_ID
                        WHERE S.IMMA_ID =
                        """ + choiceItem + ";";

                ResultSet resultSet = c.stmt.executeQuery(query);

                while (resultSet.next()) {
                    lblFirstName2.setText(resultSet.getString("first_name"));
                    lblLastName2.setText(resultSet.getString("last_name"));
                    lblEmail2.setText(resultSet.getString("email"));
                    lblDOB2.setText(resultSet.getString("dob"));
                    lblInternational2.setText(resultSet.getString("international_student"));
                    lblCourse2.setText(resultSet.getString("course_name"));
                    lblDepartment2.setText(resultSet.getString("dept_name"));
                    lblIMMAStatus2.setText(resultSet.getString("imma_status"));
                    lblFeeStatus2.setText(resultSet.getString("fees_paid"));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }}
        });

        /* 3. Buttons */

        // 3.1 Update
        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(250,550,120,30);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.addActionListener(this);
        this.add(btnUpdate);

        // 3.2 Remove
        btnRemove = new JButton("DEREGISTER");
        btnRemove.setBounds(450,550,120,30);
        btnRemove.setBackground(Color.BLACK);
        btnRemove.setForeground(Color.WHITE);
        btnRemove.addActionListener(this);
        this.add(btnRemove);

        // 3.2 Cancel
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(650,550,120,30);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        this.add(btnCancel);

        /* 4. JFrame Configurations */
        this.setSize(900, 700);
        this.setLocation(350, 50);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == btnUpdate){
            String choiceItem = studIDChoice.getSelectedItem();
            if(!choiceItem.equals("Select...")){
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "WARNING: You are about to update " + choiceItem + "!","WARNING",dialogButton);

                Conn c = new Conn();

                try {
                    if(dialogResult == 0) {
                        // YES
                        String contact = tfContact.getText();
                        String address = tfAddress.getText();

                        c.conn.setAutoCommit(false);

                        Student existingStudent = new Student();

                        if(existingStudent.updateContactDB(c, choiceItem, contact, address)==1){
                            // COMMIT
                            c.conn.commit();

                            JOptionPane.showMessageDialog(null, "UPDATE: Student Record updated successfully!");
                            setVisible(false);
                        }
                        else{
                            // ROLLBACK
                            // Contact mistake
                            c.conn.rollback();
                            JOptionPane.showMessageDialog(null, "ERROR: Contact must follow german phone standards!");
                        };
                    }
                    else {
                        // NO ACTION ITEM
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Select a student matriculation number to update!");
            }
        }
        else if(ae.getSource() == btnRemove){
            String choiceItem = studIDChoice.getSelectedItem();
            if(!choiceItem.equals("Select...")){
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "WARNING: You are about to remove " + choiceItem + "!","WARNING",dialogButton);

                if(dialogResult == 0) {
                    // YES
                    Conn c = new Conn();

                    try {

                        c.conn.setAutoCommit(false);

                        String query = """
                            DELETE FROM STUDENT
                            WHERE IMMA_ID =
                            """
                                + choiceItem + ";";

                        c.stmt.executeUpdate(query);
                        c.conn.commit();

                        JOptionPane.showMessageDialog(null, "UPDATE: Student Record deleted successfully!");
                        setVisible(false);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    // NO ACTION ITEM
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "ERROR: Select a student matriculation number to delete!");
            }
        }
        else{
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateStudent();
    }
}

