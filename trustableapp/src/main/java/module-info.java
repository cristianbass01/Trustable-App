module com.trustapp.trustableapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires java.datatransfer;
    requires java.desktop;
    requires org.controlsfx.controls;


    opens com.trustapp.trustableapp to javafx.fxml;
    exports com.trustapp.trustableapp;
    exports com.trustapp.trustableapp.Controller;
    opens com.trustapp.trustableapp.Controller to javafx.fxml;
    exports com.trustapp.trustableapp.DataLists;
    opens com.trustapp.trustableapp.DataLists to javafx.fxml;
    exports com.trustapp.trustableapp.DataClass;
    opens com.trustapp.trustableapp.DataClass to javafx.fxml;
    exports com.trustapp.trustableapp.Error;
    opens com.trustapp.trustableapp.Error to javafx.fxml;
    exports com.trustapp.trustableapp.API;
    opens com.trustapp.trustableapp.API to javafx.fxml;
}