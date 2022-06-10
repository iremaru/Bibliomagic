package com.biblio.repository;

import com.biblio.connection.ConnectionDB;
import com.biblio.model.Book;
import com.biblio.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {


    private final String DB_TABLE_NAME = "alumnos";
    private final String DB_COL_CODE = "registro";
    private final String DB_COL_YEAR = "curso";
    private final String DB_COL_NAME = "nombre";
    private final String DB_COL_SURNAME = "apellido";
    private final String DB_COL_HOUSE = "casa";
    private static StudentRepository instance;
    private static List<Student> students = new ArrayList<>();


    //*************************
    //      PUBLIC METHODS
    //*************************

    public StudentRepository()
    {
        if(instance != null) return;
        instance = this;
        GetAllFromDB();
    }

    public static StudentRepository Instance(){return instance;}

    public List<Student> GetStudentList(){
        UpdateStudentList();
        return students;
    }

    public Student GetStudent(int studentCode)
    {
        for (Student student :students) {
            if(student.getCode() == studentCode) return student;
        }
        return null;
    }

    /**
     * Refresh the student list from the db
     */
    public void UpdateStudentList()
    {
        //Get data from db.
        List<Object[]> data;
        if( (data = ConnectionDB.GetTableData(DB_TABLE_NAME)) != null)
            students = PoblateStudentList(data);
    }

    public void createStudent(Student student) {
        students.add(student);
        CreateIntoDB(student);
    }
    public void createStudent(String name, String surname, Student.House house, String year) {
        Student student = new Student(name, surname, house, year);
        CreateIntoDB(student);
        UpdateStudentList();
    }

    public void updateStudent(Student student)
    {
        UpdateIntoDB(student);
    }

    public void deleteStudent(Student student)
    {
        DeleteInDB(student);
        students.remove(student);
    }



    //*************************
    //      PRIVATE METHODS
    //*************************

    /**
     * Get data from DB
     */
    private void GetAllFromDB()
    {
        if(ConnectionDB.GetTableData(DB_TABLE_NAME) != null)
            students = PoblateStudentList(ConnectionDB.GetTableData(DB_TABLE_NAME))  ;
    }

    private void UpdateIntoDB(Student student)
    {
        String sql = "UPDATE `" + DB_TABLE_NAME + "` SET `" +
                DB_COL_YEAR + "` = '" + student.getYear() + "', `" +
                DB_COL_NAME + "` = '" + student.getName() + "', `" +
                DB_COL_SURNAME + "` = '" + student.getSurname() + "', `" +
                DB_COL_HOUSE + "` = '" + student.getHouseString() +
                "' WHERE `"+ DB_TABLE_NAME + "`.`" + DB_COL_CODE + "` = " + student.getCode();
        ConnectionDB.SendInstructionToBD(sql);
    }

    private void CreateIntoDB(Student newStudent)
    {

        String sql = "INSERT INTO " + DB_TABLE_NAME +
                //" ("+ DB_COL_CODE + ", "+
                " (" +
                DB_COL_YEAR + ", " +
                DB_COL_NAME + ", " +
                DB_COL_SURNAME + ", " +
                DB_COL_HOUSE+") "+
                "VALUES ('" +
                //newStudent.getCode() +  "', '" +
                newStudent.getYear() + "', '" +
                newStudent.getName() + "', '" +
                newStudent.getSurname() + "', '" +
                newStudent.getHouseString() + "');";
        ConnectionDB.SendInstructionToBD(sql);
    }

    private void DeleteInDB(Student student)
    {
        String sql = "delete from `" + DB_TABLE_NAME +
                "` where `" + DB_TABLE_NAME + "`.`" + DB_COL_CODE + "`= " +
                student.getCode();
        ConnectionDB.SendInstructionToBD(sql);
    }

    private List<Student> PoblateStudentList(List<Object[]> listFromBaseData)
    {
        List<Student> result = new ArrayList<>();

        for (Object[] student : listFromBaseData) {
            result.add( new Student(
                    (int)student[0],    //code --> Registro
                    (String)student[2], //name
                    (String)student[3], //surname
                    Student.House.getHouse((String)student[4]), //house
                    (String)student[1]     //grade --> curso
            ));
        }
        return result;
    }
}
