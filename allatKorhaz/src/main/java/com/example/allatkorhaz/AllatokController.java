package com.example.allatkorhaz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllatokController {

    @FXML
    private TableView<Allat> tableView;
    @FXML
    private TableColumn<Allat, String> nevColumn, fajColumn, fajtajellegColumn, szuletesiDatumColumn, gazdiNeveColumn, gazdiElerhetosegeColumn, orvosiElozmenyekColumn;

    private ObservableList<Allat> allatokLista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Oszlopok beállítása
        nevColumn.setCellValueFactory(new PropertyValueFactory<>("nev"));
        fajColumn.setCellValueFactory(new PropertyValueFactory<>("faj"));
        fajtajellegColumn.setCellValueFactory(new PropertyValueFactory<>("fajtajelleg"));
        szuletesiDatumColumn.setCellValueFactory(new PropertyValueFactory<>("szuletesiDatum"));
        gazdiNeveColumn.setCellValueFactory(new PropertyValueFactory<>("gazdiNeve"));
        gazdiElerhetosegeColumn.setCellValueFactory(new PropertyValueFactory<>("gazdiElerhetosege"));
        orvosiElozmenyekColumn.setCellValueFactory(new PropertyValueFactory<>("orvosiElozmenyek"));

        // Adatok betöltése
        loadAllatok();
    }

    public void refreshTable() {
        loadAllatok();
    }

    private void loadAllatok() {
        String query = "SELECT nev, faj, fajtajelleg, szuletesi_datum, gazdi_neve, gazdi_elerhetosege, orvosi_elozmenyek FROM allat";

        // Minden alkalommal új kapcsolatot nyitunk
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            allatokLista.clear();

            while (rs.next()) {
                allatokLista.add(new Allat(
                        rs.getString("nev"),
                        rs.getString("faj"),
                        rs.getString("fajtajelleg"),
                        rs.getString("szuletesi_datum"),
                        rs.getString("gazdi_neve"),
                        rs.getString("gazdi_elerhetosege"),
                        rs.getString("orvosi_elozmenyek")
                ));
            }

            tableView.setItems(allatokLista);

        } catch (SQLException e) {
            e.printStackTrace();
            // Itt érdemes lenne egy felhasználóbarát hibaüzenetet megjeleníteni
        }
    }
}