package com.biblio.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            SESSION_FACTORY = configuration.buildSessionFactory();
        } catch (Throwable throwable){
            //throwable.printStackTrace();
            System.out.println("la sesión no se pudo inicializar");
            throw new ExceptionInInitializerError(throwable);
        }
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
}
