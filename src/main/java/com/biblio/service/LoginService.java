package com.biblio.service;

import com.biblio.entity.Prestamos;
import com.biblio.entity.Usuarios;
import com.biblio.model.User;
import com.biblio.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.biblio.connection.ConnectionDB.connection;

public class LoginService {

    public static boolean CheckUserCredential(String userName, String password)
    {
        /*
        Usuarios loginUser = new Usuarios();
        loginUser.setClave(password);
        loginUser.setUsuario(userName);

        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Usuarios> criteriaQuery = criteriaBuilder.createQuery(Usuarios.class);

        Root<Usuarios> userRoot = criteriaQuery.from(Usuarios.class);
        Predicate predicatePassword = criteriaBuilder.equal(userRoot.get("clave"), password);
        Predicate predicateName = criteriaBuilder.equal(userRoot.get("usuario"), userName);
        Predicate predicateAnd = criteriaBuilder.and(predicateName, predicatePassword);

        criteriaQuery.where(predicateAnd);

        Usuarios bdUser = session.createQuery(criteriaQuery).getSingleResult();

        session.close();
        return bdUser != null;*/

        final String userTable = "usuarios";
        final String userField = "usuario";
        final String passwordField = "clave";
        boolean result = false;

        String SQLrequest = "SELECT * FROM "+userTable+" WHERE "+userField+"=? AND "+passwordField+"=?";
        try {
            PreparedStatement statement = connection.prepareStatement(SQLrequest) ;
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.next();

        } catch (Exception ex){ex.printStackTrace();}

        return result;
    }

}
