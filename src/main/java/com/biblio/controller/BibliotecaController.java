package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.model.Book;
import com.biblio.model.BookFinder;
import com.biblio.model.Borrow;
import com.biblio.model.Student;
import com.biblio.repository.BookRepository;
import com.biblio.repository.BorrowRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class BibliotecaController extends Application {

    //*************************
    //      TABS RELATED
    //*************************

    @FXML private AlumnTabController alumnTabController;

    private Student lastSelectedStudent;
    private Book lastSelectedBook;

    //*************************
    //      BOOK TAB PROPERTIES
    //*************************
    private BookFinder bookFinder = new BookFinder();
    @FXML
    private TextField bookNameTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField editorialTextField;
    @FXML
    private CheckBox coreSubjectCheckBox;
    @FXML
    private CheckBox selectiveSubjectCheckBox;
    @FXML
    private CheckBox extraSubjectCheckBox;
    @FXML
    private CheckBox newStateCheckBox;
    @FXML
    private CheckBox usedStateCheckBox;

    @FXML private TableView bookTable;
    @FXML private TableColumn<Book, String> bookCoverCol;
    @FXML private TableColumn<Book, String> bookTitleCol;
    @FXML private TableColumn<Book, String> bookAuthorCol;
    @FXML private TableColumn<Book, String> bookEditorialCol;
    @FXML private TableColumn<Book, String> bookSubjectCol;
    @FXML private TableColumn<Book, String> bookStateCol;

    //*************************
    //      BORROW TAB PROPERTIES
    //*************************

    @FXML private TableView borrowTable;
    @FXML private TableColumn<Borrow, String> borrowBookCol;
    @FXML private TableColumn<Borrow, String> borrowStudentCol;
    @FXML private TableColumn<Borrow, String> borrowStateCol;
    @FXML private TableColumn<Borrow, String> borrowStartDateCol;
    @FXML private TableColumn<Borrow, String> borrowEndDateCol;

    @FXML
    private void CloseApp()
    {
        Main.CloseApp();
    }

    @FXML
    private void GoToIcon8Web(ActionEvent event)
    {
        //getHostServices().showDocument("https://icons8.com");
        Main.GoToWeb("https://icons8.com");
    }


    //*************************
    //      BOOK METHODS
    //*************************
    @FXML
    private void OpenRegisterBookWindow()
    {
        Main.OpenBookCreatorWindow();
    }

    @FXML
    private void SearchBook()
    {
        bookFinder.Search(bookNameTextField.getText(),
                editorialTextField.getText(),
                authorTextField.getText(),
                newStateCheckBox.isSelected(),
                usedStateCheckBox.isSelected(),
                coreSubjectCheckBox.isSelected(),
                selectiveSubjectCheckBox.isSelected(),
                extraSubjectCheckBox.isSelected());

        SetBooksInTableView(bookFinder.GetResult());
    }

    private void SetBooksInTableView(Book[] books)
    {
        ObservableList<Book> observableList = FXCollections.observableArrayList(books);
        bookTable.setItems(observableList);
    }

    private void AddBookToTableView(Book book)
    {
        bookTable.getItems().add(book);
    }

    private Book GetBookInTable(String action)
    {
        Book book = (Book)(bookTable.getSelectionModel().getSelectedItem());

        if( book == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("No has seleccionado ningún libro");
            alert.setContentText("Seleccione una fila en la tabla antes de intentar "+action+" un libro.");
            alert.showAndWait();
            return null;
        }

        return book;
    }

    @FXML
    private void EditSelectedBook()
    {
        Book book = GetBookInTable("editar");
        if(book != null)
            Main.OpenBookEditorWindow(book, this);
    }

    @FXML
    private void DeleteSelectedBook()
    {
        Book book = GetBookInTable("eliminar");

        if(book != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Atención");
            alert.setContentText("¿Está seguro de que quiere borrar este libro?");
            if(alert.showAndWait().get() == ButtonType.OK)
            {
                BookRepository.Instance().Delete(book);
                RefreshData();
            }
        }

    }
    private void InitializeBookTable()
    {
        bookCoverCol.setCellValueFactory(new PropertyValueFactory<>("cover"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookEditorialCol.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        bookSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        bookStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

        SearchBook();
    }


    //*************************
    //      BORROW METHODS
    //*************************

    @FXML
    private void BorrowBook()
    {
        Main.OpenBorrowCreatorWindow(
                (Book)(bookTable.getSelectionModel().getSelectedItem()),
                this);
    }

    @FXML
    private void ReturnBook()
    {
        Borrow borrow = GetBorrowInTable("devolver");
        if(borrow != null)
        {
            if(borrow.getState() == Borrow.State.Returned)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("¡Ese alumno te está mintiendo!");
                alert.setContentText("No se puede devolver lo que no se tiene. " +
                        "\n O bien no lo devolvió cuando dijo que lo devolvía" +
                        "\n o bien lo que te está dando no es lo que dice dar.");
                alert.showAndWait();

            } else {
                borrow.setState(Borrow.State.Returned);
                BorrowRepository.Instance().UpdateBorrow(borrow);
                borrowTable.refresh();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Préstamo devuelto");
                alert.setContentText("Gracias por cuidar el material escolar");
                alert.showAndWait();
            }

        }
    }

    private Borrow GetBorrowInTable(String action)
    {
        Borrow borrow = (Borrow)(borrowTable.getSelectionModel().getSelectedItem());

        if( borrow == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("No has seleccionado ningún préstamo");
            alert.setContentText("Seleccione una fila en la tabla antes de intentar "+action+" un libro.");
            alert.showAndWait();
            return null;
        }

        return borrow;
    }

    private void InitializeBorrowTable()
    {
        borrowBookCol.setCellValueFactory(new PropertyValueFactory<>("book_title"));
        borrowStudentCol.setCellValueFactory(new PropertyValueFactory<>("student_name"));
        borrowStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        borrowStartDateCol.setCellValueFactory(new PropertyValueFactory<>("date_Start"));
        borrowEndDateCol.setCellValueFactory(new PropertyValueFactory<>("date_End"));
        if(BorrowRepository.Instance() == null) new BorrowRepository();
        SetBorrowsInTable(BorrowRepository.Instance().UpdateAndGetBorrowList());
    }

    private void SetBorrowsInTable(List<Borrow> borrows)
    {
        ObservableList<Borrow> observableList = FXCollections.observableArrayList(borrows);
        borrowTable.setItems(observableList);
    }

    //*************************
    //      PUBLIC METHODS
    //*************************

    public void RefreshData()
    {
        SetBooksInTableView(BookRepository.Instance().GetAll());
        SetBorrowsInTable(BorrowRepository.Instance().UpdateAndGetBorrowList());

        bookTable.refresh();
        borrowTable.refresh();
    }

    public void SetLastStudentSelected(Student student)
    {
        lastSelectedStudent = student;
    }

    public Student GetLastStudentSelected()
    {
        return lastSelectedStudent;
    }
    public void Init()
    {
        InitializeBookTable();
        InitializeBorrowTable();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Este método no se llamará nunca.
    }

    @FXML private void initialize()
    {
        alumnTabController.InjectMainController(this);
        alumnTabController.Init();
    }


}