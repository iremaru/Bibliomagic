package com.biblio.dao;

import com.biblio.entity.Libros;
import com.biblio.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookDAO implements iDAO<Libros>{

    public static final BookDAO Instance;

    static {
        Instance = new BookDAO();
    }

    @Override
    public List<Libros> fetchAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Libros> criteriaQuery = criteriaBuilder.createQuery(Libros.class);
        Root<Libros> librosRoot = criteriaQuery.from(Libros.class);
        //librosRoot.join("prestamosByCodigo");
        List<Libros> result = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return result;
    }

    @Override
    public boolean addData(Libros libro){
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(libro);
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
    public boolean updateData(Libros libro) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(libro);
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
    public boolean deleteData(Libros libro) {
        boolean result = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(libro);
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
