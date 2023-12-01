package com.example.aj_rositsanikolova;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

public class ApplicationController {

    @FXML
    private Label informationText;

    @FXML
    protected void onCsvBtnClick() {
        informationText.setText("Reading CSV file");
        FileManager.readCSVFile();
        createResultTable();
    }
    @FXML
    protected void onJsonBtnClick() {
        informationText.setText("Reading JSON file");
        FileManager.readJsonFile();
        createResultTable();
    }
    @FXML
    protected void onXmlBtnClick() {
        informationText.setText("Reading XML file");
        FileManager.readXmlFile();
        createResultTable();
    }
    @FXML
    protected void onFileChosen() {
        informationText.setText("Reading file");
        FileManager.onFileChosen();
    }
    public void createResultTable(){
        Table table = new Table();
        table.start(new Stage());
    }
    @FXML
    public void writeErrorOnFileLoad() {

    }
    public void setInformationText(String message) {
        informationText.setText(message);
        informationText.setBackground(Background.fill(Color.RED));
    }

}