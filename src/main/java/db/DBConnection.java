package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;
    private final Connection connection;

    public DBConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/thogakade2", "root", "12345");
    }

    public static DBConnection getInstance() throws SQLException {
        if(dbConnection == null){
            dbConnection = new DBConnection();
        }

        return dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
