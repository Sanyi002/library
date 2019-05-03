package hu.unideb.inf.library.model.pojo;


import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import javax.persistence.*;

/**
 * Egy User-t reprezentáló osztály
 *
 */

@Entity
public class User {
    /**
     * A user azonosítója. Automatikusan generált, auto increment típusú.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /**
     * A user felhasználó neve.
     */
    @Column (name = "userName", unique = true)
    private String userName;

    /**
     * A user neve.
     */
    @Column (name = "name")
    private String name;

    /**
     * A user jelszava.
     */
    @Column (name = "password")
    private String password;

    /**
     * A user születési helye.
     */
    @Column (name = "birthPlace")
    private String birthPlace;

    /**
     * A user születés ideje.
     */
    @Column (name = "birthDate")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate birthDate;

    /**
     * A user admin-e?
     * Csak admin joggal tudja használni a kölcsönzés és az új könyv felvitele funkciót.
     */
    @Column (name = "admin")
    private boolean admin;

    public User() {
    }

    /**
     * Konstruktor, amely létrehoz egy User objektumot.
     * @param userName user felhasználóneve
     * @param name user neve
     * @param password user jelszava
     * @param birthPlace user születési helye
     * @param birthDate user születés ideje
     * @param admin user admin-e
     */
    public User(String userName, String name, String password, String birthPlace, LocalDate birthDate, boolean admin) {
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.admin = admin;
    }

    /**
     * Visszaadja a user felhasználónevét.
     * @return user felhasználóneve
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Beállítja a user felhasználónevét.
     * @param userName a user felhasználóneve
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Visszaadja a user nevét.
     * @return user neve
     */
    public String getName() {
        return name;
    }

    /**
     * Beállítja a user nevét.
     * @param name a user neve
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Visszadja a user jelszavát.
     * @return user jelszó
     */
    public String getPassword() {
        return password;
    }

    /**
     * Beállítja a user jelszavát.
     * @param password user jelszó
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Visszadja, hogy admin joggal rendelkezik-e a user.
     * @return admin jog (logikai érték)
     */
    public boolean getAdmin() {
        return admin;
    }

    /**
     * Beállítja az admin jogot.
     * @param admin logikai érték
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Visszaadja a user születésének helyét.
     * @return születés helye
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * Beállítja a user születésének helyét.
     * @param birthPlace születés helye
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * Visszaadja a user születésének dátumát.
     * @return születés dátuma
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Beállítja a user születésének dátumát.
     * @param birthDate születés dátuma
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
