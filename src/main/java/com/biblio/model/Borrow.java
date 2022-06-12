package com.biblio.model;

import com.biblio.entity.Prestamos;
import com.biblio.repository.BookRepository;
import com.biblio.repository.StudentRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class Borrow {

    private int id;
    private int id_Student;
    private int id_Book;
    private Date date_Start;
    private Date date_End;
    private State state;
    private String book_title;
    private String student_name;

    private Period borrowPeriod = Period.ofDays(14);

    public enum State{
        Borrowed("Prestado"),
        Delayed("Retrasado"),
        Returned("Devuelto");

        private String label;

        private State(String label)
        {
            this.label = label;
        }

        public String toString(){return label;}
        public static State getState(String stateString)
        {
            if(stateString.equals(Borrowed.label))
            {
                return Borrowed;
            } else if(stateString.equals(Delayed.label))
            {
                return Delayed;
            } else if(stateString.equals(Returned.label))
            {
                return Returned;
            } else
            {
                return Borrowed;
            }
        }
    }

    public Borrow(int id, int id_Student, int id_Book, Date date_Start,
                  Date date_End, State state) {
        this.id = id;
        this.id_Student = id_Student;
        this.id_Book = id_Book;
        this.date_Start = date_Start;
        this.date_End = date_End;
        this.state = state;
        SetBookAndStudent();
    }

    public Borrow(int id_Student, int id_Book) {
        this.id_Student = id_Student;
        this.id_Book = id_Book;
        this.date_Start = Date.valueOf(LocalDate.now());
        this.date_End = Date.valueOf(LocalDate.now().plus(borrowPeriod));
        this.state = State.Borrowed;
        SetBookAndStudent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Student() {
        return id_Student;
    }

    public void setId_Student(int id_Student) {
        this.id_Student = id_Student;
    }

    public int getId_Book() {
        return id_Book;
    }

    public void setId_Book(int id_Book) {
        this.id_Book = id_Book;
    }

    public Date getDate_Start() {
        return date_Start;
    }

    public void setDate_Start(Date date_Start) {
        this.date_Start = date_Start;
    }

    public Date getDate_End() {
        return date_End;
    }

    public void setDate_End(Date date_End) {
        this.date_End = date_End;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getBook_title() {
        return book_title;
    }

    public String getStudent_name() {
        return student_name;
    }

    private void SetBookAndStudent()
    {
        book_title = BookRepository.Instance().Get(id_Book).getTitle();
        student_name = StudentRepository.Instance().GetStudent(id_Student).getName()
                + ' ' +
                StudentRepository.Instance().GetStudent(id_Student).getSurname();
    }

    public static Borrow convertFromPrestamo(Prestamos prestamo)
    {
        return new Borrow(prestamo.getId(),
                prestamo.getCodAlumno(),
                prestamo.getCodLibros(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucion(),
                State.getState(prestamo.getEstado())
                );
    }

    public static Prestamos convertIntoPrestamo(Borrow borrow)
    {
        Prestamos prestamo = new Prestamos();
        prestamo.setId(borrow.id);
        prestamo.setCodAlumno(borrow.getId_Student());
        prestamo.setCodLibros(borrow.getId_Book());
        prestamo.setFechaPrestamo(borrow.date_Start);
        prestamo.setFechaDevolucion(borrow.date_End);
        prestamo.setEstado(borrow.state.label);

        return prestamo;
    }
}

