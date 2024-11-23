package admin;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Importing Custom Classes
import grades.*;
import instructor.*;
import student.*;

public class AdminMain extends JFrame implements ActionListener {
    ImageIcon icon1, iconBG;
    Image imgBG;
    JLabel lblBG;

    JMenuBar menuBar;
    JMenu menuNew, menuView, menuUpdate, menuRemove, menuExam, menuFees, menuUtility, menuReset, menuExit;
    JMenuItem newInstructorInfo, newStudentInfo, viewInstructorInfo, viewStudentInfo,
            updateInstructorInfo, updateStudentInfo, removeInstructorInfo, removeStudentInfo,
            examResults, examEnterMarks, feesStructure, feesForm, utilityCalculator, utilityNotepad,
            exitMenuItem;

    public AdminMain(){
        /* 1. Images */

        // 1.1 Desktop Background
        icon1 = new ImageIcon(ClassLoader.getSystemResource("images/desktop_bg.jpeg"));
        imgBG = icon1.getImage().getScaledInstance(1540,750, Image.SCALE_DEFAULT);
        iconBG = new ImageIcon(imgBG);
        lblBG = new JLabel(iconBG);
        this.add(lblBG);

        /* 2. Menu Bar */

        menuBar = new JMenuBar();

        // 2.1 New
        menuNew = new JMenu("Add");
        menuNew.setForeground(Color.BLACK);
        menuBar.add(menuNew);

        // // 2.1.1 New Instructor Information
        newInstructorInfo = new JMenuItem("New Instructor");
        newInstructorInfo.setBackground(Color.WHITE);
        newInstructorInfo.addActionListener(this);
        menuNew.add(newInstructorInfo);

        // // 2.1.2 New Student Information
        newStudentInfo = new JMenuItem("New Student");
        newStudentInfo.setBackground(Color.WHITE);
        newStudentInfo.addActionListener(this);
        menuNew.add(newStudentInfo);

        // 2.2 View Existing
        menuView = new JMenu("View");
        menuView .setForeground(Color.BLACK);
        menuBar.add(menuView );

        // // 2.2.1 View Existing Instructor Information
        viewInstructorInfo = new JMenuItem("Instructor Details");
        viewInstructorInfo.setBackground(Color.WHITE);
        viewInstructorInfo.addActionListener(this);
        menuView .add(viewInstructorInfo);

        // // 2.2.2 View Existing Student Information
        viewStudentInfo = new JMenuItem("Student Details");
        viewStudentInfo.setBackground(Color.WHITE);
        viewStudentInfo.addActionListener(this);
        menuView .add(viewStudentInfo);

        // 2.3 Update
        menuUpdate = new JMenu("Update");
        menuUpdate.setForeground(Color.BLACK);
        menuBar.add(menuUpdate);

        // // 2.3.1 Update Instructor Information
        updateInstructorInfo = new JMenuItem("Update Instructor");
        updateInstructorInfo.setBackground(Color.WHITE);
        updateInstructorInfo.addActionListener(this);
        menuUpdate.add(updateInstructorInfo);

        // // 2.3.2 Update Student Information
        updateStudentInfo = new JMenuItem("Update Student");
        updateStudentInfo.setBackground(Color.WHITE);
        updateStudentInfo.addActionListener(this);
        menuUpdate.add(updateStudentInfo);

        // 2.4 Remove
        menuRemove= new JMenu("Remove");
        menuRemove.setForeground(Color.BLACK);
        menuBar.add(menuRemove);

        // // 2.4.1 Remove Instructor Information
        removeInstructorInfo = new JMenuItem("Remove Instructor");
        removeInstructorInfo.setBackground(Color.WHITE);
        removeInstructorInfo.addActionListener(this);
        menuRemove.add(removeInstructorInfo);

        // // 2.3.2 Deregister Student Information
        removeStudentInfo = new JMenuItem("Deregister Student");
        removeStudentInfo.setBackground(Color.WHITE);
        removeStudentInfo.addActionListener(this);
        menuRemove.add(removeStudentInfo);

        // 2.5 Examinations
        menuExam = new JMenu("Examinations");
        menuExam.setForeground(Color.BLACK);
        menuBar.add(menuExam);

        // // 2.5.1 View Results
        examResults = new JMenuItem("View Results");
        examResults.setBackground(Color.WHITE);
        examResults.addActionListener(this);
        menuExam.add(examResults);

        // // 2.5.2 Add Marks
        examEnterMarks = new JMenuItem("Enter Grades");
        examEnterMarks.setBackground(Color.WHITE);
        examEnterMarks.addActionListener(this);
        menuExam.add(examEnterMarks);

        // 2.6 Fees
        menuFees = new JMenu("Fees");
        menuFees.setForeground(Color.BLACK);
        menuBar.add(menuFees);

        // // 2.6.1 Fee Structure
        feesStructure = new JMenuItem("Fee Structure");
        feesStructure.setBackground(Color.WHITE);
        feesStructure.addActionListener(this);
        menuFees.add(feesStructure);

        // // 2.6.2 Student Fee Form
        feesForm = new JMenuItem("Student Form");
        feesForm.setBackground(Color.WHITE);
        feesForm.addActionListener(this);
        menuFees.add(feesForm);

        // 2.7 Utilities
        menuUtility = new JMenu("Utility");
        menuUtility.setForeground(Color.BLACK);
        menuBar.add(menuUtility);

        // // 2.7.1 Calculator
        utilityCalculator = new JMenuItem("Standard Calculator");
        utilityCalculator.setBackground(Color.WHITE);
        utilityCalculator.addActionListener(this);
        menuUtility.add(utilityCalculator);

        // // 2.7.2 Notepad
        utilityNotepad= new JMenuItem("Standard Notepad");
        utilityNotepad.setBackground(Color.WHITE);
        utilityNotepad.addActionListener(this);
        menuUtility.add(utilityNotepad);

        // 2.9 Exit
        menuExit = new JMenu("Exit");
        menuExit.setForeground(Color.BLACK);
        menuBar.add(menuExit);

        // // 2.9.1 Logout
        exitMenuItem = new JMenuItem("Logout");
        exitMenuItem.setBackground(Color.WHITE);
        exitMenuItem.addActionListener(this);
        menuExit.add(exitMenuItem);

        /* 3. JFrame Configurations */
        this.setJMenuBar(menuBar);
        this.setSize(1540, 850);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        String selectedMenuItem = ae.getActionCommand();
        switch(selectedMenuItem){
            case "New Instructor":
                new AddInstructor();
                break;
            case "New Student":
                new AddStudent();
                break;
            case "Instructor Details":
                new ViewInstructor();
                break;
            case "Student Details":
                new ViewStudent();
                break;
            case "Update Instructor", "Remove Instructor":
                new UpdateInstructor();
                break;
            case "Update Student", "Deregister Student":
                new UpdateStudent();
                break;
            case "View Results":
                new ViewGrades();
                break;
            case "Enter Grades":
                new InsertGrades();
                break;
            case "Fee Structure":
                new FeeStructure();
                break;
            case "Student Form":
                new FeesForm();
                break;
            case "Standard Calculator":
                try{
                    Runtime.getRuntime().exec("calc.exe");
                    break;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            case "Standard Notepad":
                try{
                    Runtime.getRuntime().exec("notepad.exe");
                    break;
               }
               catch(Exception e){
                    e.printStackTrace();
                }
            case "Logout":
                System.exit(15);
        }
    }

    public static void main(String[] args) {
        new AdminMain();
    }
}
