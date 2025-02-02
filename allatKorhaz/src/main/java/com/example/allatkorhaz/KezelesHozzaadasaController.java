package com.example.allatkorhaz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class KezelesHozzaadasaController {

    @FXML
    private ComboBox<String> allatNevComboBox;
    @FXML
    private ComboBox<String> orvosNevComboBox;
    @FXML
    private TextField kezelesTipusaTextField;
    @FXML
    private DatePicker datumPicker;
    @FXML
    private TextArea megjegyzesTextArea;


    public void initialize() {
        try {
            datumPicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item.isAfter(LocalDate.now())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    }
                }
            });
            feltoltAllatokComboBox();
            feltoltOrvosokComboBox();
            datumPicker.setValue(LocalDate.now());
        } catch (SQLException e) {
            Alert alertHiba = new Alert(Alert.AlertType.ERROR, "Adatbázis kapcsolódási hiba: " + e.getMessage());
            alertHiba.show();
        }
    }

    private void feltoltAllatokComboBox() throws SQLException {
        ObservableList<String> allatok = FXCollections.observableArrayList();
        String query = "SELECT nev FROM allat ORDER BY nev";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                allatok.add(rs.getString("nev"));
            }
            allatNevComboBox.setItems(allatok);
        }
    }

    private void feltoltOrvosokComboBox() throws SQLException {
        ObservableList<String> orvosok = FXCollections.observableArrayList();
        String query = "SELECT nev, szakterulet FROM allatorvosok ORDER BY nev";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                orvosok.add(rs.getString("nev") + " - " + rs.getString("szakterulet"));
            }
            orvosNevComboBox.setItems(orvosok);
        }
    }

    private Integer getAllatId(String allatNev) throws SQLException {
        String query = "SELECT allat_id FROM allat WHERE nev = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, allatNev);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("allat_id");
            }
            return null;
        }
    }

    private Integer getAllatorvosId(String orvosNev) throws SQLException {
        String query = "SELECT allatorvos_id FROM allatorvosok WHERE nev = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, orvosNev);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("allatorvos_id");
            }
            return null;
        }
    }

    @FXML
    public void kezelesHozzaadas() {
        String allatNev = allatNevComboBox.getValue();
        String orvosNevTeljes = orvosNevComboBox.getValue();
        String kezelesTipus = kezelesTipusaTextField.getText().trim();
        LocalDate datum = datumPicker.getValue();
        String megjegyzes = megjegyzesTextArea.getText().trim();

        if (allatNev == null || allatNev.isEmpty()) {
            showError("Válasszon ki egy állatot!");
            return;
        }
        if (orvosNevTeljes == null || orvosNevTeljes.isEmpty()) {
            showError("Válasszon ki egy állatorvost!");
            return;
        }

        String orvosNev = orvosNevTeljes.split(" - ")[0];

        if (kezelesTipus.isEmpty() || kezelesTipus.length() > 32) {
            showError("A kezelés típusa nem lehet üres vagy 32 karakternél hosszabb!");
            return;
        }
        if (datum == null) {
            showError("Adja meg a kezelés dátumát!");
            return;
        }
        if (megjegyzes.isEmpty()) {
            showError("A megjegyzés nem lehet üres!");
            return;
        }

        try {
            Integer allatId = getAllatId(allatNev);
            Integer orvosId = getAllatorvosId(orvosNev);

            if (allatId == null) {
                showError("A kiválasztott állat nem található az adatbázisban!");
                return;
            }
            if (orvosId == null) {
                showError("A kiválasztott állatorvos nem található az adatbázisban!");
                return;
            }

            String insertQuery = "INSERT INTO kezelesek (datum, kezeles_tipusa, megjegyzes, allat_id, allatorvos_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

                pstmt.setDate(1, Date.valueOf(datum));
                pstmt.setString(2, kezelesTipus);
                pstmt.setString(3, megjegyzes);
                pstmt.setInt(4, allatId);
                pstmt.setInt(5, orvosId);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    showInfo("A kezelés sikeresen rögzítve!");
                    MenuBarController.getInstance().handleKezelesTipusai();
                } else {
                    showError("Nem sikerült rögzíteni a kezelést!");
                }

            }
        } catch (SQLException e) {
            showError("Adatbázis hiba: " + e.getMessage());
        }
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Információ");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}