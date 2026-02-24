package ro.utcn.reprezentanta;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Conexiune reusita la SQL Server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
