package com.biblio.controller;

import com.biblio.biblioteca.Main;
import com.biblio.repository.BookRepository;
import com.biblio.model.Book;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
        CreateNewBook();

        title.setText("");
        author.setText("");
        editorial.setText("");
    }

    @FXML
    private void CreateAndClose(ActionEvent event)
    {
        CreateNewBook();
        Close(event);
    }

    private void CreateNewBook()
    {
        BookRepository.Instance().Create(
                title.getText(),
                author.getText(),
                editorial.getText(),
                subject.getValue(),
                state.getValue());
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
