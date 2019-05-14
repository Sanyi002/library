package hu.unideb.inf.library.model;

import hu.unideb.inf.library.model.pojo.User;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserModel implements AutoCloseable {

    /**
     * Logger osztály egy példánya.
     */
    private Logger logger = LoggerFactory.getLogger(UserModel.class);

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
     * Felhasználó lekérése felhasználónév és jelszó alapján.
     * @param userName felhasználóinév
     * @param password felhasználó jelszava
     * @return bejelentkezett felasználó obejtuma
     */
    public User getUser(String userName, String password) {
        User user = null;

        try {
            TypedQuery<User> query = em.createQuery("" +
                    "SELECT u " +
                    "FROM User u " +
                    "WHERE userName = '" + userName + "' and password = '" + password + "'", User.class);
            user = query.getSingleResult();
        } catch (Exception ex) {
            logger.error("Hiba a felhasználó lekérésekor! " + ex);
        }

        return user;
    }

    /**
     * Admin jogokkal nem rendelkező felhasználók lekérése.
     * @return admin jogokkal nem rendelkezző felhasználók listája
     */
    public List<User> getNotAdmins() {
        List<User> result = null;

        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.admin = false", User.class);
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
