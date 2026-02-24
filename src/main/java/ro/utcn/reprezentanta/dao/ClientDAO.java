package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        String sql = "SELECT id_client, nume, prenume, telefon, email FROM clienti";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client c = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("telefon"),
                        rs.getString("email")
                );
                clients.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void addClient(String nume, String prenume, String telefon, String email) {
        String sql = "INSERT INTO clienti (nume, prenume, telefon, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nume);
            stmt.setString(2, prenume);
            stmt.setString(3, telefon);
            stmt.setString(4, email);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteClient(int idClient) {
        String sql = "DELETE FROM clienti WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateClient(int idClient, String nume, String prenume, String telefon, String email) {
        String sql = """
        UPDATE clienti
        SET nume = ?, prenume = ?, telefon = ?, email = ?
        WHERE id_client = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nume);
            stmt.setString(2, prenume);
            stmt.setString(3, telefon);
            stmt.setString(4, email);
            stmt.setInt(5, idClient);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
