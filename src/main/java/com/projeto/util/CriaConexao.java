package com.projeto.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriaConexao {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("estoque");

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
