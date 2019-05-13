package hu.unideb.inf.library.model.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    Book book1 = new Book("978-963", "A boszorkánymester", "Rejtő Jenő", "Alexandra", 2011, 197, "magyar irodalom", "R53");
    Book book2 = new Book("978-963", "A boszorkánymester", "Rejtő Jenő", "Alexandra", 2011, 197, "magyar irodalom", "R53");

    @Test
    public void getIsbn() {
        assertEquals("978-963", book1.getIsbn());
    }

    @Test
    public void setIsbn() {
        book1.setIsbn("111-111");
        assertEquals("111-111", book1.getIsbn());
    }

    @Test
    public void getTitle() {
        assertEquals("A boszorkánymester", book1.getTitle());
    }

    @Test
    public void setTitle() {
        book1.setTitle("A boszorkánymester 2");
        assertEquals("A boszorkánymester 2", book1.getTitle());
    }

    @Test
    public void getAuthor() {
        assertEquals("Rejtő Jenő", book1.getAuthor());
    }

    @Test
    public void setAuthor() {
        book1.setAuthor("Rejtő Jenő Teszt");
        assertEquals("Rejtő Jenő Teszt", book1.getAuthor());
    }

    @Test
    public void getPublisher() {
        assertEquals("Alexandra", book1.getPublisher());
    }

    @Test
    public void setPublisher() {
        book1.setPublisher("Alexandra Teszt");
        assertEquals("Alexandra Teszt", book1.getPublisher());
    }

    @Test
    public void getReleaseDate() {
        assertEquals(2011, book1.getReleaseDate());
    }

    @Test
    public void setReleaseDate() {
        book1.setReleaseDate(2012);
        assertEquals(2012, book1.getReleaseDate());
    }

    @Test
    public void getPages() {
        assertEquals(197, book1.getPages());
    }

    @Test
    public void setPages() {
        book1.setPages(200);
        assertEquals(200, book1.getPages());
    }

    @Test
    public void getSubjects() {
        assertEquals("magyar irodalom", book1.getSubjects());
    }

    @Test
    public void setSubjects() {
        book1.setSubjects("magyar irodalom teszt");
        assertEquals("magyar irodalom teszt", book1.getSubjects());
    }

    @Test
    public void getStorageSign() {
        assertEquals("R53", book1.getStorageSign());
    }

    @Test
    public void setStorageSign() {
        book1.setStorageSign("R54");
        assertEquals("R54", book1.getStorageSign());

    }

    @Test
    public void bookEquals() {
        assertEquals(true, book1.bookEquals(book1, book2));
    }
}