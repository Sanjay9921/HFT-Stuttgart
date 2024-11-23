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

public class FeesForm extends JFrame implements ActionListener{
    Choice studIDChoice;
    JLabel lblMainHeading, lblHeading,
            lblTuitionFees, lblTuitionFees2,
            lblSemesterFees, lblSemesterFees2,
            lblDTicket, lblVariable,
            lblFeesPaid, lblTotalFees;
    JTextField tfVariable;
    JComboBox cbDTicket;
    JButton btnPay, btnCancel, btnReset, btnCalculate;

    public FeesForm(){
        /* 1. Form Heading */

        // 1.1 Heading
        lblMainHeading = new JLabel("Form: Student Fees");
        lblMainHeading.setBounds(230, 10, 500, 50);
        lblMainHeading.setFont(new Font("serif",Font.BOLD,30));
        this.add(lblMainHeading);

        // 1.2 Choice
        lblHeading = new JLabel("Choose a Matriculation ID: ");
        lblHeading.setBounds(20,60,170,20);
        this.add(lblHeading);

        studIDChoice = new Choice();
        studIDChoice.setBounds(190,60,150,20);
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

        // 2.1 Fees Status and Total Fees
        lblFeesPaid = new JLabel();
        lblFeesPaid.setBounds(50, 100, 150, 30);
        lblFeesPaid.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblFeesPaid);

