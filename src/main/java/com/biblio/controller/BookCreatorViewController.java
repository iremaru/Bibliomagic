package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.repository.BookRepository;
import com.biblio.model.Book;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class BookCreatorViewController {

    @FXML private TextField title;
    @FXML private TextField author;
    @FXML private TextField editorial;
    @FXML private ChoiceBox<Book.Subject> subject;
    @FXML private ChoiceBox<Book.BookState> state;

    @FXML
    private void Close(ActionEvent event)
    {
        Main.CloseSecondaryWindow((Stage)(((Node)(event.getSource())).getScene().getWindow()));
    }

    @FXML
    private void CreateAndContinue(ActionEvent event)
    {
        if(CreateNewBook()){
            title.setText("");
            author.setText("");
            editorial.setText("");
        }

    }

    @FXML
    private void CreateAndClose(ActionEvent event)
    {
        if(CreateNewBook())
            Close(event);
    }

    private boolean CreateNewBook()
    {
        if (title.getText().isBlank() ||
            author.getText().isBlank() ||
            editorial.getText().isBlank() ||
            subject.getValue() == null ||
            state.getValue() == null)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Debes ingresar toda la información");
            String message = "";
            if(title.getText().isBlank()) {
                message = "¿Qué es un libro sin título?\nHasta los libros prohibidos tienen título. Otra cosa es que no se mencione";
            } else if (author.getText().isBlank()) {
                message = "¿Qué es un libro sin autor?\nSi no lo sabes, pon \"anonimo\".\nSi lo sabes, ponlo.";
            } else if (editorial.getText().isBlank()) {
                message = "¿Qué es un libro sin editorial?\nNo me vale el \"Lo hizo un mago\".";
            } else if (subject.getValue() == null) {
                message = "¿Cómo vamos a tener un libro sin asignatura?\n¿Para qué lo quieren los alumnos entonces? ¿Para ligar? Ponlo en pociones, junto al resto de filtros de amor.";
            } else {
                message = "¿Cómo no vas a poner el estado?\n¿Y si lo prestamos cuasinuevo y nos lo devuelven desvencijado? ¿Cómo vamos a darnos cuenta?";

            }
            alert.setContentText(message);
            alert.showAndWait();
            return false;
        } else {
            BookRepository.Instance().Create(
                    title.getText(),
                    author.getText(),
                    editorial.getText(),
                    subject.getValue(),
                    state.getValue());
            return  true;
        }
    }

    //*************************
    //      PUBLIC METHODS
    //*************************

    public void Init()
    {
        subject.setItems(FXCollections.observableArrayList(Book.Subject.values()));
        subject.setConverter(new StringConverter<Book.Subject>() {
            @Override
            public String toString(Book.Subject subject) {
                return subject == null ? null : subject.ToSpanish();
            }

            @Override
            public Book.Subject fromString(String s) {
                return subject.getItems().stream().filter(
                        subjec -> subjec.CompareSpanishSubject(s))
                        .findFirst().orElse(null);
            }
        });
        state.setItems(FXCollections.observableArrayList(Book.BookState.values()));
        state.setConverter(new StringConverter<Book.BookState>() {
            @Override
            public String toString(Book.BookState bookState) {
                return bookState == null ? null : bookState.ToSpanish();
            }

            @Override
            public Book.BookState fromString(String s) {
                return state.getItems().stream().filter(
                        stat -> stat.ToSpanish().equals(s))
                        .findFirst().orElse(null);
            }
        });

    }

}
