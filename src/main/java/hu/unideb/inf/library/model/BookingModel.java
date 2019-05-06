package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.Book;

import javax.persistence.EntityManager;
import java.io.Closeable;
import java.util.List;

public class BookingModel implements AutoCloseable {

    /**
     * EntityManager osztály egy példánya.
     */
    private EntityManager em;

    /**
     * EntityManager osztály példányosítása, az adatbázis kapcsolat létrehozása.
     */
    public BookingModel() {
        this.em = new DatabaseFunctions().getEntityManager();
    }

    @Override
    public void close() {
        this.em.close();
    }
}
