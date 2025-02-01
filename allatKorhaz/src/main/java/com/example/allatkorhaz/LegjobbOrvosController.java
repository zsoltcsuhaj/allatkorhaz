package com.example.allatkorhaz;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LegjobbOrvosController {

    @FXML
    private Text orvosNeveText;

    @FXML
    private Text orvosSzakTeruletText;
    @FXML
    private Label kezeleseinekSzama;

    @FXML
    public void initialize() {
        loadLegjobbAllatorvos();
    }

    private void loadLegjobbAllatorvos() {
        String sql = "SELECT allatorvosok.nev, allatorvosok.szakterulet, COUNT(kezelesek.kezeles_id) AS kezelesek_szama " +
                "FROM kezelesek " +
                "JOIN allatorvosok ON kezelesek.allatorvos_id = allatorvosok.allatorvos_id " +
                "GROUP BY allatorvosok.allatorvos_id " +
                "ORDER BY COUNT(kezelesek.kezeles_id) DESC " +
                "LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                String nev = rs.getString("nev");
                String szakTerulet = rs.getString("szakterulet");
                int kezelesekSzama = rs.getInt("kezelesek_szama");


                orvosNeveText.setText(nev);
                orvosSzakTeruletText.setText(szakTerulet);
                kezeleseinekSzama.setText("Kezeléseinek száma: " + kezelesekSzama);
            } else {
                orvosNeveText.setText("Nincs adat");
                orvosSzakTeruletText.setText("Nincs adat");
                kezeleseinekSzama.setText("Nincs adat");
            }

        } catch (SQLException e) {
            System.out.println("Adatbázis hiba történt: "+e.getMessage());
        }
    }
}
