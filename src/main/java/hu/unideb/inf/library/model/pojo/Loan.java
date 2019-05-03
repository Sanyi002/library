package hu.unideb.inf.library.model.pojo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.time.LocalDateTime;

public class Loan {
    private int bookID;
    private int userID;
    private LocalDate loanStart;
    private LocalDate loanEnd;

    public Loan() {}

    public Loan(int bookID, int userID, LocalDateTime loanEnd) {
        this.bookID = bookID;
        this.userID = userID;
        this.loanStart = new LocalDate();
        this.loanEnd = this.loanStart.plusDays(30);
    }

    public int getBookID() {
        return bookID;
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
