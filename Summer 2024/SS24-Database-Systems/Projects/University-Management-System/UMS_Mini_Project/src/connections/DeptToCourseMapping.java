package connections;

/**
 @author Sanjay Prabhu Kunjibettu
 @author Tanay Khilare
 */


// General Classes
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Importing Custom Classes
import connections.Conn;

public class DeptToCourseMapping {
    HashMap<String, List<String>> deptToCourse;

    public DeptToCourseMapping(){
        deptToCourse = new HashMap<>();

        try{
            Conn c = new Conn();
            String query = """
                    SELECT dept_name, course_name
                    FROM DEPARTMENT D
                    LEFT JOIN COURSE C
                    ON D.dept_id = C.dept_id;
                    """;

            ResultSet rs = c.stmt.executeQuery(query);
            while(rs.next()){
                String dept = rs.getString("dept_name");
                String course = rs.getString("course_name");

                // Custom JAVA method to maintain dynamic mapping of dept-course
                insert(dept, course);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insert(String key, String value){
        // 1. Check if the key already exists
        List<String> allValues = deptToCourse.get(key);

        // 2. If the key doesn't exist, create a new mapping
        if(allValues == null){
            allValues = new ArrayList<>();
            deptToCourse.put(key, allValues);
        }

        // 3. Add the values to the new/existing list
        allValues.add(value);
    }

    public HashMap getDeptToCourseMapping(){
        return this.deptToCourse;
    }

    public String[] getDeptList(){
        return this.deptToCourse.keySet().toArray(new String[0]);
    }
}
