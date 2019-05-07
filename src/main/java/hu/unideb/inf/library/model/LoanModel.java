package hu.unideb.inf.library.model;

import javax.persistence.EntityManager;

public class LoanModel implements AutoCloseable {
    /**
     * EntityManager osztály egy példánya.
     */
    private EntityManager em;

    /**
     * EntityManager osztály példányosítása, az adatbázis kapcsolat létrehozása.
     */
    public LoanModel() {
        this.em = new DatabaseFunctions().getEntityManager();
    }

    /**
     * Adatbázis kapcsolat lezárása.
     */
    @Override
    public void close() {
        em.close();
    }
}
