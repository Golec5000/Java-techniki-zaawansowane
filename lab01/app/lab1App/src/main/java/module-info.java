module org.example.lab1app {
    requires javafx.controls;
    requires javafx.fxml;
    requires zipFileMaker;
    requires controlSumCheck;

    exports org.example.lab1app.app;
    exports org.example.lab1app.controller;

    opens org.example.lab1app.app;
    opens org.example.lab1app.controller;
}