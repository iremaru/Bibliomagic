module com.biblio.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;
    requires java.persistence;
    requires java.naming;

    requires org.apache.commons.codec;

    requires org.hibernate.orm.core;

    opens com.biblio.biblioteca to javafx.fxml;
    opens com.biblio.controller to javafx.fxml;
    opens com.biblio.model to javafx.fxml;
    opens com.biblio.dao to javafx.fxml;
    opens com.biblio.connection to javafx.fxml;
    opens com.biblio.repository to javafx.fxml;
    opens com.biblio.service to javafx.fxml;
    opens com.biblio.entity to javafx.fxml,
            java.persistence,
            java.naming,
            java.sql,
            org.hibernate.orm.core;

    exports com.biblio.biblioteca;
    exports com.biblio.controller;
    exports com.biblio.model;
    exports com.biblio.dao;
    exports com.biblio.connection;
    exports com.biblio.repository;
    exports com.biblio.service;
    exports com.biblio.entity;
}