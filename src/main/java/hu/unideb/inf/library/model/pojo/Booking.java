package hu.unideb.inf.library.model.pojo;

import java.time.LocalDateTime;

public class Booking {
    private int bookID;

    private int userID;

    private LocalDateTime bookingStart;

    public Booking() {}

    public Booking(int bookID, int userID, LocalDateTime bookingStart) {
        this.bookID = bookID;
        this.userID = userID;
        this.bookingStart = bookingStart;
    }

    public int getBookID() {
        return bookID;
    }

    public int getUserID() {
        return userID;
    }

    public LocalDateTime getBookingStart() {
        return bookingStart;
    }
}
