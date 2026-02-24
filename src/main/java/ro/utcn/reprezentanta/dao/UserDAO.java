package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.User;

import java.sql.*;

public class UserDAO {

    public User login(String username, String password) {

        String sql = """
            SELECT id_user, username, role
            FROM users
            WHERE username = ? AND password_hash = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
