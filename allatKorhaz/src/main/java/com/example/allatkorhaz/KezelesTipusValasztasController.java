package com.example.allatkorhaz;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KezelesTipusValasztasController {

    @FXML
    private ComboBox<String> kezelesTipusComboBox;

    @FXML
    private void initialize() {
        loadKezelesTipusok();
    }

    private void loadKezelesTipusok() {
        String sql = "SELECT DISTINCT kezeles_tipusa FROM kezelesek";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String kezelesTipus = rs.getString("kezeles_tipusa");
                kezelesTipusComboBox.getItems().add(kezelesTipus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void kezelesKeresese() {
        String selectedTipus = kezelesTipusComboBox.getValue();
        if (selectedTipus != null) {
            if (vanAdat(selectedTipus)) {
                MenuBarController.getInstance().handleElmultHet(selectedTipus);
            } else {
                showNoDataAlert();
            }
        } else {
            showNoDataAlert();
        }
    }

    private boolean vanAdat(String kezelesTipus) {
        String sql = "SELECT COUNT(*) as count FROM kezelesek WHERE kezeles_tipusa = ? AND datum >= DATE_SUB(CURRENT_DATE, INTERVAL 1 WEEK)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kezelesTipus);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showNoDataAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Nincs elérhető adat");
        alert.setHeaderText("Nincs megjeleníthető kezelés");
        alert.setContentText("A kiválasztott kezelés típusával kapcsolatban nincs adat az elmúlt hétre vonatkozóan.");
        alert.showAndWait();
    }
}

