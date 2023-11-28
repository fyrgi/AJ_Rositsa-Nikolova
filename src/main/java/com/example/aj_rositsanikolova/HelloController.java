package com.example.aj_rositsanikolova;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void onCsvBtnClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        FileManager.readCSVFile();
    }
    @FXML
    protected void onJsonBtnClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        FileManager.readJsonFile();
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