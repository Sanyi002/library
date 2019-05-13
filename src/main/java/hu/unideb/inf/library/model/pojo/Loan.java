package hu.unideb.inf.library.model.pojo;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Egy Loan-t reprezentáló osztály
 */
@Entity
public class Loan {

    /**
     * A könyv ISBN száma.
     */
    @Id
    @Column (name = "bookISBN")
    private String bookISBN;

    /**
     * A felhasználó ID-ja.
     */
    @Column (name = "userID")
    private int userID;

    /**
     * A kölcsönzés kezdetének dátuma.
     */
    @Column (name = "loanStart")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate loanStart;

    /**
     * A kölcsönzés végének dátuma.
     */
    @Column (name = "loanEnd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate loanEnd;

    public Loan() {}

    /**
     * Konstruktor, amely létrehozz egy Loan objektumot.
     * @param bookISBN a könyv ISBN száma
     * @param userID a felhasználó ID-ja
     */
    public Loan(String bookISBN, int userID) {
        this.bookISBN = bookISBN;
        this.userID = userID;
        this.loanStart = new LocalDate();
        this.loanEnd = this.loanStart.plusDays(30);
    }

    /**
     * Visszaadja a kölcsönzött könyv ISBN számát.
     * @return kölcsönzött könyv ISBN száma
     */
    public String getBookISBN() {
        return bookISBN;
    }

    /**
     * Visszaadja a kölcsönző felhasználó ID-ját.
     * @return felhasználó ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Visszaadja a kölcsönzés kezdetének dátumát.
     * @return kölcsönzés kezdetének dátuma
     */
    public LocalDate getLoanStart() {
        return loanStart;
    }

    /**
     * Visszaadja a kölcsönzés végének dátumát.
     * @return kölcsönzés végének dátuma
     */
    public LocalDate getLoanEnd() {
        return loanEnd;
    }
}
