package com.example.aj_rositsanikolova;
import com.eclipsesource.json.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//import static com.example.aj_rositsanikolova.HelloApplication.scene;
import static com.example.aj_rositsanikolova.HelloApplication.stage;

public class FileManager {


    public static ArrayList<String> aryL = new ArrayList<>();
    public static Scanner scanner;
    private int totalCol, totalRow;
    public static void readCSVFile() {
        String[][] array2d = new String[3][11];
        try {
            File file = new File("src/Files/sample.csv");
            scanner = new Scanner(file);
            int rows = 0, cols = 0, commas = 0;
            boolean foundFirst = false;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if(!foundFirst){
                    for(int i = 0; i < line.length(); i++){
                        if(line.charAt(i)==',')
                            commas++;
                    }
                    cols = commas + 1;
                    foundFirst = true;
                }
                String[] array = line.split(",", cols);
                aryL.addAll(Arrays.asList(array));
                System.out.println("1: "+array[0]);
                System.out.println("2: "+Arrays.deepToString(array));
                System.out.println("3: "+line);
            }
            System.out.println("4: "+aryL);

            for (String s : aryL) {
                System.out.println("5: "+s);
            }
            scanner.close();
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
        try {
            File file = new File("src/Files/sample.csv");
            scanner = new Scanner(file);
            String page = "";
            while (scanner.hasNext()) {
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
                //System.out.println(j.get(i));
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }

    public static void onFileChosen(){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("src")); //src znar kyde se namira / e za C
        fc.setTitle("Open file");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        //File file = fc.showOpenMultipleDialog(scene.getWindow());
        //hela filen is returned
        //File selectedFile = fc.showOpenDialog(stage.getStage().getWindow());
        //if (selectedFile != null) {
        //    System.out.println("file");
        //}
    }
}
