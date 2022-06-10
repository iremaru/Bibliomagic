package com.biblio.model;

import com.biblio.repository.StudentRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StudentFinder {
    private final StudentRepository studentRepository = new StudentRepository();
    private String name;
    private String surname;

    private List<Student.House> allowHouses = new LinkedList<>();

    private String year;
    private List<Student> searchResult;

    public StudentFinder()
    {
        name = "";
        surname = "";
        allowHouses = new LinkedList< Student.House >( Arrays.asList( Student.House.values() ));
        year = "";
        searchResult = GetAllMatch();
    }

    public List<Student> Search(String name, String surname, List<Student.House> allowedHouses, String year)
    {
        this.name = name;
        this.surname = surname;
        allowHouses = new LinkedList<>(allowedHouses);
        this.year = year;
        searchResult = GetAllMatch();
        return searchResult;
    }
    public List<Student> Search(String name, String surname, Student.House allowedHouse, String year)
    {
        this.name = name;
        this.surname = surname;
        this.year = year;

        allowHouses.clear();
        allowHouses.add(allowedHouse);

        searchResult = GetAllMatch();
        return searchResult;
    }

    private List<Student> GetAllMatch()
    {
        List<Student> students = StudentRepository.Instance().GetStudentList();
        List<Student> resultList = new ArrayList<>();
        students.forEach(student -> {
            if(MatchName(student, name)
                    && MatchSurname(student, surname)
                    && MatchHouse(student, allowHouses)
                    && MatchYear(student, year))
                resultList.add(student);
        });
        return resultList;
    }

    //-----------------------
    //      Finder Related
    //-----------------------
    private boolean MatchName(Student student, String nameSearched)
    {
        return CompareStrings(student.getName(), nameSearched);
    }

    private boolean MatchSurname(Student student, String surnameSearched)
    {
        return CompareStrings(student.getSurname(), surnameSearched);
    }

    private boolean MatchHouse(Student student, List<Student.House> houses)
    {
        if(houses.size() == Student.House.values().length
                || houses.size() < 1) return true;
        for (Student.House house :houses) {
            if(house == student.getHouse()) return true;
        }
        return false;
    }

    private boolean MatchYear(Student student, String yearSearched)
    {
        return CompareStrings(student.getYear(), yearSearched);
    }

    private boolean CompareStrings(String studentString, String searchedString)
    {
        if(studentString.isBlank()) return true;
        if(studentString.equalsIgnoreCase(searchedString));
        studentString = studentString.toUpperCase();
        searchedString = searchedString.toUpperCase();
        return MatchAllWords(studentString, searchedString);
    }

    private boolean MatchAllWords(String text, String searchedWords)
    {
        String[] eachWord = searchedWords.split("([ ,.:;?¿!¡]+)");
        for (String word : eachWord) {
            if(!text.contains(word)) return false;
        }
        return true;
    }
}
