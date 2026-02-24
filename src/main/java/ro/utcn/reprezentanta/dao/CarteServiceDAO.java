package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.CarteService;

import java.sql.*;
import java.time.LocalDate;

public class CarteServiceDAO {

    public CarteService getCarteServiceByMasina(int idMasina) {

        String sql = """
        SELECT id_carte_service,
               km_initiali,
               ultima_revizie,
               data_expirare_garantie
        FROM carte_service
        WHERE id_masina = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CarteService(
                        rs.getInt("id_carte_service"),
                        rs.getInt("km_initiali"),
                        rs.getDate("ultima_revizie").toLocalDate(),
                        rs.getDate("data_expirare_garantie").toLocalDate()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // 👇 METODA TA (AICI)
    public void createCarteServiceForMasina(
            int idMasina,
            int kmInitiali,
            LocalDate dataExpirareGarantie
    ) {

        String sql = """
        INSERT INTO carte_service (id_masina, km_initiali, ultima_revizie, data_expirare_garantie)
        VALUES (?, ?, GETDATE(), ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            stmt.setInt(2, kmInitiali);
            stmt.setDate(3, Date.valueOf(dataExpirareGarantie));

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateUltimaRevizieByMasina(int idMasina) {

        String sql = """
        UPDATE carte_service
        SET ultima_revizie = (
            SELECT MAX(data_revizie)
            FROM revizii
            WHERE revizii.id_masina = ?
        )
        WHERE id_carte_service = (
            SELECT id_carte_service
            FROM masini
            WHERE id_masina = ?
        )
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            stmt.setInt(2, idMasina);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteCarteServiceByMasina(int idMasina) {

        String sql = "DELETE FROM carte_service WHERE id_masina = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMasina);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
