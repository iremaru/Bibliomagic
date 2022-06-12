package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.model.Student;
import com.biblio.model.StudentFinder;
import com.biblio.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.List;

public class AlumnTabController {

    private StudentFinder studentFinder = new StudentFinder();
    @FXML private ChoiceBox<Student.House> houseTF;
    @FXML private TextField surnameTF;
    @FXML private TextField nameTF;
    @FXML private TextField yearTF;

    //STUDENT TABLE:
    @FXML private TableView<Student> studentsTable;
    @FXML private TableColumn<Student, String> studentNameCol;
    @FXML private TableColumn<Student, String> studentSurnameCol;
    @FXML private TableColumn<Student, String> studentHouseCol;
    @FXML private TableColumn<Student, String> studentGradeCol;

    private BibliotecaController mainController;
    private StudentRepository studentRepository = new StudentRepository();
    //*************************
    //      PUBLIC METHODS
    //*************************

    public void Init()
    {

        houseTF.setItems(FXCollections.observableArrayList(Student.House.values()));
        houseTF.setConverter(new StringConverter<Student.House>() {
            @Override
            public String toString(Student.House house) {
                return house == null ? " " : house.getLabel();
            }

            @Override
            public Student.House fromString(String s) {
                return houseTF.getItems().stream().filter(
                                house -> house.toString().equals(s))
                        .findFirst().orElse(null);
            }
        });

        InitializeStudentTableView();
        SetStudentsInTableView(StudentRepository.Instance().GetStudentList());
        studentsTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if(newSelection != null)
                {
                    mainController.SetLastStudentSelected(newSelection);
                }
            }
        );
    }

    public void InjectMainController(BibliotecaController mainController)
    {
        this.mainController = mainController;
    }

    public void RefreshTable()
    {
        SetStudentsInTableView(StudentRepository.Instance().GetStudentList());
        studentsTable.refresh();
        mainController.RefreshData();
    }

    //*************************
    //      PRIVATE METHODS
    //*************************

    @FXML
    private void SearchStudents()
    {
        Student.House house = houseTF.getValue();
        if(house != null)
        {
            SetStudentsInTableView(
                    studentFinder.Search(
                            nameTF.getText(),
                            surnameTF.getText(),
                            house,
                            yearTF.getText()
                    )
            );
        } else {
            List<Student.House> houses = List.of(Student.House.values());
            SetStudentsInTableView(
                    studentFinder.Search(
                            nameTF.getText(),
                            surnameTF.getText(),
                            houses,
                            yearTF.getText()
                    )
            );
        }

    }

    private void SetStudentsInTableView(List<Student> students)
    {
        ObservableList<Student> observableList = FXCollections.observableArrayList(students);
        studentsTable.setItems(observableList);
    }

    @FXML
    private void InitializeStudentTableView()
    {
        studentGradeCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentSurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        studentHouseCol.setCellValueFactory(new PropertyValueFactory<>("house"));

        SearchStudents();
    }

    @FXML
    private void RegisterNewStudent()
    {
        Main.OpenStudentCreatorWindow();
    }

    @FXML
    private void UpdateStudent()
    {
        Student student = GetStudentInTable("transformar");
        if(student != null)
        {
            Main.OpenStudentEditorWindow((Student)(studentsTable.getSelectionModel().getSelectedItem()), this);
        }
    }
    @FXML
    private void DeleteStudent()
    {
        Student student = GetStudentInTable("eliminar");
        if(student != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Atención");
            alert.setContentText("¿Está seguro de que quiere eliminar a este pobre estudiante? \n (¿Tan mal se ha portado?)");
            if(alert.showAndWait().get() == ButtonType.OK)
            {
                StudentRepository.Instance().deleteStudent(student);
                RefreshTable();
            }

        }
    }

    private Student GetStudentInTable(String action)
    {
        Student student = (Student)(studentsTable.getSelectionModel().getSelectedItem());

        if( student == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("No has seleccionado ningún alumno");
            alert.setContentText("Seleccione un alumno en la tabla antes de intentar "+action+"lo.");
            alert.showAndWait();
            return null;
        }

        return student;
    }
}
