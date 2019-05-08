package hu.unideb.inf.library.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Book {

    /**
     * A könyv isbn száma.
     */
    @Id
    private String isbn;

    /**
     * A könyv címe.
     */
    @Column (name = "title")
    private String title;

    /**
     * A könyv szerzője.
     */
    @Column (name = "author")
    private String author;

    /**
     * A könyv kiadója
     */
    @Column (name = "publisher")
    private String publisher;

    /**
     * A könyv megjelenésének éve.
     */
    @Column (name = "releaseDate")
    private int releaseDate;

    /**
     * A könyv terjedelme.
     */
    @Column (name = "pages")
    private int pages;

    /**
     * A könyv tárgyszavai.
     */
    @Column (name = "subjects")
    private String subjects;

    /**
     * A könyv raktári jelzete.
     */
    @Column (name = "storageSign")
    private String storageSign;

    public Book(){}

    /**
     * Konstruktor, amely létrehoz egy Book objektumot.
     * @param isbn a könyv ISBN száma
     * @param title a könyv címe
     * @param author a könyv szerzője
     * @param publisher a könyv kiadója
     * @param releaseDate a könyv megjelenésének éve
     * @param pages a könyv terjedelme
     * @param subjects a könyv tárgyszavai
     * @param storageSign a könyv raktári jelzete
     */
    public Book(String isbn, String title, String author, String publisher, int releaseDate, int pages, String subjects, String storageSign) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.subjects = subjects;
        this.storageSign = storageSign;
    }

    /**
     * Visszaadja a könyv ISBN számát
     * @return könyv ISBN száma
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Beállítja a könyv ISBN számát.
     * @param isbn a könyv ISBN száma
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Visszaadja a könyv címét.
     * @return a könyv címe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Beállítja a könyv címét.
     * @param title a könyv címe
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Visszaadja a könyv szerzőjét.
     * @return a könyv szerzője
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Beállítja a könyv szerzőjét.
     * @param author a könyv szerzője
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Visszaadja a könyv kiadóját.
     * @return a könyv kiadója
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Beállítja a könyv szerzőjét.
     * @param publisher a könyv szerzője
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Visszaadja a könyv megjelenésének évét.
     * @return a könyv megjelenésének éve
     */
    public int getReleaseDate() {
        return releaseDate;
    }

    /**
     * Beállítja a könyv megjelenésének az évét.
     * @param releaseDate a könyv megjelenésének éve
     */
    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Visszaadja a könyv terjedelmét.
     * @return a könyv terjedelme
     */
    public int getPages() {
        return pages;
    }

    /**
     * Beállítja a könyv terjedelmét.
     * @param pages a könyv terjedelme
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * Visszaadja a könyv tárgyszavait.
     * @return a könyv tárgyszavai
     */
    public String getSubjects() {
        return subjects;
    }

    /**
     * Beállítja a könyv tárgyszavait.
     * @param subjects a könyv tárgyszavai
     */
    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    /**
     * Visszaadja a könyv raktári jelzetét.
     * @return a könyv raktári jelzete
     */
    public String getStorageSign() {
        return storageSign;
    }

    /**
     * Beállítja a könyv raktári jelzetét.
     * @param storageSign a könyv raktári jelzete
     */
    public void setStorageSign(String storageSign) {
        this.storageSign = storageSign;
    }

    /**
     * Két könyv adatainak összehasonlítása.
     * Csak akkor add vissza igaz értéket, ha minden adat megegyezik.
     * @param b1 vizsgált könyv egy példánya
     * @param b2 vizsgált könyv egy példánya
     * @return logikai érték
     */
    public boolean bookEquals(Book b1, Book b2) {
        if(b1.getIsbn().equals(b2.getIsbn()) && b1.getTitle().equals(b2.getTitle()) &&
            b1.getAuthor().equals(b2.getAuthor()) && b1.getPublisher().equals(b2.getPublisher()) &&
            b1.getReleaseDate() == b2.getReleaseDate() && b1.getPages() == b2.getPages() &&
            b1.getSubjects().equals(b2.getSubjects()) && b1.getStorageSign().equals(b2.getStorageSign())) {

            return true;
        } else {
            return false;
        }
    }
}
