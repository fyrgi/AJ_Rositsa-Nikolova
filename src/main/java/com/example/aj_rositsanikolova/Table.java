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

public class Table extends Application {

        private TableView<ObservableList<StringProperty>> table = new TableView();
        public static void main(String[] args) {
            launch(args);
        }
        private ArrayList<String> columnNames = FileManager.getColumnFileValues();
        private ArrayList<String> rowData = FileManager.getDataFileValues();
        @Override
        public void start(Stage stage) {
            Scene scene = new Scene(new Group());
            stage.setTitle("Table View Sample");
            //stage.setWidth(300);
            //stage.setHeight(500);

            final Label label = new Label("Address Book");
            label.setFont(new Font("Arial", 20));

            table.setEditable(true);

            for (int column = 0; column < columnNames.size(); column++) {
                table.getColumns().add(
                        createColumn(column, columnNames.get(column)));
            }

            int rows = 0;
            while(rows < FileManager.getRows()-1) {

                // Add data to table:
                ObservableList<StringProperty> data = FXCollections
                        .observableArrayList();
                for (String value : rowData) {
                    data.add(new SimpleStringProperty(value));
                }
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
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table);

            ((Group) scene.getRoot()).getChildren().addAll(vbox);

            stage.setScene(scene);
            stage.show();
        }
    private TableColumn<ObservableList<StringProperty>, String> createColumn(
            final int columnIndex, String columnTitle) {
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0) {
            title = "Column " + (columnIndex + 1);
        } else {
            title = columnTitle;
        }
        column.setText(title);
        column
                .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
                        ObservableList<StringProperty> values = cellDataFeatures.getValue();
                        if (columnIndex >= values.size()) {
                            return new SimpleStringProperty("");
                        } else {
                            return cellDataFeatures.getValue().get(columnIndex);
                        }
                    }
                });
        return column;
    }

}

