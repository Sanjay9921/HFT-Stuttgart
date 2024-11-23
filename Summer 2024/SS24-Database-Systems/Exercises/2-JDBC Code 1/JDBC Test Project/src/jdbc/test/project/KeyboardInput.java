package jdbc.test.project;

import java.util.Scanner;

/**
 * @author Dorothee Koch
 * @version March 2023
 * Class methods for handling input from the keyboard.
 * Most of the ideas stem from Julie Zelenski, Stanford University.
 */
public class KeyboardInput {
    static Scanner stdin = new Scanner(System.in);

    /**
     * This reads the input from the keyboard and returns it as
     * an integer type.
     */
    public static int readInt() {
        return Integer.parseInt(readString());
    }

    /**
     * This reads the input from the keyboard and returns it as
     * an integer type. One can provide a message that is displayed
     * to tell the user what is expected.
     */
    public static int readInt(String text) {
        System.out.print(text);
        return readInt();
    }

    public static double readDouble() {
        return Double.parseDouble(readString());
    }

    public static char readChar() {
        return readString().charAt(0);
    }

    public static String readString() {
        return stdin.nextLine();
    }

    public static String readString(String text) {
        System.out.print(text);
        return readString();
    }

    public static String readPassword(String text) {
        System.out.print(text);
        if (System.console() != null) {
            return new String(System.console().readPassword());
        } else {
            return readString();
        }
    }
}
