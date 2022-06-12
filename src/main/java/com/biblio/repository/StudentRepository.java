package com.biblio.repository;

import com.biblio.connection.ConnectionDB;
import com.biblio.dao.StudentDAO;
import com.biblio.entity.Alumnos;
import com.biblio.model.Book;
import com.biblio.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private static StudentRepository instance;
    private static List<Student> students = new ArrayList<>();


    //*************************
    //      PUBLIC METHODS
    //*************************

    public StudentRepository()
    {
        if(instance != null) return;
        instance = this;
        PoblateStudentList();
    }

    public static StudentRepository Instance(){return instance;}

    public List<Student> GetStudentList(){
        PoblateStudentList();
        return students;
    }

    public Student GetStudent(int studentCode)
    {
        for (Student student :students) {
            if(student.getCode() == studentCode) return student;
        }
        return null;
    }

    public void createStudent(Student newStudent) {
        students.add(newStudent);
        StudentDAO.Instance.addData(Student.convertIntoAlumno(newStudent));
    }
    public void createStudent(String name, String surname, Student.House house, String year) {
        Student student = new Student(name, surname, house, year);
        StudentDAO.Instance.addData(Student.convertIntoAlumno(student));;
        students.add(student);
    }

    public void updateStudent(Student student)
    {
        StudentDAO.Instance.updateData(Student.convertIntoAlumno(student));
    }

    public void deleteStudent(Student student)
    {
        StudentDAO.Instance.deleteData(Student.convertIntoAlumno(student));
        students.remove(student);
    }



    //*************************
    //      PRIVATE METHODS
    //*************************


    private List<Student> PoblateStudentList()
    {
        students.clear();
        for (Alumnos alumno : StudentDAO.Instance.fetchAll()) {
            students.add(Student.convertFromAlumno(alumno));
        }
        return students;
    }
}
