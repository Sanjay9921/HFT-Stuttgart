package student;

// Only illustrates the structure of Fees
// Includes the necessary details for the students

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.awt.*;
import javax.swing.*;

public class FeeStructure extends JFrame{
    JLabel lblMainHeading, lblHeading,
            lblTuitionFees, lblTuitionFees2,
            lblSemesterFees, lblSemesterFees2,
            lblDTicket, lblDTicket2, lblVariable, lblVariable2;

    public FeeStructure(){
        /* 1. Form Heading */

        // 1.1 Heading
        lblMainHeading = new JLabel("Fee Structure for Students");
        lblMainHeading.setBounds(230, 10, 500, 50);
        lblMainHeading.setFont(new Font("serif",Font.BOLD,30));
        this.add(lblMainHeading);

        /* 2. Form Details */

        // 2.1 Tuition Fees
        lblTuitionFees = new JLabel("Tuition Fees: ");
        lblTuitionFees.setBounds(50, 100, 150, 30);
        lblTuitionFees.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblTuitionFees);

        lblTuitionFees2 = new JLabel("Only for International Students - €1500,00 / Semester");
        lblTuitionFees2.setBounds(250, 100, 500, 30);
        lblTuitionFees2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblTuitionFees2);

        // 2.2 Semester Fees
        lblSemesterFees = new JLabel("Semester Fees: ");
        lblSemesterFees.setBounds(50, 150, 150, 30);
        lblSemesterFees.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblSemesterFees);

        lblSemesterFees2 = new JLabel("For All - € 200,00 / Semester");
        lblSemesterFees2.setBounds(250, 150, 500, 30);
        lblSemesterFees2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblSemesterFees2);

        // 2.3 D-Ticket (Optional)
        lblDTicket = new JLabel("D-Ticket: ");
        lblDTicket.setBounds(50, 200, 150, 30);
        lblDTicket.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblDTicket);

        lblDTicket2 = new JLabel("Optional - € 180,00 / Semester");
        lblDTicket2.setBounds(250, 200, 500, 30);
        lblDTicket2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblDTicket2);

        // 2.4 Variable Fees
        lblVariable = new JLabel("Variables: ");
        lblVariable.setBounds(50, 250, 150, 30);
        lblVariable.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblVariable);

        lblVariable2 = new JLabel("Includes any variable fees / fines");
        lblVariable2.setBounds(250, 250, 500, 30);
        lblVariable2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblVariable2);

        /* 3. JFrame Configurations */
        this.setSize(900, 700);
        this.setLocation(350, 50);
        this.setLayout(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new FeeStructure();
    }
}
