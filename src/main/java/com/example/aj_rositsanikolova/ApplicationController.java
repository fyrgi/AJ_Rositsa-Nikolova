package com.example.aj_rositsanikolova;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;

public class ApplicationController {
    private Table table = new Table();
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onCsvBtnClick() {
        welcomeText.setText("Reading CSV file");
        FileManager.readCSVFile();
        table.start(new Stage());
    }
    @FXML
    protected void onJsonBtnClick() {
        welcomeText.setText("Reading JSON file");
        FileManager.readJsonFile();
        table.start(new Stage());
    }
    @FXML
    protected void onXmlBtnClick() {
        //FileManager.onFileChosen();
    }
    @FXML
    protected void onFileChosen() {
        FileManager.onFileChosen();
    }
}