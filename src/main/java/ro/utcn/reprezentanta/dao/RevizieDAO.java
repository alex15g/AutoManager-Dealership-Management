package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.Revizie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevizieDAO {
    public List<Revizie> getReviziiByMasina(int idMasina) {

        List<Revizie> list = new ArrayList<>();

        String sql = """
        SELECT id_revizie, data_revizie, km, descriere, cost
        FROM revizii
        WHERE id_masina = ?
        ORDER BY data_revizie DESC
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Revizie(
                        rs.getInt("id_revizie"),
                        rs.getDate("data_revizie").toLocalDate(),
                        rs.getInt("km"),
                        rs.getString("descriere"),
                        rs.getDouble("cost")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addRevizie(
            int idMasina,
            int km,
            String descriere,
            double cost
    ) {

        String sql = """
        INSERT INTO revizii (id_masina, data_revizie, km, descriere, cost)
        VALUES (?, GETDATE(), ?, ?, ?)
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            stmt.setInt(2, km);
            stmt.setString(3, descriere);
            stmt.setDouble(4, cost);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRevizie(int idRevizie) {

        String sql = "DELETE FROM revizii WHERE id_revizie = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRevizie);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteReviziiByMasina(int idMasina) {
        String sql = "DELETE FROM revizii WHERE id_masina = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateRevizie(
            int idRevizie,
            int km,
            String descriere,
            double cost
    ) {

        String sql = """
        UPDATE revizii
        SET km = ?, descriere = ?, cost = ?
        WHERE id_revizie = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, km);
            stmt.setString(2, descriere);
            stmt.setDouble(3, cost);
            stmt.setInt(4, idRevizie);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getMaxKmByMasina(int idMasina) {

        String sql = """
        SELECT MAX(km) AS max_km
        FROM revizii
        WHERE id_masina = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("max_km");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // NU există revizii
    }



}
