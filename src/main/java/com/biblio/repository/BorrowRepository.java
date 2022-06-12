package com.biblio.repository;

import com.biblio.connection.ConnectionDB;
import com.biblio.dao.BorrowDAO;
import com.biblio.entity.Prestamos;
import com.biblio.model.Borrow;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepository {

    private static BorrowRepository instance;
    private static List<Borrow> borrows = new ArrayList<>();


    //*************************
    //      PUBLIC METHODS
    //*************************

    public BorrowRepository()
    {
        if(instance != null)return;;
        instance = this;
        PoblateBorrowList();
    }

    public static BorrowRepository Instance(){return instance;}

    public List<Borrow> UpdateAndGetBorrowList()
    {
        return PoblateBorrowList();
    }

    public void CreateBorrow(int bookCode, int studentCode)
    {
        Borrow borrow = new Borrow(studentCode, bookCode);
        BorrowDAO.Instance.addData(Borrow.convertIntoPrestamo(borrow));
        borrows.add(borrow);
    }

    public void UpdateBorrow(Borrow borrow){
        BorrowDAO.Instance.updateData(Borrow.convertIntoPrestamo(borrow));
    }

    public void DeleteBorrow(Borrow borrow)
    {
        BorrowDAO.Instance.deleteData(Borrow.convertIntoPrestamo(borrow));
        borrows.remove(borrow);
    }

    //*************************
    //      PRIVATE METHODS
    //*************************

    private List<Borrow> PoblateBorrowList()
    {
        borrows.clear();
        for (Prestamos prestamo : BorrowDAO.Instance.fetchAll()) {
            borrows.add(Borrow.convertFromPrestamo(prestamo));
        }

        return borrows;
    }
}
