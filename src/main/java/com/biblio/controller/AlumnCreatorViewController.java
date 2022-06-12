package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.model.Book;
import com.biblio.model.Student;
import com.biblio.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AlumnCreatorViewController {

    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField year;
    @FXML private ChoiceBox<Student.House> house;


    @FXML
    private void CreateAndClose(ActionEvent event)
    {
        if(CreateStudent())
            Close(event);
    }

    @FXML
    private void Close(ActionEvent event)
    {
        Main.CloseSecondaryWindow((Stage)(((Node)(event.getSource())).getScene().getWindow()));
    }
    @FXML
    private void CreateAndContinue()
    {
        if(CreateStudent())
        {
            name.setText("");
            surname.setText("");
            house.setValue(null);
            year.setText("");
        }

    }


    public boolean CreateStudent()
    {
        if(!name.getText().isBlank() &&
            !surname.getText().isBlank() &&
            house.getValue() != null &&
            !year.getText().isBlank())
        {
            StudentRepository.Instance().createStudent(
                    name.getText(),
                    surname.getText(),
                    house.getValue(),
                    year.getText()
            );
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Debes ingresar toda la información");
        String message = "";
        if(name.getText().isBlank())
        {
            message = "¿Qué es un alumno sin nombre?\nHasta los muggles tienen nombre.";
        } else if (surname.getText().isBlank()) {
            message = "¿Qué es un alumno sin apellido?\nEn este mundo no eres nadie sin tu familia.";
        } else if (house.getValue() == null) {
            message = "¿Qué es un alumno sin casa?\n¿Cómo sabremos si es de los malos o de los buenos?.";
        } else {
            message = "¿Qué es un alumno sin curso?\n¿Cómo sabremos si tiene edad para leer ese libro de filtros de amor?";

        }


        alert.setContentText(message);
        alert.showAndWait();
        return false;
    }

    //*************************
    //      PUBLIC METHODS
    //*************************

    public void Init()
    {
        house.setItems(FXCollections.observableArrayList(Student.House.values()));
        house.setConverter(new StringConverter<Student.House>() {

            @Override
            public String toString(Student.House house) {
                return house == null ? " " : house.getLabel();
            }

            @Override
            public Student.House fromString(String s) {
                return house.getItems().stream().filter(
                        house -> house.toString().equals(s))
                        .findFirst().orElse(null);
            }

        });
    }

}
