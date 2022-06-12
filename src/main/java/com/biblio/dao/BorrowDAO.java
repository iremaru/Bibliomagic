package com.biblio.dao;

import com.biblio.entity.Prestamos;
import com.biblio.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BorrowDAO implements iDAO<Prestamos> {

    public static final BorrowDAO Instance;

    static {
        Instance = new BorrowDAO();
    }

    @Override
    public  List<Prestamos> fetchAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Prestamos> criteriaQuery = criteriaBuilder.createQuery(Prestamos.class);
        Root<Prestamos> prestamosRoot = criteriaQuery.from(Prestamos.class);
        prestamosRoot.join("alumnosByCodAlumno");
        prestamosRoot.join("librosByCodLibros");
        List<Prestamos> result = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return result;
    }

    @Override
    public boolean addData(Prestamos data) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(data);
            result = true;
            transaction.commit();
        } catch (HibernateException e)
        {
            transaction.rollback();
        }
        session.close();
        return result;
    }

    @Override
    public boolean updateData(Prestamos data) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(data);
            result = true;
            transaction.commit();
        } catch (HibernateException e)
        {
            transaction.rollback();
        }
        session.close();
        return result;
    }

    @Override
    public boolean deleteData(Prestamos data) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(data);
            result = true;
            transaction.commit();
        } catch (HibernateException e)
        {
            transaction.rollback();
        }
        session.close();
        return result;
    }
}
