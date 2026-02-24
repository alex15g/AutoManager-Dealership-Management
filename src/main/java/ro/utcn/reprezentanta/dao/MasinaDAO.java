package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.Masina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MasinaDAO {

    public List<Masina> getAllMasini() {
        List<Masina> masini = new ArrayList<>();

        String sql = "SELECT id_masina, marca, model, an_fabricatie, pret, id_client FROM masini\n";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                masini.add(new Masina(
                        rs.getInt("id_masina"),
                        rs.getString("marca"),
                        rs.getString("model"),
                        rs.getInt("an_fabricatie"),
                        rs.getDouble("pret"),
                        rs.getInt("id_client")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return masini;
    }

    public void addMasina(
            String marca,
            String model,
            int anFabricatie,
            double pret,
            int idClient
    ) {
        String sql = """
        INSERT INTO masini (marca, model, an_fabricatie, pret, id_client)
        VALUES (?, ?, ?, ?, ?)
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca);
            stmt.setString(2, model);
            stmt.setInt(3, anFabricatie);
            stmt.setDouble(4, pret);
            stmt.setInt(5, idClient);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteMasina(int idMasina) {
        String sql = "DELETE FROM masini WHERE id_masina = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMasina(int idMasina, String marca, String model, int an, double pret) {
        String sql = """
        UPDATE masini
        SET marca = ?, model = ?, an_fabricatie = ?, pret = ?
        WHERE id_masina = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca);
            stmt.setString(2, model);
            stmt.setInt(3, an);
            stmt.setDouble(4, pret);
            stmt.setInt(5, idMasina);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Masina> getMasiniByClient(int idClient) {

        List<Masina> list = new ArrayList<>();

        String sql = """
        SELECT id_masina, marca, model, an_fabricatie, pret, id_client
        FROM masini
        WHERE id_client = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Masina(
                        rs.getInt("id_masina"),
                        rs.getString("marca"),
                        rs.getString("model"),
                        rs.getInt("an_fabricatie"),
                        rs.getDouble("pret"),
                        rs.getInt("id_client")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Masina> getMasiniDisponibile() {

        List<Masina> list = new ArrayList<>();

        String sql = """
        SELECT m.*
        FROM masini m
        WHERE NOT EXISTS (
            SELECT 1 FROM vanzari v
            WHERE v.id_masina = m.id_masina
        )
    """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Masina(
                        rs.getInt("id_masina"),
                        rs.getString("marca"),
                        rs.getString("model"),
                        rs.getInt("an_fabricatie"),
                        rs.getDouble("pret"),
                        rs.getInt("id_client")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }





}
