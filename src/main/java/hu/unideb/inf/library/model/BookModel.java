package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.Book;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class BookModel implements AutoCloseable {

    /**
     * EntityManager osztály egy példánya.
     */
    private EntityManager em;

    /**
     * EntityManager osztály példányosítása, az adatbázis kapcsolat létrehozása.
     */
    public BookModel() {
        this.em = new DatabaseFunctions().getEntityManager();
    }

    /**
     * Az összes könyv lekérése az adatbázisból.
     * @return könyvek egy listája
     */
    public List<Book> getAllBook() {
        List<Book> list = null;

        try {
            TypedQuery<Book> query = em.createQuery(
                    "SELECT b FROM Book b", Book.class);
            list = query.getResultList();
        } catch (Exception ex) {
            // TODO: Log infó: Hiba
        }
        return list;
     }

    /**
     * Könyv keresése cím és/vagy szerző alapján.
     * @param title a könyv címe
     * @param author a könyv szerzője
     * @return a keresett könyv(ek) listája
     */
    public List<Book> getSearchedBooks(String title, String author) {
        List<Book> result = null;

        if(!title.isEmpty() && author.isEmpty()) {
            try {
                TypedQuery<Book> query = em.createQuery(
                        "SELECT b FROM Book b WHERE title LIKE '%" + title + "%'", Book.class);
                result = query.getResultList();
            } catch (Exception ex) {
                // TODO: Log infó: Hiba
            }
        } else if(!author.isEmpty() && title.isEmpty()) {
            try {
                TypedQuery<Book> query = em.createQuery(
                        "SELECT b FROM Book b WHERE author LIKE '%" + author + "%'", Book.class);
                result = query.getResultList();
            } catch (Exception ex) {
                // TODO: Log infó: Hiba
            }
        } else {
            try {
                TypedQuery<Book> query = em.createQuery(
                        "SELECT b FROM Book b WHERE title LIKE '%" + title + "%' AND author LIKE '%" + author + "%'", Book.class);
                result = query.getResultList();
            } catch (Exception ex) {
                // TODO: Log infó: Hiba
            }
        }

        return result;
    }

    /**
     * Adatbázis kapcsolat lezárása.
     */
    @Override
    public void close() {
        em.close();
    }

}
