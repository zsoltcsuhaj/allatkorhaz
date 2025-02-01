package com.example.allatkorhaz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MenuBarController implements IViews{
    @FXML
    private StackPane contentPane;


    @FXML
    public void initialize() {
        handleAllatok();
    }
    public void loadView(String fxmlfile){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
            StackPane newView = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(newView);
        } catch (IOException e){
            System.err.println("Error loading FXML file: " + fxmlfile);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUjAllat(){
        loadView(UJALLAT);
    }
    @FXML
    public void handleLegjobbAllatorvos(){
        loadView(LEGJOBBALLATORVOS);
    }
    @FXML
    public void handleKontrollVizsgalat(){
        loadView(KONTROLLVIZSGALAT);
    }
    @FXML
    public void handleKezelesTipusai(){
        loadView(KEZELESTIPUSAI);
    }
    @FXML
    public void handleKezelesHozzaadasa(){
        loadView(KEZELESHOZZAADASA);
    }
    @FXML
    public void handleAllatok(){
        loadView(ALLATOK);
    }



}
