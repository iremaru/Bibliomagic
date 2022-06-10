package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.model.*;
import com.biblio.repository.BookRepository;
import com.biblio.repository.BorrowRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowCreatorViewController {

    private Book borrowedBook;
    private Student student;
    private BookFinder bookFinder = new BookFinder();
    private StudentFinder studentFinder = new StudentFinder();
    private BibliotecaController bibliotecaController;

    @FXML private TextField studentName;
    @FXML private TextField studentSurname;
    @FXML private ChoiceBox<Student.House> studentHouse;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> studentNameCol;
    @FXML private TableColumn<Student, String> studentSurnameCol;
    @FXML private TableColumn<Student, String> studentHouseCol;
    @FXML private TableColumn<Student, String> studentYearCol;

    @FXML private TextField bookTitle;
    @FXML private TextField bookAuthor;
    @FXML private ChoiceBox<Book.Subject> bookSubject;

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> bookTitleCol;
    @FXML private TableColumn<Book, String> bookAuthorCol;
    @FXML private TableColumn<Book, String> bookEditorialCol;
    @FXML private TableColumn<Book, String> bookSubjectCol;

    @FXML private Label borrowStudent;
    @FXML private Label borrowStudentHouse;
    @FXML private Label borrowStudentYear;
    @FXML private Label borrowTitle;
    @FXML private Label borrowAuthor;
    @FXML private Label borrowEditorial;



    public void Init(Book book, BibliotecaController bibliotecaController)
    {
        this.bibliotecaController = bibliotecaController;
        student = bibliotecaController.GetLastStudentSelected();

        InitializeChoiceBoxs();
        InitializeTables();

        if(student != null)
        {
            SetBorrowingStudent(student);
            studentName.setText(student.getName());
            studentSurname.setText(student.getSurname());
            studentHouse.setValue(student.getHouse());
        } else {
            borrowStudent.setText(" ");
            borrowStudentHouse.setText(" ");
            borrowStudentHouse.setText(" ");
        }

        if(book != null && book.getAvailable())
        {
            SetBorrowBook(book);
            bookAuthor.setText(book.getAuthor());
            bookTitle.setText(book.getTitle());
            bookSubject.setValue(book.getSubject());
        } else {
            borrowTitle.setText(" ");
            borrowEditorial.setText(" ");
            borrowAuthor.setText(" ");
        }
    }


    //*************************
    //      PRIVATE METHODS
    //*************************

    @FXML
    private void SearchStudent()
    {
        SetStudentsInTable(
            studentFinder.Search(
                    studentName.getText(),
                    studentSurname.getText(),
                    studentHouse.getValue(),
                    " "
            ));
    }

    @FXML
    private void SearchBook()
    {
        bookFinder.Search(bookTitle.getText(),
                "",
                bookAuthor.getText(),
                true,
                true,
                bookSubject.getValue(),
                true);

        SetBooksInTable(bookFinder.GetResult());
    }

    @FXML
    private void BorrowBook()
    {
        borrowedBook.setAvailable(false);
        BookRepository.Instance().Update(borrowedBook);
        BorrowRepository.Instance().CreateBorrow(
                borrowedBook.getCode(),
                student.getCode());

        SearchBook(); //In order to vanish the borrowedBook from the table
        bibliotecaController.RefreshData();
    }

    @FXML
    private void CloseWindow(ActionEvent event)
    {
        Main.CloseSecondaryWindow((Stage)(((Node)(event.getSource())).getScene().getWindow()));
    }

    private void InitializeTables()
    {
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentSurnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        studentHouseCol.setCellValueFactory(new PropertyValueFactory<>("house"));
        studentYearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        studentTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSelection, newSelection) -> {
                if(newSelection != null)
                {
                    SetBorrowingStudent(newSelection);
                }
            }
        );

        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookEditorialCol.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        bookSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        bookTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if(newSelection != null)
                    {
                        SetBorrowBook(newSelection);
                    }
                }
        );
    }

    private void InitializeChoiceBoxs()
    {
        studentHouse.setItems(FXCollections.observableArrayList(Student.House.values()));
        studentHouse.setConverter(new StringConverter<Student.House>() {
            @Override
            public String toString(Student.House house) {
                return house == null ? " " : house.getLabel();
            }

            @Override
            public Student.House fromString(String s) {
                return studentHouse.getItems().stream().filter(
                                house -> house.toString().equals(s))
                        .findFirst().orElse(null);
            }
        });


        bookSubject.setItems(FXCollections.observableArrayList(Book.Subject.values()));
        bookSubject.setConverter(new StringConverter<Book.Subject>() {
            @Override
            public String toString(Book.Subject subject) {
                return subject == null ? null : subject.ToSpanish();
            }

            @Override
            public Book.Subject fromString(String s) {
                return bookSubject.getItems().stream().filter(
                                subjec -> subjec.CompareSpanishSubject(s))
                        .findFirst().orElse(null);
            }
        });
    }

    private void SetBooksInTable(Book[] books)
    {
        ObservableList<Book> observableList = FXCollections.observableArrayList(books);
        bookTable.setItems(observableList);
    }

    private void SetStudentsInTable(List<Student> students)
    {
        ObservableList<Student> observableList = FXCollections.observableArrayList(students);
        studentTable.setItems(observableList);
    }

    private void SetBorrowBook(Book book)
    {
        borrowedBook = book;
        borrowTitle.setText(book.getTitle());
        borrowAuthor.setText(book.getAuthor());
        borrowEditorial.setText(book.getEditorial());
    }

    private void SetBorrowingStudent(Student student)
    {
        this.student = student;
        borrowStudent.setText(student.getName() + ' ' + student.getSurname());
        borrowStudentHouse.setText(student.getHouseString());
        borrowStudentYear.setText(student.getYear());
    }

}
