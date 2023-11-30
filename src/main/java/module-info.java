module com.example.aj_rositsanikolova {
    requires javafx.controls;
    requires javafx.fxml;
    requires minimal.json;
    requires org.jetbrains.annotations;


    opens com.example.aj_rositsanikolova to javafx.fxml;
    exports com.example.aj_rositsanikolova;
}