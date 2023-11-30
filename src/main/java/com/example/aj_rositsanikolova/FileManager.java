package com.example.aj_rositsanikolova;

import com.eclipsesource.json.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//import static com.example.aj_rositsanikolova.HelloApplication.scene;


public class FileManager {
    private static ArrayList<String> allFileValues = new ArrayList<>(), columnFileValues= new ArrayList<>(),dataFileValues = new ArrayList<>() ;
    private static Scanner scanner;
    private static int rows = 0;
    private static File url;
    public static void readFile(){
        String fileExtension = findExtension();
        switch (fileExtension){
            case "csv":
                readCSVFile();
                break;
            case "json":
                readJsonFile();
                break;
            default:
                System.out.println("Can not read file");
                break;
        }
    }
    public static void readCSVFile() {
        allFileValues.clear();
        columnFileValues.clear();
        dataFileValues.clear();
        rows=0;
        try {
            File file;
            if(url != null){
                file = new File(String.valueOf(url));
            } else {
                file = new File("src/Files/sample.csv");
            }
            scanner = new Scanner(file);
            int cols = 0, commas = 0;
            boolean foundFirst = false;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                rows++;
                if(!foundFirst){
                    for(int i = 0; i < line.length(); i++){
                        if(line.charAt(i)==',')
                            commas++;
                    }
                    rows--;
                    cols = commas + 1;
                    foundFirst = true;
                }
                if(line.split(",").length > cols){
                    System.err.println("Error in data "+ Arrays.toString(line.split(",", cols)) + " on row: "+ rows);
                }
                String[] array = line.split(",", cols);
                allFileValues.addAll(Arrays.asList(array));
            }
            scanner.close();
            dataFileValues.addAll(allFileValues);
            for(int i = 0; i < cols; i++){
                columnFileValues.add(allFileValues.get(i));
                dataFileValues.remove(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }
/*
    public static void readJsonFileManuell(){
        try {

            // Trqbva da se napravi edin manuell parsing.
            // kogato chetem pyrvata liniq moje da vzemem headera.
            //zatova moje da napravim exceltion gore  za pyrviq zapis. Za da namerim
            // za JSON e }, unikalno zashtoto zatvarq zapis. Izpolzva se za da razdelim zapisite.
            // split kydeto namerim },
            // Pyrviqt index index[0] ima header i values.
            // vsemi headera i values i v posledstvie samo vlues.
            // kato sme vzeli Header i values shte razdelqme po zapetaika.

            File file = new File("src/Files/sample.json");
            scanner = new Scanner(file);
            String page = "";
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                page += line;
            }
            scanner.close();
            page.split("}");
            String[] array = page.split("}");

            //System.out.println(page);
            //System.out.println(Arrays.deepToString(page.split("}")));
            for(String s : page.split("}")){
                System.out.println(s);
            }
            for(int i = 0; i < array.length; i++){
            }
            System.out.println(array[0]);

        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    } */
    public static void readJsonFile() {
        columnFileValues.clear();
        dataFileValues.clear();
        rows = 0;
        allFileValues.clear();
        try {
            File file;
            if(url != null){
                file = new File(String.valueOf(url));
            } else {
                file = new File("src/Files/sample.json");
            }
            scanner = new Scanner(file);
            String page = "";
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                page += line;
                // System.out.println(line.length());
            }
            scanner.close();
            JsonValue jv = Json.parse(page);
            JsonArray ja = jv.asArray();
            JsonObject jo = ja.get(0).asObject();
            columnFileValues.addAll(jo.names());
            for (int i = 0; i < ja.size(); i++) {
                JsonObject record = ja.get(i).asObject();
                rows++;
                for(int j = 0; j < columnFileValues.size(); j++){
                    dataFileValues.add(String.valueOf(record.get(columnFileValues.get(j))));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }

    public static void readXmlFile(){
        //TODO read XML?
    }
    public static void onFileChosen(){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("src")); //src znar kyde se namira / e za C
        fc.setTitle("Open file");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            url = selectedFile;
            readFile();
            Table table = new Table();
            table.start(new Stage());
            url = null;
        }
    }
    public void saveFile(File url){
        String ex = findExtension();
        FileChooser fc = new FileChooser();
        //File selectedFile = fc.showSaveDialog();
    }

    public static String findExtension(){
        String[] dots = url.getName().split("\\.");
        return url.getName().split("\\.")[dots.length-1];
    }

    public static ArrayList<String> getAllFileValues() {
        return allFileValues;
    }

    public static ArrayList<String> getColumnFileValues() {
        return columnFileValues;
    }

    public static ArrayList<String> getDataFileValues() {
        return dataFileValues;
    }

    public static int getRows() {
        return rows;
    }
}
