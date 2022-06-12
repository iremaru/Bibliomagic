package com.biblio.dao;

import com.biblio.entity.Alumnos;
import com.biblio.entity.Libros;
import com.biblio.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class StudentDAO implements iDAO<Alumnos> {

    public static final StudentDAO Instance;

    static {
        Instance = new StudentDAO();
    }


    @Override
    public List<Alumnos> fetchAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Alumnos> criteriaQuery = criteriaBuilder.createQuery(Alumnos.class);
        Root<Alumnos> alumnosRoot = criteriaQuery.from(Alumnos.class);
        //alumnosRoot.join("prestamosByRegistro");
        List<Alumnos> result = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return result;
    }

    @Override
    public boolean addData(Alumnos data) {
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
    public boolean updateData(Alumnos data) {
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
    public boolean deleteData(Alumnos data) {
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
