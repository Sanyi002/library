package hu.unideb.inf.library.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseFunctions implements AutoCloseable {

    /**
     * A DatabaseFunctions osztály egy példánya.
     */
    private static DatabaseFunctions dbf = new DatabaseFunctions();

    /**
     * EntityManagerFactory osztály egy példánya.
     */
    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            if(entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
            }
        } catch (Exception ex) {
            System.out.println("Fatal: Unable to create entity manager factory" + ex);
        }
    }

    /**
     * Visszaad az adatbáziskapcsolathoz használt dbf objektumot.
     * @return EntityManagerFactory objektum
     */
    public static DatabaseFunctions getInstance() {
        return dbf;
    }

    /**
     * Visszaad az adatbáziskapcsolathoz használt EntityManagerFactory objektumot.
     * @return EntityManagerFactory objektum
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * Visszaad az adatbáziskapcsolathoz használt EntityManager objektumot.
     * @return EntityManager objektum
     */
    public static EntityManager getEntityManager() {
        return  entityManagerFactory.createEntityManager();
    }

    /**
     * Bezárja az adatbáziskapcsolatot.
     */
    @Override
    public void close() {
        if(entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
