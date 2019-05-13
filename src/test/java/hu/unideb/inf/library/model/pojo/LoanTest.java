package hu.unideb.inf.library.model.pojo;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    Loan loan = new Loan("978-963", 15);

    @Test
    void getBookISBN() {
        assertEquals("978-963", loan.getBookISBN());
    }

    @Test
    void getUserID() {
        assertEquals(15, loan.getUserID());
    }

    @Test
    void getLoanStart() {
        assertEquals(LocalDate.now(), loan.getLoanStart());
    }

    @Test
    void getLoanEnd() {
        assertEquals(LocalDate.now().plusDays(30), loan.getLoanEnd());
    }
}