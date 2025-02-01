package com.example.allatkorhaz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MenuBarController implements IViews {
    @FXML
    private StackPane contentPane;

    private static MenuBarController instance;

    @FXML
    public void initialize() {
        instance = this;
        handleAllatok();
    }

    public static MenuBarController getInstance() {
        return instance;
    }

    public void loadView(String fxmlfile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
            StackPane newView = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlfile);
            e.printStackTrace();
        }
    }

    public Object loadView2(String fxmlfile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
            StackPane newView = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);

            return loader.getController();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlfile);
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void handleUjAllat() {
        loadView(UJALLAT);
    }

    @FXML
    public void handleLegjobbAllatorvos() {
        loadView(LEGJOBBALLATORVOS);
    }

    @FXML
    public void handleKontrollVizsgalat() {
        loadView(KONTROLLVIZSGALAT);
    }

    @FXML
    public void handleKezelesTipusai() {
        loadView(KEZELESTIPUSAI);
    }

    @FXML
    public void handleKezelesHozzaadasa() {
        loadView(KEZELESHOZZAADASA);
    }

    @FXML
    public void handleAllatok() {
        loadView(ALLATOK);
    }

    @FXML
    public void handleKezelesTipusValasztas() {
        loadView(KEZELESTIPUSVALASZTAS);
    }

    @FXML
    public void handleElmultHet(String kivalasztottKezeles) {
        Object controllerObj = loadView2(ELMULTHET);

        if (controllerObj instanceof ElmultHetController) {
            ElmultHetController controller = (ElmultHetController) controllerObj;
            controller.loadElmultHet(kivalasztottKezeles);
        }
    }

    @FXML
    public void handleEvVizsgalat() {
        loadView(EVVIZSGALAT);
    }
}
