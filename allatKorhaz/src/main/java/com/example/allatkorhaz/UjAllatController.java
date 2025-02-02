package com.example.allatkorhaz;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class UjAllatController {

    @FXML
    private TextField allatNeve, allatFajtaja, allatFajtaJellege, gazdiNeve, gazdiTel;
    @FXML
    private TextArea orvosiElozmenyek;
    @FXML
    private DatePicker szulDatum;

    public boolean isString(String data) {
        if (!Character.isUpperCase(data.charAt(0))) {
            showError("Nagybetűvel kell kezdődjön!");
            return false;
        }
        for (int i = 1; i < data.length(); i++) {
            if (!Character.isLowerCase(data.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isCorrectData(String data, int size) {
        data = data.trim();
        if (data.isEmpty() || data.length() > size) {
            return false;
        }
        String reszek[] = data.split(" ");
        for (int i = 0; i < reszek.length; i++) {
            if (!isString(reszek[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean isCorrectMedicalHistory(String data, int size) {
        data = data.trim();
        return !data.isEmpty() && data.length() <= size;
    }
    public void initialize(){
        szulDatum.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }


    public void hozzaadas() {
        String nev = allatNeve.getText();
        String fajta = allatFajtaja.getText();
        String fajtajelleg = allatFajtaJellege.getText();
        String elozmeny = orvosiElozmenyek.getText();
        String gnev = gazdiNeve.getText();
        String gtel = gazdiTel.getText();
        LocalDate selectedDate = szulDatum.getValue();

        boolean ok = true;

        if (!isCorrectData(nev, 32)) {
            showError("Hibás név!");
            ok = false;
        } else if (!isCorrectData(fajta, 32)) {
            showError("Hibás fajta!");
            ok = false;
        } else if (!isCorrectData(fajtajelleg, 32)) {
            showError("Hibás fajtajelleg!");
            ok = false;
        } else if (selectedDate == null) {
            showError("Hibás dátum!");
            ok = false;
        } else if (!isCorrectData(gnev, 32)) {
            showError("Hibás gazdi név!");
            ok = false;
        } else if (!isCorrectMedicalHistory(elozmeny, 500)) {
            showError("Hibás orvosi előzmény!");
            ok = false;
        } else if (!gtel.matches("([\\+](36)(20|30|70|50)\\d{7})|((06)\\d{9})")) {
            showError("Hibás telefonszám!");
            ok = false;
        }

        if (ok) {
            String sql = "INSERT INTO allat (nev, faj, fajtajelleg, szuletesi_datum, gazdi_neve, gazdi_elerhetosege, orvosi_elozmenyek) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nev);
                pstmt.setString(2, fajta);
                pstmt.setString(3, fajtajelleg);
                pstmt.setDate(4, Date.valueOf(selectedDate));
                pstmt.setString(5, gnev);
                pstmt.setString(6, gtel);
                pstmt.setString(7, elozmeny);

                pstmt.executeUpdate();

                showInfo("Állat sikeresen hozzáadva!");
                MenuBarController.getInstance().handleAllatok();

            } catch (SQLException e) {
                showError("Adatbázis hiba történt: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("Sikertelen feltöltés!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Siker!");
        alert.setHeaderText("Sikeres feltöltés!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}