package com.example.aj_rositsanikolova;

import com.eclipsesource.json.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import org.w3c.dom.Document;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//import static com.example.aj_rositsanikolova.HelloApplication.scene;


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
            default:
                System.out.println("Can not read file");
                break;
        }
    }
    public static void readCSVFile() {
        flaggedValues.clear();
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
                    //System.err.println("Error in data "+ Arrays.toString(line.split(",", cols)) + " on row: "+ rows);
                    flaggedValues.add(rows);
                }
                String[] array = line.split(",", cols);
                dataFileValues.addAll(Arrays.asList(array));
            }
            scanner.close();
            for(int i = 0; i < cols; i++){
                columnFileValues.add(dataFileValues.get(i));
                dataFileValues.remove(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }

    public static void readJsonFile() {
        columnFileValues.clear();
        dataFileValues.clear();
        rows = 0;
        flaggedValues.clear();
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
                    dataFileValues.add(String.valueOf(record.get(columnFileValues.get(j))));
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }

    public static void readXmlFile(){
        columnFileValues.clear();
        dataFileValues.clear();
        rows=0;
        File file;
        if(url != null){
            file = new File(String.valueOf(url));
        } else {
            file = new File("src/Files/sample.xml");
        }

        //TODO read XML?

        // source https://stackoverflow.com/questions/61948901/java-get-tag-name-of-a-node
        // special credits to https://youtu.be/2JH5YeQ68H8
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();

            NodeList list = root.getChildNodes();
            // Crazy way to get to the tagNames for the first record.
            for(int j = 1; j < list.item(1).getChildNodes().getLength(); j+=2){
                Node nodeArr = list.item(1).getChildNodes().item(j);
                columnFileValues.add(nodeArr.getNodeName());
            }
            for (int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);
                String[] findExtension = node.getNodeName().split(":");
                // find the node name manual.
                String nodeName = findExtension[0];
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    for(int j = 0; j < columnFileValues.size(); j++){
                        Element eElement = (Element) node;
                        System.out.println("For "+columnFileValues.get(j)+ " we have "+ eElement.getAttribute(columnFileValues.get(j)));
                    }
                }
            }

           /* for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    for(int j = 1; j < list.item(1).getChildNodes().getLength(); j+=2){
                        Node nodeArr = list.item(1).getChildNodes().item(j);
                        columnFileValues.add(nodeArr.getNodeName());
                    }
                    String str = node.getTextContent();
                    for(int j = 0; j < str.length(); j++){
                        System.out.println("Char at "+j+" is "+ str.charAt(j));
                    }
                    dataFileValues.add(str);
                }
            }
            for(String row : dataFileValues){
            }*/

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

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
