package student;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */

// General Libraries
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

// Plugins
import net.proteanit.sql.DbUtils; // rs2xml.jar

// Importing Custom Classes
import connections.Conn;

public class ViewSingleStudent extends JFrame implements ActionListener {
    Choice studIDChoice;
    JLabel lblHeading;
    JTable tableAll;
    JButton btnSearch, btnPrint, btnCancel;
    JScrollPane scrollPane;

    String stuID = "";

    public ViewSingleStudent(String stuID){
        this.stuID = stuID;

        // 0. Set Border Layout
        // Border Layout works best for tables, scrolling and printing
        this.setLayout(new BorderLayout());

        /* 1. Select the Student Matriculation ID to view their details */

        // 1.4 Connection to get student info with course and dept info
        tableAll = new JTable();
        try{
            Conn c = new Conn();
            String query = """
                    SELECT DISTINCT
                    S.IMMA_ID, S.FIRST_NAME, S.LAST_NAME, S.CONTACT, S.EMAIL, S.DOB, S.HOME_ADDRESS, S.INTERNATIONAL_STUDENT,
                    C.COURSE_NAME, D.DEPT_NAME,
                    E.IMMA_DATE, E.IMMA_STATUS, F.FEES_PAID
                    FROM
                    STUDENT S
                    LEFT JOIN ENROLLMENT E
                    ON S.IMMA_ID = E.IMMA_ID
                    LEFT JOIN FEES F
                    ON S.IMMA_ID = F.STUDENT_ID
                    LEFT JOIN COURSE C
                    ON E.COURSE_ID = C.COURSE_ID
                    LEFT JOIN DEPARTMENT D
                    ON C.DEPT_ID = D.DEPT_ID
                    WHERE S.IMMA_ID =
                    """ + stuID + ";";
            ResultSet rs = c.stmt.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int totalColumns = metaData.getColumnCount();

            tableAll.setModel(DbUtils.resultSetToTableModel(rs));
            tableAll.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            // Set column size
            TableColumnModel colModel = tableAll.getColumnModel();
            for(int i=0; i<totalColumns; i++){
                TableColumn tc = colModel.getColumn(i);
                tc.setPreferredWidth(130);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 1.5 Scroll Pane
        scrollPane = new JScrollPane(tableAll, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // 1.6 Border Layout for Scroll Pane
        JPanel centerPanel = new JPanel();
        centerPanel.add(scrollPane);
        centerPanel.setPreferredSize(new Dimension(900,(int)centerPanel.getPreferredSize().getHeight()));
        centerPanel.setLayout(new GridLayout(1, 3));
        this.add(centerPanel, BorderLayout.CENTER);

        /* 2. Buttons */

        // 2.1 Print
        btnPrint = new JButton("PRINT");
        btnPrint.addActionListener(this);

        // 2.2 North Border Layout
        JPanel northPanel = new JPanel();
        northPanel.add(btnPrint);
        northPanel.setPreferredSize(new Dimension(900,(int)northPanel.getPreferredSize().getHeight()));
        this.add(northPanel, BorderLayout.NORTH);

        /* 3. JFrame Configurations */

        this.setTitle("My Details");
        this.pack();
        this.setSize(900, 600);
        this.setLocation(350, 50);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == btnPrint){
            try {
                tableAll.print();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // new ViewSingleStudent();
    }
}

