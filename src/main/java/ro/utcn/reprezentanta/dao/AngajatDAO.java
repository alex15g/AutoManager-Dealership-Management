package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.Angajat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AngajatDAO {

    public List<Angajat> getAllAngajati() {
        List<Angajat> list = new ArrayList<>();

        String sql = """
    SELECT id_angajat, nume, prenume, functie, email, salariu
    FROM angajati
""";


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new Angajat(
                        rs.getInt("id_angajat"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("functie"),
                        rs.getString("email"),
                        rs.getDouble("salariu")
                ));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addAngajat(String nume, String prenume,
                           String functie, String email, double salariu) {

        String sql = """
            INSERT INTO angajati (nume, prenume, functie, email, salariu)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nume);
            stmt.setString(2, prenume);
            stmt.setString(3, functie);
            stmt.setString(4, email);
            stmt.setDouble(5, salariu);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAngajat(int idAngajat, String nume,
                              String prenume, String functie, String email, double salariu) {

        String sql = """
        UPDATE angajati
        SET nume = ?, prenume = ?, functie = ?, email = ?, salariu = ?
        WHERE id_angajat = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nume);
            stmt.setString(2, prenume);
            stmt.setString(3, functie);
            stmt.setString(4, email);
            stmt.setDouble(5, salariu);
            stmt.setInt(6, idAngajat);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAngajat(int idAngajat) {
        String sql = "DELETE FROM angajati WHERE id_angajat = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAngajat);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
