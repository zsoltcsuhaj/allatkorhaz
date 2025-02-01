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


public class ElmultHetController {

    @FXML
    private TableView<ElmultHet> EHtableView;
    @FXML
    private TableColumn<ElmultHet, String> EHnevColumn, EHfajColumn, EHszuletesiDatumColumn, EHgazdiNeveColumn, EHkezelesDatumaColumn, EHkezelesTipusaColumn;

    private ObservableList<ElmultHet> elmultHetLista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        EHnevColumn.setCellValueFactory(new PropertyValueFactory<>("allatNev"));
        EHfajColumn.setCellValueFactory(new PropertyValueFactory<>("allatFaj"));
        EHszuletesiDatumColumn.setCellValueFactory(new PropertyValueFactory<>("szuletesiDatum"));
        EHgazdiNeveColumn.setCellValueFactory(new PropertyValueFactory<>("gazdiNeve"));
        EHkezelesDatumaColumn.setCellValueFactory(new PropertyValueFactory<>("kezelesDatum"));
        EHkezelesTipusaColumn.setCellValueFactory(new PropertyValueFactory<>("kezelesTipusa"));


    }

    public void loadElmultHet(String kivalasztottKezeles) {
        String query = "SELECT a.nev, a.faj, a.szuletesi_datum, a.gazdi_neve, k.datum, k.kezeles_tipusa " +
                "FROM kezelesek k " +
                "JOIN allat a ON k.allat_id = a.allat_id " +
                "WHERE k.kezeles_tipusa = ? AND k.datum >= CURDATE() - INTERVAL 1 WEEK";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, kivalasztottKezeles);

            ResultSet rs = stmt.executeQuery();
            elmultHetLista.clear();

            while (rs.next()) {
                ElmultHet elmultHet = new ElmultHet(
                        rs.getString("kezeles_tipusa"),
                        rs.getString("nev"),
                        rs.getString("faj"),
                        rs.getString("szuletesi_datum"),
                        rs.getString("gazdi_neve"),
                        rs.getDate("datum")
                );

                elmultHetLista.add(elmultHet);
            }


            EHtableView.setItems(elmultHetLista);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
