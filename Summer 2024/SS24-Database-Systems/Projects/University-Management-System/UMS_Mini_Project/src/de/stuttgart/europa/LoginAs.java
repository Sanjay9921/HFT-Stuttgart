package de.stuttgart.europa;

// General Libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginAs extends JFrame implements ActionListener {
    JLabel lblHeading;
    JButton btnAdmin, btnStudent, btnInstructor;

    public LoginAs(){
        /* 1. Main Heading */

        lblHeading = new JLabel("Welcome to the Login Page!");
        lblHeading.setBounds(70, 20, 300, 30);
        lblHeading.setFont(new Font("serif",Font.BOLD,21));
        this.add(lblHeading);

        /* 2. Buttons */

        // 2.1 Admin Button
        btnAdmin = new JButton("Login as Admin");
        btnAdmin.setBounds(100, 80, 150, 30);
        btnAdmin.setBackground(Color.black);
        btnAdmin.setForeground(Color.white);
        btnAdmin.addActionListener(this);
        this.add(btnAdmin);

        // 2.2 Student Button
        btnStudent = new JButton("Login as Student");
        btnStudent.setBounds(100, 130, 150, 30);
        btnStudent.setBackground(Color.black);
        btnStudent.setForeground(Color.white);
        btnStudent.addActionListener(this);
        this.add(btnStudent);

        // 2.3 Instructor Button
        btnInstructor = new JButton("Login as Instructor");
        btnInstructor.setBounds(100, 180, 150, 30);
        btnInstructor.setBackground(Color.black);
        btnInstructor.setForeground(Color.white);
        btnInstructor.addActionListener(this);
        this.add(btnInstructor);

        // JFrame Configurations
        this.setTitle("User Login Page");
        this.setSize(400,300);
        this.setLocation(400,300);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == btnAdmin){
            this.setVisible(false);
            new AdminLogin();
        }
        else if(ae.getSource() == btnStudent){
            this.setVisible(false);
            new StudentLogin();
        }
        else if(ae.getSource() == btnInstructor){
            this.setVisible(false);
            new InstructorLogin();
        }
    }

    public static void main(String[] args) {
        new LoginAs();
    }
}
