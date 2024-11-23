package de.stuttgart.europa;

// Splash Screen is the initial screen that appears for 10 miliseconds
// It gives the system time to load the configurations and U.I.

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import javax.swing.*;
import java.awt.*;

// Importing Custom Classes


public class SplashScreen extends JFrame implements Runnable {
    Thread t;

    SplashScreen(){
        ImageIcon imageIcon1 = new ImageIcon(ClassLoader.getSystemResource("images/splash_screen_university.jpg"));
        Image image1 = imageIcon1.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT);
        ImageIcon imageIcon2 = new ImageIcon(image1);

        JLabel lblImage = new JLabel(imageIcon2);
        this.add(lblImage);

        // Start Thread
        t = new Thread(this);
        t.start();

        try {
            Thread.sleep(10);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // JFrame Configurations
        this.setTitle("Hochschule für Wissenschaft Europa");
        this.setSize(1000,500);
        this.setVisible(true);
        this.setLocation(300,200);

    }

    @Override
    public void run(){
        try{
            Thread.sleep(5000);
            setVisible(false);
            new LoginAs();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SplashScreen();
    }
}
