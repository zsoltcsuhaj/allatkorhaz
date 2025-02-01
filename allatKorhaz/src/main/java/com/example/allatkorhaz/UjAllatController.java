package com.example.allatkorhaz;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.time.LocalDate;

public class UjAllatController {
    private Connection conn;

    @FXML
    private TextField allatNeve, allatFajtaja, allatFajtaJellege, gazdiNeve, gazdiTel;
    @FXML
    private TextArea orvosiElozmenyek;
    @FXML
    private DatePicker szulDatum;

    public boolean isString(String data) {
        if (!Character.isUpperCase(data.charAt(0))) {
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

    public void initialize() {
        conn = DatabaseConnection.getConnection();
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
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás név!");
            alertNemJo.show();
            ok = false;
        } else if (!isCorrectData(fajta, 32)) {
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás fajta!");
            alertNemJo.show();
            ok = false;
        } else if (!isCorrectData(fajtajelleg, 32)) {
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás fajtajelleg!");
            alertNemJo.show();
            ok = false;
        } else if (selectedDate == null) {
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás dátum!");
            alertNemJo.show();
            ok = false;
        } else if (!isCorrectData(gnev, 32)) {
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás gazdi név!");
            alertNemJo.show();
            ok = false;
        } else if (!isCorrectMedicalHistory(elozmeny, 500)) {
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás orvosi előzmény!");
            alertNemJo.show();
            ok = false;
        } else if (!gtel.matches("([\\+](36)(20|30|70|50)\\d{7})|((06)\\d{9})")) {
            Alert alertNemJo = new Alert(Alert.AlertType.ERROR, "Hibás telefonszám!");
            alertNemJo.show();
            ok = false;
        }

        if (ok) {
            String parancs = "INSERT INTO allat (nev, faj, fajtajelleg, szuletesi_datum, gazdi_neve, gazdi_elerhetosege, orvosi_elozmenyek)" + " VALUES ('" + nev + "', '" + fajta + "', '" + fajtajelleg + "', '" + selectedDate + "', '" + gnev + "', '" + gtel + "', '" + elozmeny + "')";
            try {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(parancs);
                Alert alertJo = new Alert(Alert.AlertType.INFORMATION, "Állat sikeresen hozzáadva!");
                alertJo.show();
                MenuBarController.getInstance().handleAllatok();
            } catch (SQLException s) {
                System.out.println(s.getMessage());
            }
        }
    }
}