package instructor;

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

// Import Plugins
import net.proteanit.sql.DbUtils; // rs2xml.jar

// Importing Custom Classes
import connections.Conn;

public class ViewInstructor extends JFrame implements ActionListener {
    Choice empIDChoice;
    JLabel lblHeading;
    JTable tableAll;
    JButton btnSearch, btnPrint, btnCancel;
    JScrollPane scrollPane;

    public ViewInstructor(){

        // 0. Set Border Layout
        // Border Layout works best for tables, scrolling and printing
        this.setLayout(new BorderLayout());

        /* 1. Select the Instructor ID to view their details */

        // 1.1 Label
        lblHeading = new JLabel("Choose an Employee ID: ");

        // 1.2 Choice
        empIDChoice = new Choice();
        empIDChoice.add("All");

        // 1.3 Connection to get a list of choice of emp ids
        try{
            Conn c = new Conn();
            String query = "select distinct emp_id from INSTRUCTOR;";
            ResultSet rs = c.stmt.executeQuery(query);
            while(rs.next()){
                empIDChoice.add(rs.getString("emp_id"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 1.4 Connection to get instructor info with course and dept info
        tableAll = new JTable();
        try{
            Conn c = new Conn();
            String query = """
                    SELECT DISTINCT
                    I.EMP_ID, I.FIRST_NAME, I.LAST_NAME, I.CONTACT, I.EMAIL, I.DOB, I.HOME_ADDRESS, I.HFT_CABIN,
                    C.COURSE_NAME, D.DEPT_NAME
                    FROM
                    INSTRUCTOR I
                    LEFT JOIN COURSE C
                    ON I.EMP_ID = C.INSTRUCTOR_ID
                    LEFT JOIN DEPARTMENT D
                    ON C.DEPT_ID = D.DEPT_ID;
                    """;
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

        // 2.1 Search
        btnSearch = new JButton("SEARCH");
        btnSearch.addActionListener(this);

        // 2.2 Print
        btnPrint = new JButton("PRINT");
        btnPrint.addActionListener(this);

        // 2.3 Cancel the operation
        btnCancel = new JButton("CANCEL");
        btnCancel.addActionListener(this);

        // 2.4 North Border Layout
        JPanel northPanel = new JPanel();
        northPanel.add(lblHeading);
        northPanel.add(empIDChoice);
        northPanel.add(btnPrint);
        northPanel.add(btnSearch);
        northPanel.add(btnCancel);
        northPanel.setPreferredSize(new Dimension(900,(int)northPanel.getPreferredSize().getHeight()));
        this.add(northPanel, BorderLayout.NORTH);

        /* 3. JFrame Configurations */

        this.setTitle("Instructor Details");
        this.pack();
        this.setSize(900, 600);
        this.setLocation(350, 50);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == btnSearch){
            try {
                Conn c = new Conn();
                String choiceItem = empIDChoice.getSelectedItem();
                if(!choiceItem.equals("All")){
                    String query = """
                    SELECT DISTINCT
                    I.EMP_ID, I.FIRST_NAME, I.LAST_NAME, I.CONTACT, I.EMAIL, I.DOB, I.HOME_ADDRESS, I.HFT_CABIN,
                    C.COURSE_NAME, D.DEPT_NAME
                    FROM
                    INSTRUCTOR I
                    LEFT JOIN COURSE C
                    ON I.EMP_ID = C.INSTRUCTOR_ID
                    LEFT JOIN DEPARTMENT D
                    ON C.DEPT_ID = D.DEPT_ID
                    WHERE I.EMP_ID =
                    """ + choiceItem + ";";

                    ResultSet resultSet = c.stmt.executeQuery(query);

                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int totalColumns = metaData.getColumnCount();

                    tableAll.setModel(DbUtils.resultSetToTableModel(resultSet));
                    tableAll.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    // Set column size
                    TableColumnModel colModel = tableAll.getColumnModel();
                    for(int i=0; i<totalColumns; i++){
                        TableColumn tc = colModel.getColumn(i);
                        tc.setPreferredWidth(130);
                    }
                }
                else{
                    String query = """
                    SELECT DISTINCT
                    I.EMP_ID, I.FIRST_NAME, I.LAST_NAME, I.CONTACT, I.EMAIL, I.DOB, I.HOME_ADDRESS, I.HFT_CABIN,
                    C.COURSE_NAME, D.DEPT_NAME
                    FROM
                    INSTRUCTOR I
                    LEFT JOIN COURSE C
                    ON I.EMP_ID = C.INSTRUCTOR_ID
                    LEFT JOIN DEPARTMENT D
                    ON C.DEPT_ID = D.DEPT_ID
                    """;

                    ResultSet resultSet = c.stmt.executeQuery(query);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int totalColumns = metaData.getColumnCount();

                    tableAll.setModel(DbUtils.resultSetToTableModel(resultSet));
                    tableAll.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    // Set column size
                    TableColumnModel colModel = tableAll.getColumnModel();
                    for(int i=0; i<totalColumns; i++){
                        TableColumn tc = colModel.getColumn(i);
                        tc.setPreferredWidth(130);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if (ae.getSource() == btnPrint){
            try {
                tableAll.print();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            // Cancel Transaction
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ViewInstructor();
    }
}