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

// Importing Custom Classes
import grades.*;
import instructor.*;
import student.*;

public class InstructorMain extends JFrame implements ActionListener {
    ImageIcon icon1, iconBG;
    Image imgBG;
    JLabel lblBG;

    JMenuBar menuBar;
    JMenu menuNew, menuView, menuUpdate, menuRemove, menuExam, menuFees, menuUtility, menuReset, menuExit;
    JMenuItem newInstructorInfo, newStudentInfo, viewInstructorInfo, viewStudentInfo,
            updateInstructorInfo, updateStudentInfo, removeInstructorInfo, removeStudentInfo,
            examResults, examEnterMarks, feesStructure, feesForm, utilityCalculator, utilityNotepad,
            exitMenuItem;

    String empID = "";

    public InstructorMain(String empID){
        this.empID = empID;

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

        // // 2.1.1 View Existing Instructor Information
        viewInstructorInfo = new JMenuItem("Instructor Details");
        viewInstructorInfo.setBackground(Color.WHITE);
        viewInstructorInfo.addActionListener(this);
        menuView .add(viewInstructorInfo);

        // 2.2 Examinations
        menuExam = new JMenu("Examinations");
        menuExam.setForeground(Color.BLACK);
        menuBar.add(menuExam);

        // // 2.2.1 View Results
        examResults = new JMenuItem("View Results");
        examResults.setBackground(Color.WHITE);
        examResults.addActionListener(this);
        menuExam.add(examResults);

        // // 2.2.2 Add Marks
        examEnterMarks = new JMenuItem("Enter Grades");
        examEnterMarks.setBackground(Color.WHITE);
        examEnterMarks.addActionListener(this);
        menuExam.add(examEnterMarks);

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
            case "Instructor Details":
                new ViewSingleInstructor(empID);
                break;
            case "View Results":
                new ViewGrades();
                break;
            case "Enter Grades":
                new InsertGrades();
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
        // new InstructorMain();
    }
}
