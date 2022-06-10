package com.biblio.repository;

import com.biblio.connection.ConnectionDB;
import com.biblio.model.Borrow;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepository {

    private final String DB_TABLE_NAME = "prestamos";
    private final String DB_COL_ID = "id";
    private final String DB_COL_IDSTUDENT = "codAlumno";
    private final String DB_COL_IDBOOK = "codLibros";
    private final String DB_COL_DATESTART = "fechaPrestamo";
    private final String DB_COL_DATEEND = "fechaDevolucion";
    private final String DB_COL_STATE = "estado";

    private static BorrowRepository instance;
    private static List<Borrow> borrows = new ArrayList<>();


    //*************************
    //      PUBLIC METHODS
    //*************************

    public BorrowRepository()
    {
        if(instance != null)return;;
        instance = this;
        GetAllFromDB();
    }

    public static BorrowRepository Instance(){return instance;}

    public List<Borrow> UpdateAndGetBorrowList()
    {
        UpdateBorrowList();
        return borrows;
    }


    /**
     * Refresh the borrow list from the db
     */
    public void UpdateBorrowList()
    {
        //Get data from db.
        List<Object[]> data;
        if( (data = ConnectionDB.GetTableData(DB_TABLE_NAME)) != null)
            borrows = PoblateBorrowList(data);
    }

    public void CreateBorrow(int bookCode, int studentCode)
    {
        Borrow borrow = new Borrow(studentCode, bookCode);
        CreateIntoDB(borrow);
        UpdateBorrowList();
    }

    public void UpdateBorrow(Borrow borrow){ UpdateIntoDB(borrow);}
    public void DeleteBorrow(Borrow borrow)
    {
        DeleteInDB(borrow);
        borrows.remove(borrow);
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
            borrows = PoblateBorrowList(ConnectionDB.GetTableData(DB_TABLE_NAME))  ;
    }

    private void UpdateIntoDB(Borrow borrow)
    {
        String sql = "UPDATE `" + DB_TABLE_NAME + "` SET `" +
                DB_COL_IDSTUDENT + "` = '" + borrow.getId_Student() + "', `" +
                DB_COL_IDBOOK + "` = '" + borrow.getId_Book() + "', `" +
                DB_COL_DATESTART + "` = '" + borrow.getDate_Start() + "', `" +
                DB_COL_DATEEND + "` = '" + borrow.getDate_End() + "', `" +
                DB_COL_STATE + "` = '" + borrow.getState().toString() +
                "' WHERE `"+ DB_TABLE_NAME + "`.`" + DB_COL_ID + "` = " + borrow.getId();
        ConnectionDB.SendInstructionToBD(sql);
    }

    private void CreateIntoDB(Borrow newBorrow)
    {

        String sql = "INSERT INTO " + DB_TABLE_NAME +
                " (" +
                DB_COL_IDSTUDENT+ ", " +
                DB_COL_IDBOOK + ", " +
                DB_COL_DATESTART + ", " +
                DB_COL_DATEEND + ", " +
                DB_COL_STATE+") "+
                "VALUES ('" +
                newBorrow.getId_Student() + "', '" +
                newBorrow.getId_Book() + "', '" +
                newBorrow.getDate_Start() + "', '" +
                newBorrow.getDate_End() + "', '" +
                newBorrow.getState().toString() + "');";
        ConnectionDB.SendInstructionToBD(sql);
    }

    private void DeleteInDB(Borrow borrow)
    {
        String sql = "delete from `" + DB_TABLE_NAME +
                "` where `" + DB_TABLE_NAME + "`.`" + DB_COL_ID + "`= " +
                borrow.getId();
        ConnectionDB.SendInstructionToBD(sql);
    }

    private List<Borrow> PoblateBorrowList(List<Object[]> listFromBaseData)
    {
        List<Borrow> result = new ArrayList<>();

        for (Object[] borrow : listFromBaseData) {
            result.add( new Borrow(
                    (int)borrow[0],    //code --> Registro
                    (int)borrow[1],     //codAlumno
                    (int)borrow[2],     //codLibro
                    (Date)borrow[3],    //fechaPrestamo
                    (Date)borrow[4],    //fechaDevolucion
                    Borrow.State.getState((String)borrow[5])   //estado
            ));
        }
        return result;
    }
}
