package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.Loan;

import javax.persistence.EntityManager;
import java.time.LocalDate;

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

    public void addLoan(String bookISBN, int userID) {
        Loan loan = new Loan(bookISBN, userID);

        try {
            em.getTransaction().begin();
            em.persist(loan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            // TODO: Log info
        }
    }

    /**
     * Adatbázis kapcsolat lezárása.
     */
    @Override
    public void close() {
        this.em.close();
    }
}
