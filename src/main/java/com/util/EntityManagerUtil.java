package com.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory factory= null;
    private static EntityManager em = null;

    public static EntityManager getEntityManager(){
        if(factory == null){
            factory = Persistence.createEntityManagerFactory("jpaPU");
        }

        if(em==null){
            em = factory.createEntityManager();
        }

        return em;
    }
}
