package connections;

// General Libraries
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Conn {
    public Connection conn;
    public Statement stmt;

    public Conn(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://193.196.143.168/dk4s_41kusa1mst", "dk4s_41kusa1mst", "bitteaendern"
            );

            stmt = conn.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
