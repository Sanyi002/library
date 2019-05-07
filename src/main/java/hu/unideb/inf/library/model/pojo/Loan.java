package hu.unideb.inf.library.model.pojo;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

// TODO:

//@Entity
public class Loan {

    @Column (name = "bookISBN")
    private int bookISBN;

    @Column (name = "userID")
    private int userID;

    @Column (name = "loanStart")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate loanStart;

    @Column (name = "loanEnd")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate loanEnd;

    public Loan() {}

    public Loan(int bookID, int userID, LocalDateTime loanEnd) {
        this.bookISBN = bookID;
        this.userID = userID;
        this.loanStart = new LocalDate();
        this.loanEnd = this.loanStart.plusDays(30);
    }

    public int getBookISBN() {
        return bookISBN;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDate getLoanStart() {
        return loanStart;
    }

    public LocalDate getLoanEnd() {
        return loanEnd;
    }

    public void setLoanEnd(LocalDate loanEnd) {
        this.loanEnd = loanEnd;
    }
}
