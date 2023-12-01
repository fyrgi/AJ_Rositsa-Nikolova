package com.example.aj_rositsanikolova;

import com.eclipsesource.json.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class FileManager {
    private static ArrayList<Integer> flaggedValues = new ArrayList<>();
    private static ArrayList<String> columnFileValues= new ArrayList<>(), dataFileValues = new ArrayList<>() ;
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
            case "xml":
                readXmlFile();
                break;
            default:
                //I wanted to change the label, but it is not as trivial as it may look.
                // I tried several approaches both here in this class and in the ApplicationController all of them not working
                // and throwing a Cannot invoke "javafx.scene.control.Label.setText(String)" exception
                System.out.println("Cannot read file");
                break;
        }
    }
    //All readTypeFile have the same logic. Find the columns from the first record, store them in an ArrayList of Strings
    // then find all values corresponding to the column and store them in an ArrayList of Strings.
    // The 2 array lists are used to read through the file in Table.java where every X amount of records corresponding
    // to the amount of columns is a row in the table.
    public static void readCSVFile() {
        doClear();
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
                    //System.err.println("Error in data "+ Arrays.toString(line.split(",", cols)) + " on row: "+ rows);
                    flaggedValues.add(rows);
                }
                String[] array = line.split(",", cols);
                dataFileValues.addAll(Arrays.asList(array));
            }
            scanner.close();
            for(int i = 0; i < cols; i++){
                columnFileValues.add(dataFileValues.get(0));
                dataFileValues.remove(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }

    public static void readJsonFile() {
        doClear();
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
                page += line;
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
                    int lenght = (record.get(columnFileValues.get(j)).toString().length());
                    if((record.get(columnFileValues.get(j)).toString().charAt(0) == '"' && (record.get(columnFileValues.get(j)).toString().charAt(lenght-1) == '"'))){
                        dataFileValues.add(((record.get(columnFileValues.get(j)).toString().substring(1, lenght - 1))));
                    } else {
                        dataFileValues.add(String.valueOf(record.get(columnFileValues.get(j))));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }

    public static void readXmlFile(){
        doClear();
        File file;
        if(url != null){
            file = new File(String.valueOf(url));
        } else {
            file = new File("src/Files/sample.xml");
        }
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();
            String nodeName = "";
            NodeList list = root.getChildNodes();
            // A way to get to the tagNames for the first record.
            // This might not work if the first record is not found on the same row as with sample.xml
            for(int j = 1; j < list.item(1).getChildNodes().getLength(); j+=2){
                Node nodeArr = list.item(1).getChildNodes().item(j);
                columnFileValues.add(nodeArr.getNodeName());
            }

            // find the node name manually and store it into a String. Brake from the loop the moment I have the name
            for (int i = 1; i < list.getLength(); i+=2) {
                Node node = list.item(i);
                String[] findExtension = node.getNodeName().split(":");
                nodeName = findExtension[0];
                if (!nodeName.isEmpty()) {
                    break;
                }
            }
            // now knowing the name of the Node, identical for all the records I create a new list where I will be finding the values
            // using the column names as a TagName later to extract the text.
            NodeList nList = doc.getElementsByTagName(nodeName);
            for(int i = 0; i < nList.getLength(); i++){
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    for(int j = 0; j < columnFileValues.size(); j++){
                        //System.out.println("Column "+columnFileValues.get(j)+" Value "+ eElement.getElementsByTagName(columnFileValues.get(j)).item(0).getTextContent());
                        dataFileValues.add(eElement.getElementsByTagName(columnFileValues.get(j)).item(0).getTextContent());
                    }
                }
                rows++;
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }

    public static void onFileChosen(){
        try{
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
            //Creates a table even for files that are not CSV, JSON or XML. TODO can change it by removing the all files option :)
            Table table = new Table();
            table.start(new Stage());
            url = null;
        }
        } catch (Exception e){
            System.out.println(e.getMessage());
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

    public static String getUrlName() {
        return url.getName();
    }

    private static void doClear(){
        columnFileValues.clear();
        dataFileValues.clear();
        flaggedValues.clear();
        rows = 0;
    }

    public static ArrayList<Integer> getFlaggedValues() {
        return flaggedValues;
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
