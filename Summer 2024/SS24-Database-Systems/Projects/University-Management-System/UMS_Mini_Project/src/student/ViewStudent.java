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

public class ViewStudent extends JFrame implements ActionListener {
    Choice studIDChoice;
    JLabel lblHeading;
    JTable tableAll;
    JButton btnSearch, btnPrint, btnCancel;
    JScrollPane scrollPane;

    public ViewStudent(){

        // 0. Set Border Layout
        // Border Layout works best for tables, scrolling and printing
        this.setLayout(new BorderLayout());

        /* 1. Select the Student Matriculation ID to view their details */

        // 1.1 Label
        lblHeading = new JLabel("Choose a Matriculation ID: ");

        // 1.2 Choice
        studIDChoice = new Choice();
        studIDChoice.add("All");

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
        northPanel.add(studIDChoice);
        northPanel.add(btnPrint);
        northPanel.add(btnSearch);
        northPanel.add(btnCancel);
        northPanel.setPreferredSize(new Dimension(900,(int)northPanel.getPreferredSize().getHeight()));
        this.add(northPanel, BorderLayout.NORTH);

        /* 3. JFrame Configurations */

        this.setTitle("Student Details");
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
                String choiceItem = studIDChoice.getSelectedItem();
                if(!choiceItem.equals("All")){
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
                    ON C.DEPT_ID = D.DEPT_ID;
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
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else if (ae.getSource() == btnPrint){
            try {
                tableAll.print();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            // Cancel Transaction
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new ViewStudent();
    }
}
