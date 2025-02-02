package com.example.allatkorhaz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EvVizsgalatController {

    @FXML
    private TableView<EvVizsgalat> EvTableView;

    @FXML
    private TableColumn<EvVizsgalat, String> EvNevColumn, EvFajColumn, EvSzuletesiDatumColumn, EvGazdiNeveColumn, EvGazdiElerhetosegeColumn, EvLegutobbiVizsgalatColumn;

    private ObservableList<EvVizsgalat> evLista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        EvTableView.setPlaceholder(new Label("Nincs megjelenítendő adat.") {{
            setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
        }});
        EvNevColumn.setCellValueFactory(new PropertyValueFactory<>("nev"));
        EvFajColumn.setCellValueFactory(new PropertyValueFactory<>("faj"));
        EvSzuletesiDatumColumn.setCellValueFactory(new PropertyValueFactory<>("szuletesiDatum"));
        EvGazdiNeveColumn.setCellValueFactory(new PropertyValueFactory<>("gazdiNeve"));
        EvGazdiElerhetosegeColumn.setCellValueFactory(new PropertyValueFactory<>("gazdiElerhetosege"));
        EvLegutobbiVizsgalatColumn.setCellValueFactory(new PropertyValueFactory<>("legutobbiVizsgalat"));

        EvLegutobbiVizsgalatColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item.equals("Nem volt még vizsgálaton")) {
                    setText(item);
                } else {
                    setText(item);
                }
            }
        });

        loadEvVizsgalat();
    }

    private void loadEvVizsgalat() {
        String query = """
                SELECT * FROM (
                    SELECT allat.nev, allat.faj, allat.szuletesi_datum, allat.gazdi_neve,
                           allat.gazdi_elerhetosege,
                           COALESCE(MAX(kezelesek.datum), 'Nem volt még kezelésen') AS legutobbi_vizsgalat
                    FROM allat 
                    LEFT JOIN kezelesek ON allat.allat_id = kezelesek.allat_id
                    GROUP BY allat.szuletesi_datum DESC
                ) AS sub
                WHERE legutobbi_vizsgalat = 'Nem volt még kezelésen' 
                   OR legutobbi_vizsgalat < DATE_SUB(CURDATE(), INTERVAL 1 YEAR)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            evLista.clear();

            while (rs.next()) {
                evLista.add(new EvVizsgalat(
                        rs.getString("nev"),
                        rs.getString("faj"),
                        rs.getString("szuletesi_datum"),
                        rs.getString("gazdi_neve"),
                        rs.getString("gazdi_elerhetosege"),
                        rs.getString("legutobbi_vizsgalat")
                ));
            }

            EvTableView.setItems(evLista);

        } catch (SQLException e) {
            System.out.println("Adatbázis hiba történt: "+e.getMessage());
        }
    }
}
