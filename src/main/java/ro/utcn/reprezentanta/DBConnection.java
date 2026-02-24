package ro.utcn.reprezentanta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:sqlserver://localhost:1433;" +
                    "databaseName=gestionare_reprezentanta_auto;" +
                    "encrypt=true;trustServerCertificate=true";

    private static final String USER = "javauser";
    private static final String PASS = "Java123!";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