        lblTotalFees = new JLabel("NOT CALCULATED");
        lblTotalFees.setBounds(500, 100, 200, 30);
        lblTotalFees.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblTotalFees);


        // 2.2 Tuition Fees
        lblTuitionFees = new JLabel("Tuition Fees: ");
        lblTuitionFees.setBounds(50, 150, 150, 30);
        lblTuitionFees.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblTuitionFees);

        lblTuitionFees2 = new JLabel();
        lblTuitionFees2.setBounds(200, 150, 150, 30);
        lblTuitionFees2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblTuitionFees2);

        // 2.3 Semester Fees
        lblSemesterFees = new JLabel("Semester Fees: ");
        lblSemesterFees.setBounds(400, 150, 150, 30);
        lblSemesterFees.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblSemesterFees);

        lblSemesterFees2 = new JLabel();
        lblSemesterFees2.setBounds(600, 150, 150, 30);
        lblSemesterFees2.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblSemesterFees2);

        // 2.4 D-Ticket (Optional)
        lblDTicket = new JLabel("D-Ticket: ");
        lblDTicket.setBounds(50, 200, 150, 30);
        lblDTicket.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblDTicket);

        cbDTicket = new JComboBox(new String[]{"YES", "NO"});
        cbDTicket.setBounds(200, 200, 150, 30);
        cbDTicket.setFont(new Font("serif", Font.BOLD, 15));
        this.add(cbDTicket);

        // 2.5 Variable Fees
        lblVariable = new JLabel("Variables: ");
        lblVariable.setBounds(400, 200, 150, 30);
        lblVariable.setFont(new Font("serif", Font.BOLD, 20));
        this.add(lblVariable);

        tfVariable = new JTextField();
        tfVariable.setBounds(600, 200, 150, 30);
        tfVariable.setFont(new Font("serif", Font.BOLD, 20));
        this.add(tfVariable);

        studIDChoice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                try {
                    Conn c = new Conn();
                    String choiceItem = studIDChoice.getSelectedItem();
                    String query = """
                        SELECT *
                        FROM
                        FEES
                        WHERE STUDENT_ID =
                        """ + choiceItem + ";";

                    ResultSet resultSet = c.stmt.executeQuery(query);

                    while (resultSet.next()) {
                        lblFeesPaid.setText("Fees Paid?: " + resultSet.getString("fees_paid"));
                        lblTuitionFees2.setText(resultSet.getString("tuition_fees"));
                        lblSemesterFees2.setText(resultSet.getString("semester_fees"));
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }}
        });

        /* 3. Buttons */

        // 3.1 Calculate
        btnCalculate = new JButton("CALCULATE");
        btnCalculate.setBounds(100,250,120,30);
        btnCalculate.setBackground(Color.BLACK);
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.addActionListener(this);
        this.add(btnCalculate);

        // 3.2 Pay
        btnPay = new JButton("PAY");
        btnPay.setBounds(250,250,120,30);
        btnPay.setBackground(Color.BLACK);
        btnPay.setForeground(Color.WHITE);
        btnPay.addActionListener(this);
        this.add(btnPay);

        // 3.3 Cancel
        btnCancel = new JButton("CANCEL");
        btnCancel.setBounds(400,250,120,30);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        this.add(btnCancel);

        // 3.4 Reset
        btnReset = new JButton("RESET");
        btnReset.setBounds(550,250,120,30);
        btnReset.setBackground(Color.BLACK);
        btnReset.setForeground(Color.WHITE);
        btnReset.addActionListener(this);
        this.add(btnReset);

        /* 4. JFrame Configurations */
        this.setSize(900, 450);
        this.setLocation(350, 50);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(!studIDChoice.getSelectedItem().equals("Select...")){
            if(ae.getSource() == btnCalculate){
                double tuitionFees = Double.parseDouble(lblTuitionFees2.getText());
                double semesterFees = Double.parseDouble(lblSemesterFees2.getText());

                // D Ticket
                String dTicket = String.valueOf(cbDTicket.getSelectedItem());
                double d_ticket = (dTicket.equals("YES") ? 180 : 0);

                double variables = Double.parseDouble(
                        (tfVariable.getText().isEmpty() ? "0" : tfVariable.getText())
                );

                double totalFees = tuitionFees + semesterFees + d_ticket + variables;

                lblTotalFees.setText("Total: €" + String.valueOf(totalFees));
            }
            else if(ae.getSource() == btnPay){
                if(lblTotalFees.getText().equals("NOT CALCULATED")){
                    JOptionPane.showMessageDialog(null, "Calculate the amount first!");
                }
                else{
                    // D Ticket
                    String dTicket = String.valueOf(cbDTicket.getSelectedItem());
                    double d_ticket = (dTicket.equals("YES") ? 360 : 0);

                    double variables = Double.parseDouble(
                            (tfVariable.getText().isEmpty() ? "0" : tfVariable.getText())
                    );

                    Conn c = new Conn();
                    Student s = new Student();
                    String immaID = studIDChoice.getSelectedItem();
                    System.out.println(immaID);

                    if(s.updateFeesDB(c, immaID, String.valueOf(d_ticket), String.valueOf(variables))==1){
                        try {
                            c.conn.commit();

                            String query = "UPDATE ENROLLMENT SET IMMA_STATUS = 'ENROLLED' WHERE IMMA_ID = '" + immaID + "';";
                            c.stmt.executeUpdate(query);

                            c.conn.commit();

                            JOptionPane.showMessageDialog(null, "Fees paid!");
                            this.setVisible(false);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The student has already paid the fees!");
                    }
                }
            }
            else if(ae.getSource() == btnReset){
                Conn c = new Conn();

                try{
                    c.conn.setAutoCommit(false);

                    String immaID = studIDChoice.getSelectedItem();

                    // TRANSACTIONS - COMMIT ROLLBACK
                    try{
                        String query = "UPDATE FEES SET D_TICKET = '0', RESEARCH_VARIABLE = '0', FEES_PAID='NO';";
                        c.stmt.executeUpdate(query);
                        c.conn.commit();

                        query = "UPDATE ENROLLMENT SET IMMA_STATUS = 'ADMITTED' WHERE IMMA_ID = '" + immaID + "';";
                        c.stmt.executeUpdate(query);
                        c.conn.commit();
                    }
                    catch (Exception ee){
                        c.conn.rollback();
                    }

                    JOptionPane.showMessageDialog(null, "Warning: Fees Status changed to NOT PAID! ");
                    this.setVisible(false);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                this.setVisible(false);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Select a Student Matriculation Number!");

        }

    }

    public static void main(String[] args) {
        new FeesForm();
    }
}
