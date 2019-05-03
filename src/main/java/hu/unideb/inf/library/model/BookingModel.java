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

    public void pushNewBook(String isbn, String title, String author, String publisher, int releaseDate, int pages, String subjects, String storageSign) {
        Book nb = new Book(isbn, title, author, publisher, releaseDate, pages, subjects, storageSign);
        try {
            em.getTransaction().begin();
            em.persist(nb);
            em.getTransaction().commit();
        } catch (Exception ex) {
            // TODO: Log infó: Hiba az új könyv hozzáadásakor
            System.out.println("Hiba az új könyv hozzáadásakor: "+ ex);
        }

    }

    @Override
    public void close() {
        this.em.close();
    }
}
