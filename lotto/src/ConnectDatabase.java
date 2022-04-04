import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {

    private Connection conn;

    public ConnectDatabase() {

        conn = null;

        ConnectDatabaseToDatabase();
    }

    private void ConnectDatabaseToDatabase() {

        String url = "jdbc:mariadb://localhost:3306/skandi";

        try {

            conn = DriverManager.getConnection( url, "root", "");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

        public void closeConnect() {
            
            try {
                if( conn != null ){
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
    }

    public Connection getConnection(){ return conn; }

    
}


