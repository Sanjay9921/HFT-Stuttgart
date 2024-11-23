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

// Importing Custom Classes
import grades.*;
import instructor.*;
import student.*;

public class StudentMain extends JFrame implements ActionListener {
    ImageIcon icon1, iconBG;
    Image imgBG;
    JLabel lblBG;

    JMenuBar menuBar;
    JMenu menuNew, menuView, menuUpdate, menuRemove, menuExam, menuFees, menuUtility, menuReset, menuExit;
    JMenuItem newInstructorInfo, newStudentInfo, viewInstructorInfo, viewStudentInfo,
            updateInstructorInfo, updateStudentInfo, removeInstructorInfo, removeStudentInfo,
            examResults, examEnterMarks, feesStructure, feesForm, utilityCalculator, utilityNotepad,
            exitMenuItem;

    String stuID = "";

    public StudentMain(String stuID){

        this.stuID = stuID;

        /* 1. Images */

        // 1.1 Desktop Background
        icon1 = new ImageIcon(ClassLoader.getSystemResource("images/desktop_bg.jpeg"));
        imgBG = icon1.getImage().getScaledInstance(1540,750, Image.SCALE_DEFAULT);
        iconBG = new ImageIcon(imgBG);
        lblBG = new JLabel(iconBG);
        this.add(lblBG);

        /* 2. Menu Bar */
        menuBar = new JMenuBar();

        // 2.1 View Existing
        menuView = new JMenu("View");
        menuView .setForeground(Color.BLACK);
        menuBar.add(menuView );

        // // 2.1.2 View Existing Student Information
        viewStudentInfo = new JMenuItem("Student Details");
        viewStudentInfo.setBackground(Color.WHITE);
        viewStudentInfo.addActionListener(this);
        menuView .add(viewStudentInfo);

        // 2.2 Examinations
        menuExam = new JMenu("Examinations");
        menuExam.setForeground(Color.BLACK);
        menuBar.add(menuExam);

        // // 2.2.1 View Results
        examResults = new JMenuItem("View Results");
        examResults.setBackground(Color.WHITE);
        examResults.addActionListener(this);
        menuExam.add(examResults);

        // 2.3 Fees
        menuFees = new JMenu("Fees");
        menuFees.setForeground(Color.BLACK);
        menuBar.add(menuFees);

        // // 2.3.1 Fee Structure
        feesStructure = new JMenuItem("General Fee Structure");
        feesStructure.setBackground(Color.WHITE);
        feesStructure.addActionListener(this);
        menuFees.add(feesStructure);

        // 2.4 Utilities
        menuUtility = new JMenu("Utility");
        menuUtility.setForeground(Color.BLACK);
        menuBar.add(menuUtility);

        // // 2.4.1 Calculator
        utilityCalculator = new JMenuItem("Standard Calculator");
        utilityCalculator.setBackground(Color.WHITE);
        utilityCalculator.addActionListener(this);
        menuUtility.add(utilityCalculator);

        // // 2.4.2 Notepad
        utilityNotepad= new JMenuItem("Standard Notepad");
        utilityNotepad.setBackground(Color.WHITE);
        utilityNotepad.addActionListener(this);
        menuUtility.add(utilityNotepad);

        // 2.5 Exit
        menuExit = new JMenu("Exit");
        menuExit.setForeground(Color.BLACK);
        menuBar.add(menuExit);

        // // 2.5.1 Logout
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
            case "Student Details":
                new ViewSingleStudent(stuID);
                break;
            case "View Results":
                new ViewSingleGrades(stuID);
                break;
            case "General Fee Structure":
                new FeeStructure();
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
        // new StudentMain();
    }
}
