package grades;

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
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Random;

// Plugins
import com.toedter.calendar.JDateChooser;
import connections.Conn;

// Importing Custom Classes

public class InsertGrades extends JFrame implements ActionListener{
    Choice studIDChoice;
    JComboBox cbSemester;
    JLabel lblMainHeading, lblHeading, lblSemester,
            lblSubject, lblGrades, lblCourse;
    String courseID;
    JTextField tfSubject1, tfSubject2, tfSubject3, tfSubject4, tfSubject5, tfElective1,
    tfGrade1, tfGrade2, tfGrade3, tfGrade4, tfGrade5, tfGrade6;
    JButton btnSubmit, btnCancel;

    public InsertGrades(){
        /* 1. Form Heading */

        // 1.1 Heading
        lblMainHeading = new JLabel("Form: Insert Grades for a Student");
        lblMainHeading.setBounds(230, 10, 500, 50);
        lblMainHeading.setFont(new Font("serif",Font.BOLD,30));
        this.add(lblMainHeading);

        // 1.2 Choice
        lblHeading = new JLabel("Choose a Matriculation ID: ");
        lblHeading.setBounds(50,60,200,20);
        this.add(lblHeading);

        studIDChoice = new Choice();
        studIDChoice.setBounds(270,60,150,20);
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

        // Course Label
        lblCourse = new JLabel();
        lblCourse.setBounds(20, 100, 150, 20);
        this.add(lblCourse);

        // Get the course id
        studIDChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    String choiceItem = studIDChoice.getSelectedItem();
                    String query = """
                        SELECT DISTINCT
                        C.COURSE_ID, C.COURSE_NAME
                        FROM
                        STUDENT S
                        LEFT JOIN ENROLLMENT E
                        ON S.IMMA_ID = E.IMMA_ID
                        LEFT JOIN COURSE C
                        ON E.COURSE_ID = C.COURSE_ID
                        WHERE S.IMMA_ID =
                        """ + choiceItem + ";";

                    ResultSet resultSet = c.stmt.executeQuery(query);

                    while (resultSet.next()) {
                        lblCourse.setText(resultSet.getString("course_name"));
                        courseID = resultSet.getString("course_id");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }}
        });

        /* 2. Form Details */

        // 2.1 Select Semester
        lblSemester = new JLabel("Select Semester: ");
        lblSemester.setBounds(50,110,150,20);
        this.add(lblSemester);

        String[] semester = {"1","2","3","4","5","6","7","8"};
        cbSemester = new JComboBox(semester);
        cbSemester.setBounds(270,110,150,20);
        cbSemester.setBackground(Color.WHITE);
        this.add(cbSemester);

        // 2.2 Headings for Subjects and Marks
        lblSubject = new JLabel("Subject: ");
        lblSubject.setBounds(100,150,200,40);
        lblSubject.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblSubject);

        lblGrades = new JLabel("Grades: ");
        lblGrades.setBounds(400,150,200,40);
        lblGrades.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblGrades);

        // 2.3 Subjects
        tfSubject1 = new JTextField();
        tfSubject1.setBounds(100,200,200,20);
        this.add(tfSubject1);

        tfSubject2 = new JTextField();
        tfSubject2.setBounds(100,230,200,20);
        this.add(tfSubject2);

        tfSubject3 = new JTextField();
        tfSubject3.setBounds(100,260,200,20);
        this.add(tfSubject3);

        tfSubject4 = new JTextField();
        tfSubject4.setBounds(100,290,200,20);
        this.add(tfSubject4);

        tfSubject5 = new JTextField();
        tfSubject5.setBounds(100,320,200,20);
        this.add(tfSubject5);

        tfElective1 = new JTextField();
        tfElective1.setBounds(100,350,200,20);
        this.add(tfElective1);

        // 2.4 Grades
        tfGrade1 = new JTextField();
        tfGrade1.setBounds(350,200,200,20);
        this.add(tfGrade1);

        tfGrade2 = new JTextField();
        tfGrade2.setBounds(350,230,200,20);
        this.add(tfGrade2);

        tfGrade3= new JTextField();
        tfGrade3.setBounds(350,260,200,20);
        this.add(tfGrade3);

        tfGrade4 = new JTextField();
        tfGrade4.setBounds(350,290,200,20);
        this.add(tfGrade4);

        tfGrade5 = new JTextField();
        tfGrade5.setBounds(350,320,200,20);
        this.add(tfGrade5);

        tfGrade6 = new JTextField();
        tfGrade6.setBounds(350,350,200,20);
        this.add(tfGrade6);

        /* 3. Buttons */

        // 3.1 Submit
        // 3.1 Insert
        btnSubmit = new JButton("INSERT");
        btnSubmit.setBounds(150,400,120,30);
        btnSubmit.setBackground(Color.BLACK);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.addActionListener(this);
        this.add(btnSubmit);

        // 3.2 Cancel
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(370,400,120,30);
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
        if(ae.getSource() == btnSubmit){
            String choiceItem = studIDChoice.getSelectedItem();
            if(!choiceItem.equals("Select...")){

                Conn c = new Conn();

                try {

                    c.conn.setAutoCommit(false);

                    // Check if there are no duplicates
                    String query = """
                            select count(*) student_id from SUBJECT
                            where student_id = '"""
                            + choiceItem + "' and semester = '" + cbSemester.getSelectedItem() + "';";

                    ResultSet rs = c.stmt.executeQuery(query);
                    String rowCount="";

                    while(rs.next()){
                        rowCount = rs.getString("student_id");
                    }

                    if(rowCount.equals("0")){
                        query = "INSERT INTO SUBJECT VALUES('" + choiceItem + "', '" + courseID + "', '" + cbSemester.getSelectedItem() + "', '" +
                                tfSubject1.getText() + "', '" + tfSubject2.getText() + "', '" + tfSubject3.getText() + "', '" + tfSubject4.getText() + "', '" + tfSubject5.getText() + "', '" + tfElective1.getText() + "');";

                        c.stmt.executeUpdate(query);
                        c.conn.commit();

                        query = "INSERT INTO GRADES VALUES('" + choiceItem + "', '" + courseID + "', '" + cbSemester.getSelectedItem() + "', '" +
                                tfGrade1.getText() + "', '" + tfGrade2.getText() + "', '" + tfGrade3.getText() + "', '" + tfGrade4.getText() + "', '" + tfGrade5.getText() + "', '" + tfGrade6.getText() + "');";
                        c.stmt.executeUpdate(query);
                        c.conn.commit();

                        JOptionPane.showMessageDialog(null, "Grades inserted successfully!");
                        setVisible(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Value exists for the selected student and semester! Please try again!");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Select a student matriculation number to insert!");
            }
        }
        else{
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new InsertGrades();
    }
}
