package com.biblio.repository;

import com.biblio.model.Book;
import com.biblio.connection.ConnectionDB;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private final String DB_TABLE_NAME = "libros";
    private final String DB_COL_CODE = "codigo";
    private final String DB_COL_TITLE = "titulo";
    private final String DB_COL_AUTHOR = "autor";
    private final String DB_COL_EDITORIAL = "editorial";
    private final String DB_COL_STATE = "estado";
    private final String DB_COL_SUBJECT = "asignatura";
    private final String DB_COL_AVAILABILITY = "disponible";

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
        GetFromDB();
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
        Book newBook = new Book(AssignBookCode(), title,author,editorial,subject,state, 1);

        books.add(newBook);
        CreateIntoDB(newBook);
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

    public void UpdateAll() {
        UpdateDB();
    }

    public void Update(Book book) {
        //// TODO: 13/01/2022
        UpdateIntoDB(book);
    }

    public void Delete(Book book) {
        DeleteInDB(book);
        books.remove(book);
    }

    //*************************
    //      PRIVATE METHODS
    //*************************

    /**
     * Get data from DB
     */
    private void GetFromDB()
    {
        if(ConnectionDB.GetTableData(DB_TABLE_NAME) != null)
            books = PoblateBookList(ConnectionDB.GetTableData(DB_TABLE_NAME))  ;
    }

    private void UpdateDB()
    {
        for (Book book :books) {
            UpdateIntoDB(book);
        }
    }

    /**
     * Update data into database
     * @param newBook
     */
    private void UpdateIntoDB(Book newBook)
    {
        //UPDATE `libros` SET `Titulo` = 'A', `Autor` = 'B', `Editorial` = 'C',
        // `Asignatura` = 'A', `estado` = 'D' WHERE `libros`.`codigo` = 0
        String sql = "UPDATE `" + DB_TABLE_NAME + "` SET `" +
                DB_COL_TITLE + "` = '" + newBook.getTitle() + "', `" +
                DB_COL_AUTHOR + "` = '" + newBook.getAuthor() + "', `" +
                DB_COL_EDITORIAL + "` = '" + newBook.getEditorial() + "', `" +
                DB_COL_SUBJECT + "` = '" + newBook.getSubjectString() + "', `" +
                DB_COL_AVAILABILITY + "` = '" + newBook.getAvailableInt() + "', `" +
                DB_COL_STATE + "` = '" + newBook.getStateString() +
                "' WHERE `"+ DB_TABLE_NAME + "`.`" + DB_COL_CODE + "` = " + newBook.getCode();
        ConnectionDB.SendInstructionToBD(sql);

    }

    private void CreateIntoDB(Book newBook)
    {
        String sql = "INSERT INTO " + DB_TABLE_NAME +
                " ("+ DB_COL_CODE + ", "+
                DB_COL_TITLE + ", " +
                DB_COL_AUTHOR + ", " +
                DB_COL_EDITORIAL + ", " +
                DB_COL_SUBJECT + ", " +
                DB_COL_STATE+") "+
                "VALUES ('" +
                newBook.getCode() +  "', '" +
                newBook.getTitle() + "', '" +
                newBook.getAuthor() + "', '" +
                newBook.getEditorial() + "', '" +
                newBook.getSubjectString() + "', '" +
                newBook.getStateString() + "');";
        ConnectionDB.SendInstructionToBD(sql);
    }

    private void DeleteInDB(Book book)
    {
        //"DELETE FROM `libros` WHERE `libros`.`codigo` = 9"
        String sql = "delete from `" + DB_TABLE_NAME +
                "` where `" + DB_TABLE_NAME + "`.`" + DB_COL_CODE + "`= " +
                book.getCode();
        ConnectionDB.SendInstructionToBD(sql);
    }

    private List<Book> PoblateBookList(List<Object[]> listFromBaseData)
    {
        //We will receive a List of rows (Data Entries)
        //And each row will have a []object in each column.

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

        return result;
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
