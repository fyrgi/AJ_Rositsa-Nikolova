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
import javafx.scene.control.Button;
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
        //TODO write the filename
        final Label label = new Label("Reading file <FileName>");
        //TODO try to save a file
        Button saveBtn = new Button("Save");
        saveBtn.idProperty().setValue("saveFile");
        label.setFont(new Font("Arial", 20));
        table.getColumns().clear();
        table.setEditable(true);
        //Display the columns
        for (int column = 0; column < columnNames.size(); column++)
            table.getColumns().add(createColumn(column, columnNames.get(column)));
        int rows = 0;
        // Get the rows of the table to iterate that many times and add the cell value.
        while(rows < FileManager.getRows()) {
            // Add data to table:
            ObservableList<StringProperty> data = FXCollections.observableArrayList();
            for (String value : rowData)
                data.add(new SimpleStringProperty(value));
            table.getItems().add(data);
            rows++;
            //Removes the value that we just added to the table from the list.
            // Potential problem in case we don't have Header. Then columnNames might be faulty
            // most likely have to save data in FileManager.cols.
            if(!rowData.isEmpty())
                if (!columnNames.isEmpty()) {
                    rowData.subList(0, columnNames.size()).clear();
                }
        }
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(label, saveBtn, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    // add values to the cells. First we create the headers of the table and then fill each cell with its value.
    private TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex, String columnName){
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnName == null || columnName.trim().length() == 0)
            title = "Column " + (columnIndex + 1);
        else
            title = columnName;
        column.setText(title);
        // How this row works is a mystery. I understand that withing the declaration it overrides
        column.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>
                , ObservableValue<String>>() {
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

