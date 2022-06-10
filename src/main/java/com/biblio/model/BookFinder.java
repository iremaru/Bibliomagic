package com.biblio.model;

import com.biblio.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookFinder {

    private final BookRepository bookRepository = new BookRepository();

    private String title;
    private String editorial;
    private String author;
    private Book.BookState[] states;
    private Book.Subject.Type[] subjectTypes;
    private Book.Subject subject;
    private Book[] searchResult;
    private boolean requireAvailability;

    /**
     * Instantiate BookFinder with a global search*/
    public BookFinder()
    {
        this.title = "";
        this.editorial = "";
        this.author = "";
        SetStates(true, true);
        SetSubjects(true, true, true);
        requireAvailability = false;
        this.searchResult = GetAllMatch();
    }

    /**
     * Instantiate BookFinder with a specific search*/
    public BookFinder(String title, String editorial, String author,
                      boolean isNewBook, boolean isUsedBook,
                      boolean isCore, boolean isSelective, boolean isExtra) {
        this.title = title;
        this.editorial = editorial;
        this.author = author;
        SetStates(isUsedBook, isNewBook);
        SetSubjects(isCore, isSelective, isExtra);
        this.searchResult = GetAllMatch();
    }

    public void Search(String title, String editorial, String author,
                      boolean isNewBook, boolean isUsedBook,
                      boolean isCore, boolean isSelective, boolean isExtra) {
        this.title = title;
        this.editorial = editorial;
        this.author = author;
        SetStates(isUsedBook, isNewBook);
        SetSubjects(isCore, isSelective, isExtra);
        subject = null;
        requireAvailability = false;

        this.searchResult = GetAllMatch();
    }

    public void Search(String title, String editorial, String author,
                       boolean isNewBook, boolean isUsedBook,
                       Book.Subject subject, boolean isAvailable) {
        this.title = title;
        this.editorial = editorial;
        this.author = author;
        SetStates(isUsedBook, isNewBook);
        this.subject = subject;
        this.searchResult = GetAllMatch();
        requireAvailability = isAvailable;
    }

    public Book[] GetResult()
    {
        return searchResult;
    }

    //*************************
    //      PRIVATE METHODS
    //*************************



    private void SetStates(boolean usedBook, boolean newBook)
    {
        int count = (usedBook ? 2 : 0) + (newBook ? 2 : 0);

        Book.BookState[] resultStates = new Book.BookState[count];
        if(count == 4) {
            resultStates[0] = Book.BookState.Rickety;
            resultStates[1] = Book.BookState.Used;
            resultStates[2] = Book.BookState.NewLike;
            resultStates[3] = Book.BookState.New;
        } else if (usedBook)
        {
            resultStates[0] = Book.BookState.Rickety;
            resultStates[1] = Book.BookState.Used;
        } else if (newBook)
        {
            resultStates[0] = Book.BookState.NewLike;
            resultStates[1] = Book.BookState.New;
        }

        states = resultStates;
    }

    private void SetSubjects(boolean isCore, boolean isSelective, boolean isExtra)
    {
        int count = (isCore? 1 : 0) + (isSelective? 1:0) + (isExtra ? 1 : 0);
        Book.Subject.Type[] types = new Book.Subject.Type[count];

        if(isCore)
        {
            count--;
            types[count] = Book.Subject.Type.Core;
        }
        if(isSelective)
        {
            count--;
            types[count] = Book.Subject.Type.Elective;
        }
        if(isExtra)
        {
            count--;
            types[count] = Book.Subject.Type.Extracurricular;
        }
        subjectTypes = types;
    }

        //-----------------------
        //      Finder Related
        //-----------------------

    private Book[] GetAllMatch()
    {
        List<Book> books = BookRepository.Instance().GetAllListed();
        List<Book> resultList = new ArrayList<Book>();
        books.forEach( book -> {
            if (MatchTitle(book, title)
                    && MatchAuthor(book, author)
                    && MatchEditorial(book, editorial)
                    && (subject == null ? MatchSubjectTypes(book, subjectTypes)
                    : MatchSubject(book, subject))
                    && MatchStates(book, states)
                    && MatchAvailability(book, requireAvailability))
                resultList.add(book);
        });
        return resultList.toArray(Book[]::new) ;
    }

    private boolean MatchTitle(Book book, String titleAllowed )
    {
        if (titleAllowed.isBlank()) return true;
        String bookTitle = book.getTitle().toUpperCase();
        titleAllowed = titleAllowed.toUpperCase();
        //return book.getTitle().equalsIgnoreCase(titleAllowed) || book.getTitle().toUpperCase().contains(titleAllowed.toUpperCase());
        return bookTitle.equalsIgnoreCase(titleAllowed)
                || MatchAllWords(bookTitle, titleAllowed);
    }

    private boolean MatchAuthor(Book book, String author)
    {
        if(author.isBlank()) return true;

        String bookAuthor = book.getAuthor().toUpperCase();
        author = author.toUpperCase();

        //return book.getAuthor().equalsIgnoreCase(author) || book.getAuthor().contains(author);
        return bookAuthor.equalsIgnoreCase(author) || MatchAllWords(bookAuthor, author);
    }

    private boolean MatchEditorial(Book book, String editorial)
    {
        return editorial.isBlank() || MatchAllWords(book.getEditorial().toUpperCase(), editorial.toUpperCase());
    }

    private boolean MatchAllWords(String text, String searchedWords)
    {
        //String[] eachWord = searchedWords.split("([A-Z][áóíúé]?)\\w+/gi");
        String[] eachWord = searchedWords.split("([ ,.:;?¿!¡]+)");
        for (String word : eachWord) {
            if(!text.contains(word)) return false;
        }
        return true;
    }

    private boolean MatchSubject(Book book, Book.Subject subject)
    {
        return book.getSubject() == subject;
    }

    private boolean MatchSubjectTypes(Book book, Book.Subject.Type[] typeAllows)
    {
        for (Book.Subject.Type type :
                typeAllows) {
            if (type == book.GetSubjectTypePlain()) return true;
        }
        return false;
    }

    private boolean MatchStates(Book book, Book.BookState[] statesAllowed)
    {
        for (Book.BookState state :  statesAllowed) {
            if(state == book.GetBookState()) return true;
        }
        return false;
    }

    private boolean MatchAvailability(Book book, boolean isRequired)
    {
        return isRequired ? book.getAvailable() : true;
    }
}
