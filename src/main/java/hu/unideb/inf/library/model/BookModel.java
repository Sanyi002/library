package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.Loan;
import javafx.scene.control.TextField;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
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
     * Új könyv hozzáadása az adatbázishoz.
     * // TODO
     * @param isbn
     * @param title
     * @param author
     * @param publisher
     * @param releaseDate
     * @param pages
     * @param subjects
     * @param storageSign
     */
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
     * A módosított könyv frissítése az adatbázisban.
     * @param book a módosított könyv egy példánya
     */
    public void updateBook(Book book) {
        try {
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();
        } catch (Exception ex) {
            // TODO: Log infó: Nem sikerült a könyv frissítése.
        }
    }

    /**
     * Könyv hozzáadása/frissítése esetén az input értékékek validálása.
     * @param inputs
     * @return int érték: 1 = sikeres, 0 = hibás mező érték, -1 = üres mezők
     */
    public int bookValidation(Map<String, TextField> inputs) {
        if(inputs.entrySet().stream().anyMatch(textField -> textField.getValue().getText().trim().isEmpty())) {
            return -1;
        } else {
            try {
                Integer.parseInt(inputs.get("bookPagesInput").getText());
            } catch (NumberFormatException ex) {
                return 0;
            }
            try {
                Integer.parseInt(inputs.get("bookReleaseDateInput").getText());
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * Kölcsönözhető könyvek lekérése.
     * @return kölcsönözhető könyvek listája
     */
    public List<Book> getLoanableBooks() {
        List<Book> result = null;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> bookRoot = query.from(Book.class);
        query.select(bookRoot);

        Subquery<Loan> subquery = query.subquery(Loan.class);
        Root<Loan> subRoot = subquery.from(Loan.class);
        subquery.select(subRoot);

        Predicate p = cb.equal(subRoot.get("bookISBN"), bookRoot);
        subquery.where(p);
        query.where(cb.not(cb.exists(subquery)));

        TypedQuery<Book> typedQuery = em.createQuery(query);

        return result = typedQuery.getResultList();
    }

    /**
     * Adatbázis kapcsolat lezárása.
     */
    @Override
    public void close() {
        this.em.close();
    }

}
