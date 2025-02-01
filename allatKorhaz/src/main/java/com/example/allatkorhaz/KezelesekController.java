package com.example.allatkorhaz;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.sql.*;

public class KezelesekController {
    @FXML
    private Label elmultHonapKezelesek;

    @FXML
    private TableView<Kezeles> kezelesekTable;

    @FXML
    private TableColumn<Kezeles, String> kezelesekColumn;
    @FXML
    private TableColumn<Kezeles, String> orvosColumn;
    @FXML
    private TableColumn<Kezeles, Date> datumColumn;
    @FXML
    private TableColumn<Kezeles, String> leirasColumn;

    private ObservableList<Kezeles> kezelesLista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        kezelesekTable.setPlaceholder(new Label("Nincs megjelenítendő adat.") {{
            setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-font-weight: bold;");
        }});
        initializeColumns();
        loadKezelesek();
        frissitKezelesekSzama();
    }

    private void initializeColumns() {
        kezelesekColumn.setCellValueFactory(cellData -> {
            Kezeles kezeles = cellData.getValue();
            return new SimpleStringProperty(kezeles.getKezelesekOsszesitve());
        });
        orvosColumn.setCellValueFactory(new PropertyValueFactory<>("allatorvosNeve"));
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        leirasColumn.setCellValueFactory(new PropertyValueFactory<>("megjegyzes"));

        kezelesekTable.setItems(kezelesLista);

        setLeirasCellFactory();
    }

    private String getAllatNev(int allatId) {
        String sql = "SELECT nev FROM allat WHERE allat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, allatId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nev");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAllatorvosNev(int allatorvosId) {
        String sql = "SELECT nev FROM allatorvosok WHERE allatorvos_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, allatorvosId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nev");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadKezelesek() {
        String sql = "SELECT kezeles_tipusa, allat_id, allatorvos_id, datum, megjegyzes FROM kezelesek ORDER BY datum DESC";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            kezelesLista.clear();

            while (rs.next()) {
                int allatId = rs.getInt("allat_id");
                String allatNev = getAllatNev(allatId); // Lekérdezzük az állat nevét
                String kezelesTipusa = rs.getString("kezeles_tipusa");
                int allatorvosId = rs.getInt("allatorvos_id");
                String allatorvosNev = getAllatorvosNev(allatorvosId);
                Date datum = rs.getDate("datum");
                String megjegyzes = rs.getString("megjegyzes");

                // Az új Kezeles példányban az állat nevét és a kezelés típusát összefűzzük
                kezelesLista.add(new Kezeles(kezelesTipusa, allatNev, allatorvosNev, datum, megjegyzes));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void frissitKezelesekSzama() {
        String sql = "SELECT COUNT(*) as kezeles_szam FROM kezelesek WHERE datum >= DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int kezelesekSzama = rs.getInt("kezeles_szam");
                elmultHonapKezelesek.setText(kezelesekSzama + " db");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            elmultHonapKezelesek.setText("Hiba történt!");
        }
    }

    private void setLeirasCellFactory() {
        leirasColumn.setCellFactory(tc -> {
            TableCell<Kezeles, String> cell = new TableCell<>() {
                private final Text text = new Text();

                {
                    text.wrappingWidthProperty().bind(leirasColumn.widthProperty().subtract(10));
                    text.setTextAlignment(TextAlignment.CENTER);
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        text.setText(item);
                        setGraphic(text);
                    }
                }
            };
            return cell;
        });
    }


}