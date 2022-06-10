package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.model.Student;
import com.biblio.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AlumnEditorViewController {

    private Student studentToEdit;
    private AlumnTabController alumnTabController;
    @FXML
    private TextField name;
    @FXML private TextField surname;
    @FXML private TextField year;
    @FXML private ChoiceBox<Student.House> house;


    @FXML
    private void EditeAndClose(ActionEvent event)
    {
        UpdateStudent();
        alumnTabController.RefreshTable();
        Close(event);
    }

    @FXML
    private void Close(ActionEvent event)
    {
        Main.CloseSecondaryWindow((Stage)(((Node)(event.getSource())).getScene().getWindow()));
    }
    @FXML
    private void EmptyInputs()
    {
        name.setText("");
        surname.setText("");
        house.setValue(null);
        year.setText("");
    }


    public void UpdateStudent()
    {
        studentToEdit.setHouse(house.getValue());
        studentToEdit.setSurname(surname.getText());
        studentToEdit.setName(name.getText());
        studentToEdit.setYear(year.getText());
        StudentRepository.Instance().updateStudent(studentToEdit);
    }

    //*************************
    //      PUBLIC METHODS
    //*************************

    public void Init(Student studentToEdit, AlumnTabController alumnTabController)
    {
        this.studentToEdit = studentToEdit;
        this.alumnTabController = alumnTabController;

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

        name.setText(studentToEdit.getName());
        surname.setText(studentToEdit.getSurname());
        year.setText(studentToEdit.getYear());
        house.setValue(studentToEdit.getHouse());
    }

}
