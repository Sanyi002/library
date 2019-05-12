package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.Loan;

import javax.persistence.*;
import java.util.List;

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
     * Új kölcsönzés hozzáadása az adatbázishoz.
     * @param bookISBN a könyv ISBN száma
     * @param userID a felhasználó ID-ja
     */
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
     * Kölcsönözhető-e egy könyv.
     * @param isbn a vizsgálni kívánt könyv ISBN száma
     * @return logikai érték
     */
    public boolean isLoanable(String isbn) {
        boolean result;
        Loan loan = null;

        try {
            Query query = em.createQuery("" +
                    "SELECT l FROM Loan l WHERE l.bookISBN = '" + isbn + "'", Loan.class);
            loan = (Loan) query.getSingleResult();
        } catch (Exception ex) {
            // TODO
        }

        if(loan == null) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    /**
     * Összes kölcsönzés adatainak lekérése.
     * @return lista a kölcsönzésekről
     */
    public List<Loan> allLoan() {

        List<Loan> result = null;

        try {
            TypedQuery<Loan> query = em.createQuery(
                    "SELECT l FROM Loan l", Loan.class);
            result = query.getResultList();
        } catch (Exception ex) {
            // TODO: Log infó
        }

        return result;
    }

    /**
     * Kölcsönzés eltávolítása az adatbázisból.
     * @param loan kölcsönzés objektuma
     */
    public void removeLoan(Loan loan) {
        try {
            em.getTransaction().begin();
            em.remove(loan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            //TODO: Log infó
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
