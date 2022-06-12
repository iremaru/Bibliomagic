package com.biblio.repository;

import com.biblio.dao.BookDAO;
import com.biblio.dao.StudentDAO;
import com.biblio.entity.Libros;
import com.biblio.model.Book;
import com.biblio.connection.ConnectionDB;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private static BookRepository instance;
    private static List<Book> books = new ArrayList<>();
    private static List<Integer> codeNotSetted = new ArrayList<>();
    private static int highestCode;

    //*************************
    //      PUBLIC METHODS
    //*************************

    public BookRepository()
    {
        if(instance != null) return;

        instance = this;
        PoblateBookList();
        CheckCodes();
    }

    public static BookRepository Instance()
    {
        return instance;
    }


    public List<Book> GetAllListed()
    {
        return books;
    }


    public void Create(String title, String author,
                       String editorial, Book.Subject subject, Book.BookState state)
    {
        Book newBook = new Book(AssignBookCode(),
                title,
                author,
                editorial,
                subject,
                state,
                1);

        books.add(newBook);
        BookDAO.Instance.addData(Book.convertIntoLibro(newBook));
    }

    public Book Get(int bookCode) {

        for (Book book :books) {
            if(book.getCode() == bookCode) return book;
        }
        return null;
    }

    public Book[] GetAll() {
        return books.toArray(Book[]::new);
    }


    public void Update(Book book) {
        BookDAO.Instance.updateData(Book.convertIntoLibro(book));
    }

    public void Delete(Book book) {
        BookDAO.Instance.deleteData(Book.convertIntoLibro(book));
        books.remove(book);
    }

    //*************************
    //      PRIVATE METHODS
    //*************************


    private List<Book> PoblateBookList()
    {
        List<Libros> libros = BookDAO.Instance.fetchAll();

        books.clear();
        for (Libros libro :libros) {
            books.add(Book.convertFromLibro(libro));
        }
        return books;
        /*
        List<Book> result = new ArrayList<>();

        //Each Row -> A Book
        for (Object[] book : listFromBaseData) {
            //Each Column -> A Book property
            int code = (int) book[0];
            String cover = "Portada de prueba";
            String title = (String)book[1];
            String author = (String) book[2];
            String editorial = (String) book[3];
            Book.Subject subject = Book.TransformToSubject((String) book[4]);
            Book.BookState state = Book.TransformToBookState((String) book[5]);
            Boolean isAvailable = (Boolean)book[6];

            result.add( new Book(code, title, author, editorial, subject, state, isAvailable) );
        }

        return result;*/
    }

    private void CheckCodes()
    {
        int lastCode = books.get(books.size()-1).getCode();

        for (int i = 0; i < lastCode; i++) {
            boolean codeIsTaken = false;
            for (Book book : books) {

                int bookCode = book.getCode();

                if(bookCode > highestCode) highestCode = bookCode;

                if (bookCode == i) {
                    codeIsTaken = true;
                    break;
                }
            }
            if(!codeIsTaken) codeNotSetted.add(i);
        }

    }

    private int AssignBookCode()
    {
        if(codeNotSetted.size() < 1)
        {
            highestCode++;
            return highestCode;
        }
        int result = codeNotSetted.get(0);
        codeNotSetted.remove(0);
        return result;
    }
}
