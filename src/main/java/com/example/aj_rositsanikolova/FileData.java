package com.example.aj_rositsanikolova;

import java.io.File;

public class FileData {
    private String colHeader;
    private String rowData;
    int id;
    FileData(String colHeader, String rowData, int id){
        this.colHeader = colHeader;
        this.rowData = rowData;
        this.id = id;
    }
}
