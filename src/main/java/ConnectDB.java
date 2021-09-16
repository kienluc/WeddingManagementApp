
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    public static Connection getSQLServerConnection() throws SQLException{
        String url = "jdbc:sqlserver://DESKTOP-9KP06NH\\MSSQLSERVER:1433;databaseName=Weddings";
        String user = "sa";
        String password = "1234";
        Connection con = DriverManager.getConnection(url,user,password);       
        return con;       
    }     
}
