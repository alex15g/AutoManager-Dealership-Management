package ro.utcn.reprezentanta.dao;

import ro.utcn.reprezentanta.DBConnection;
import ro.utcn.reprezentanta.model.Vanzare;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VanzareDAO {

    public void addVanzare(int idClient, int idMasina, int idAngajat, double pretFinal) {

        String insertVanzare = """
            INSERT INTO vanzari (id_client, id_masina, id_angajat, data_vanzare, pret_final)
            VALUES (?, ?, ?, CAST(GETDATE() AS DATE), ?)
        """;

        String updateMasina = """
            UPDATE masini
            SET id_client = ?
            WHERE id_masina = ?
        """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(insertVanzare);
                 PreparedStatement ps2 = conn.prepareStatement(updateMasina)) {

                ps1.setInt(1, idClient);
                ps1.setInt(2, idMasina);
                ps1.setInt(3, idAngajat);
                ps1.setDouble(4, pretFinal);
                ps1.executeUpdate();

                ps2.setInt(1, idClient);
                ps2.setInt(2, idMasina);
                ps2.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Vanzare> getAllVanzari() {
        List<Vanzare> list = new ArrayList<>();

        String sql = """
           SELECT v.id_vanzare,
                                               v.data_vanzare,
                                               v.pret_final,
                                               c.nume + ' ' + c.prenume AS client,
                                               a.nume + ' ' + a.prenume AS angajat,
                                               m.marca + ' ' + m.model + ' (' + CAST(m.an_fabricatie AS VARCHAR(4)) + ')' AS masina
                                        FROM vanzari v
                                        JOIN clienti c ON c.id_client = v.id_client
                                        JOIN angajati a ON a.id_angajat = v.id_angajat
                                        JOIN masini m ON m.id_masina = v.id_masina
                                        ORDER BY v.data_vanzare DESC
                                        
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Vanzare(
                        rs.getInt("id_vanzare"),
                        rs.getDate("data_vanzare").toLocalDate(),
                        rs.getDouble("pret_final"),
                        rs.getString("client"),
                        rs.getString("masina"),
                        rs.getString("angajat")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
