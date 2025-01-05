package hospiflow1;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


/**
 *
 * @author CabCom
 */
public class mysqlpage {
   Connection conn = null;
    public static Connection ConnectDb(){
 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/hospiflow","root","");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        } 
    }
 
}
