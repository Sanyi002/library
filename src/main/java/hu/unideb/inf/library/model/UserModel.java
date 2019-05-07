package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.User;
import org.joda.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserModel implements AutoCloseable {

    /**
     * EntityManager osztály egy példánya.
     */
    private EntityManager em;

    /**
     * EntityManager osztály példányosítása, az adatbázis kapcsolat létrehozása.
     */
    public UserModel() {

        this.em = new DatabaseFunctions().getEntityManager();
    }

    /**
     *
     * @param userName
     * @return
     */
    public User getUser(String userName, String password) {
        User user = null;

        try {
            // TODO: Logger info: Sikeres felhasználó lekérés loggolása
            TypedQuery<User> query = em.createQuery("" +
                    "SELECT u " +
                    "FROM User u " +
                    "WHERE userName = '" + userName + "' and password = '" + password + "'", User.class);
            user = query.getSingleResult();
        } catch (Exception ex) {
            // TODO: Logger info: Hiba loggolása
            System.out.println("hiba!! " + ex.getMessage());
        }

        return user;
    }

    /**
     * A nem admin jogokkal rendelkező felhasználók lekérése.
     */
    public List<User> getNotAdmins() {
        List<User> result = null;

        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.admin = false", User.class);
            result = query.getResultList();
        } catch (Exception ex) {
            // TODO: Log infó
        }

        return  result;
    }


    /**
     * Adatbázis kapcsolat lezárása.
     */
    @Override
    public void close() {
        this.em.close();
    }

}
