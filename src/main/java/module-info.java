module com.biblio.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    //requires javafx.web;

    opens com.biblio.biblioteca to javafx.fxml;
    opens com.biblio.controller to javafx.fxml;
    opens com.biblio.model to javafx.fxml;

    exports com.biblio.biblioteca;
    exports com.biblio.controller;
    exports com.biblio.model;
    exports com.biblio.dao;
    opens com.biblio.dao to javafx.fxml;
    exports com.biblio.connection;
    opens com.biblio.connection to javafx.fxml;
    exports com.biblio.repository;
    opens com.biblio.repository to javafx.fxml;
}