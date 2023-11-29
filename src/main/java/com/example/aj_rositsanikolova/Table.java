package com.example.aj_rositsanikolova;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

public class Table{

    private TableView<ObservableList<StringProperty>> table = new TableView();

    private ArrayList<String> columnNames = FileManager.getColumnFileValues();
    private ArrayList<String> rowData = FileManager.getDataFileValues();

    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Read file");
        //TODO write the filename?
        final Label label = new Label("File Name");
        label.setFont(new Font("Arial", 20));
        table.setEditable(true);
        for (int column = 0; column < columnNames.size(); column++){
            table.getColumns().add(createColumn(column, columnNames.get(column)));}
        int rows = 0;
        while(rows < FileManager.getRows()) {
            // Add data to table:
            ObservableList<StringProperty> data = FXCollections.observableArrayList();
            for (String value : rowData)
                data.add(new SimpleStringProperty(value));
            table.getItems().add(data);
            rows++;
            if(!rowData.isEmpty()) {
                for (int i = 0; i < columnNames.size(); i++) {
                    rowData.remove(0);
                }
            }
        }
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(label, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    // add values to the cells. First we create the headers of the table and then fill each cell with its value.
    private TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex, String columnTitle){
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0)
            title = "Column " + (columnIndex + 1);
        else
            title = columnTitle;
        column.setText(title);
        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures){
                ObservableList<StringProperty> values = cellDataFeatures.getValue();
                if (columnIndex >= values.size())
                    return new SimpleStringProperty("");
                else
                    return cellDataFeatures.getValue().get(columnIndex);
            }
        });
        return column;
    }
}

