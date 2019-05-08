package hu.unideb.inf.library.model.pojo;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;


@Entity
public class Loan {

    @Id
    @Column (name = "bookISBN")
    private String bookISBN;

    @Column (name = "userID")
    private int userID;

    @Column (name = "loanStart")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate loanStart;

    @Column (name = "loanEnd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate loanEnd;

    public Loan() {}

    public Loan(String bookID, int userID) {
        this.bookISBN = bookID;
        this.userID = userID;
        this.loanStart = new LocalDate();
        this.loanEnd = this.loanStart.plusDays(30);
    }
}
