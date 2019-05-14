package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.Book;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class BookModel implements AutoCloseable {

    /**
     * Logger osztály egy példánya.
     */
    private Logger logger = LoggerFactory.getLogger(BookModel.class);

    /**
     * EntityManager osztály egy példánya.
     */
    private EntityManager em;

    Session session;

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
            logger.error("Hiba az adatok lekérésekor! " + ex);
        }
        return list;
     }

    /**
     * Új könyv hozzáadása az adatbázishoz.
     * @param isbn könyv ISBN száma
     * @param title könyv címe
     * @param author könyv szerzője
     * @param publisher könyv kiadója
     * @param releaseDate könyv megjelenésének éve
     * @param pages könyv terjedelme
     * @param subjects könyv tárgyszavai
     * @param storageSign könyv raktári jelzete
     */
    public void pushNewBook(String isbn, String title, String author, String publisher, int releaseDate, int pages, String subjects, String storageSign) {
        Book nb = new Book(isbn, title, author, publisher, releaseDate, pages, subjects, storageSign);
        try {
            em.getTransaction().begin();
            em.persist(nb);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.error("Hiba az új könyv hozzáadásakor! " + ex);
        }
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
                logger.error("Hiba az adatok lekérésekor! " + ex);
            }
        } else if(!author.isEmpty() && title.isEmpty()) {
            try {
                TypedQuery<Book> query = em.createQuery(
                        "SELECT b FROM Book b WHERE author LIKE '%" + author + "%'", Book.class);
                result = query.getResultList();
            } catch (Exception ex) {
                logger.info("Hiba az adatok lekérésekor! " + ex);
            }
        } else {
            try {
                TypedQuery<Book> query = em.createQuery(
                        "SELECT b FROM Book b WHERE title LIKE '%" + title + "%' AND author LIKE '%" + author + "%'", Book.class);
                result = query.getResultList();
            } catch (Exception ex) {
                logger.error("Hiba az adatok lekérésekor! " + ex);
            }
        }

        return result;
    }

    /**
     * A módosított könyv frissítése az adatbázisban.
     * @param book a módosított könyv egy példánya
     */
    public void updateBook(Book book) {
        try {
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.error("Hiba az adatok módosításakor! " + ex);
        }
    }

    /**
     * Könyv hozzáadása/frissítése esetén az input értékékek validálása.
     * @param inputs TextField-ek értékei
     * @return int érték: 1 = sikeres, 0 = hibás mező érték, -1 = üres mezők
     */
    public int bookValidation(Map<String, TextField> inputs) {
        if(inputs.entrySet().stream().anyMatch(textField -> textField.getValue().getText().trim().isEmpty())) {
            logger.error("Sikertelen könyv validálás! Üres mezők.");
            return -1;

        } else {
            try {
                logger.error("Sikertelen könyv validálás! Hibás mező érték.");
                Integer.parseInt(inputs.get("bookPagesInput").getText());
            } catch (NumberFormatException ex) {
                return 0;
            }

            try {
                logger.error("Sikertelen könyv validálás! Hibás mező érték.");
                Integer.parseInt(inputs.get("bookReleaseDateInput").getText());
            } catch (NumberFormatException ex) {
                return 0;
            }
        }

        logger.info("Sikeres könyv validálás.");
        return 1;
    }

    /**
     * Kölcsönözhető könyvek lekérése.
     * @return kölcsönözhető könyvek listája
     */
    public List<Book> getLoanableBooks() {
        List<Book> result = null;

        try {
            TypedQuery<Book> query = em.createNamedQuery("BookModel.getLoanableBooks",Book.class);
            result = query.getResultList();
        } catch (Exception ex) {
            logger.error("Hiba az adatok lekérésekor! " + ex);
        }
        return result;
    }

    /**
     * Adatbázis kapcsolat lezárása.
     */
    @Override
    public void close() {
        this.em.close();
    }

}
