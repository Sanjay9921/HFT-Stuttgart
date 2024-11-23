package de.stuttgart.europa;

// Page is followed after SplashScreen.java
// Admin Login Page

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Importing Custom Classes
import admin.AdminMain;
import com.mysql.cj.log.Log;
import connections.Conn;

public class AdminLogin extends JFrame implements ActionListener {
    JLabel lblUserName, lblPassword, lblBG;
    JTextField tfUserName;
    JPasswordField pfPassword;
    JButton btnLogin, btnBack;

    ImageIcon icon1, iconBG, icon2, iconAdmin;
    Image imgBG, imgAdmin;

    public AdminLogin(){

        /* 1. Admin User Name */

        // 1.1 Label
        lblUserName = new JLabel("Username: ");
        lblUserName.setBounds(70, 20, 100, 20);
        this.add(lblUserName);

        // 1.2 Text Field
        tfUserName = new JTextField();
        tfUserName.setBounds(180, 20, 150, 20);
        this.add(tfUserName);

        /* 2. Admin Password */

        // 2.1 Label
        lblPassword = new JLabel("Password: ");
        lblPassword.setBounds(70, 70, 100, 20);
        this.add(lblPassword);

        // 1.2 Text Field
        pfPassword = new JPasswordField();
        pfPassword.setBounds(180, 70, 150, 20);
        this.add(pfPassword);

        /* 3. Buttons */

        // 3.1 Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(50, 140, 120, 30);
        btnLogin.setBackground(Color.black);
        btnLogin.setForeground(Color.white);
        btnLogin.addActionListener(this);
        this.add(btnLogin);

        // 3.2 Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(200, 140, 120, 30);
        btnBack.setBackground(Color.black);
        btnBack.setForeground(Color.white);
        btnBack.addActionListener(this);
        this.add(btnBack);

        /* 4. Images */

        // 4.1 Background
        icon1 = new ImageIcon(ClassLoader.getSystemResource("./images/admin_login_bg.jpg"));
        imgBG = icon1.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT);
        iconBG = new ImageIcon(imgBG);
        lblBG = new JLabel(iconBG);
        lblBG.setBounds(0, 0, 500, 350);
        this.add(lblBG);

        // JFrame Configurations
        this.setTitle("Admin Login");
        this.setSize(500,350);
        this.setLocation(400,300);
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == btnLogin){
            String adminUserName = tfUserName.getText();
            String adminPassword = new String(pfPassword.getPassword());

            System.out.println(adminUserName + " " + adminPassword);

            String query = "SELECT * FROM ADMINISTRATOR WHERE USER_ID = ? AND PSWD = ?";

            try{
                Conn c = new Conn();
                PreparedStatement preparedStatement = c.conn.prepareStatement(query);
                preparedStatement.setString(1, adminUserName);
                preparedStatement.setString(2, adminPassword);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    JOptionPane.showMessageDialog(null, "You have successfully logged in !");
                    setVisible(false);
                    new AdminMain();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid username or password !");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(ae.getSource() == btnBack){
            this.setVisible(false);
            new LoginAs();
        }
    }

    public static void main(String[] args) {
        new AdminLogin();
    }
}
