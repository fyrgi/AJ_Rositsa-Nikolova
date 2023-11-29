package com.example.aj_rositsanikolova;

import com.eclipsesource.json.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//import static com.example.aj_rositsanikolova.HelloApplication.scene;


public class FileManager {

    private static ArrayList<String> allFileValues = new ArrayList<>();
    private static ArrayList<String> columnFileValues = new ArrayList<>();
    private static ArrayList<String> dataFileValues = new ArrayList<>();
    private static Scanner scanner;

    private static int rows = 0;
    private static File url = new File("src/Files/sample.csv");
    private int totalCol, totalRow;
    public static void readFile(File url){
        String ending = "";
        if(ending.equals("csv"))
            readCSVFile();
        else if (ending.equals("json"))
            readJsonFile();
        else if (ending.equals("xml"))
            readXmlFile();
    }
    public static void readCSVFile() {
        allFileValues.clear();
        columnFileValues.clear();
        dataFileValues.clear();
        try {

            File file = new File("src/Files/sample.csv");

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
                    cols = commas + 1;
                    foundFirst = true;
                }
                String[] array = line.split(",", cols);
                allFileValues.addAll(Arrays.asList(array));
                /*System.out.println("1: "+array[0]);
                System.out.println("2: "+Arrays.deepToString(array));
                System.out.println("3: "+line);*/
            }
            scanner.close();

            dataFileValues.addAll(allFileValues);
            for(int i = 0; i < cols; i++){
                columnFileValues.add(allFileValues.get(i));
                dataFileValues.remove(0);
            }
            //System.out.println("all file values "+ allFileValues);
            //System.out.println("dataFileValues "+ dataFileValues);
            //System.out.println("Column values " + columnFileValues);
            /*for (String s : allFileValues) {
                System.out.println("5: "+s);
            }*/

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
        allFileValues.clear();
        columnFileValues.clear();
        dataFileValues.clear();
        rows = 0;

        try {
            File file = new File("src/Files/sample.json");
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

            for (int i = 0; i < ja.size() - 1; i++) {
                JsonObject record = ja.get(i).asObject();
                rows++;
                for(int j = 0; j < columnFileValues.size(); j++){
                    dataFileValues.add(String.valueOf(record.get(columnFileValues.get(j))));
                    System.out.println(record.get(columnFileValues.get(j)));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }
            /*while (scanner.hasNext()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                page += line;
            }
            scanner.close();
            JsonValue jv = Json.parse(page);
            JsonArray ja = jv.asArray();
            JsonObject jo = ja.get(0).asObject();
            // kolko koloni imame
            System.out.println(jo.names().size());
            for(int i = 0; i <ja.size()-1; i++){
                JsonObject j = ja.get(i).asObject();
                System.out.println(j.get("Item"));
                System.out.println(j.get("Amount per unit"));
                System.out.println(j.get("Total amount"));
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }*/

    public static void readXmlFile(){

    }
    public static void onFileChosen(){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("src")); //src znar kyde se namira / e za C
        fc.setTitle("Open file");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("XML", "*.xml"),
                new FileChooser.ExtensionFilter("ALL FILES", "*.*")
        );
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            url = selectedFile;
        } else {
        }
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
