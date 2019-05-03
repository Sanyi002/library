package hu.unideb.inf.library.model.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> subjects;

    /**
     * A könyv raktári jelzete.
     */
    @Column (name = "storageSign")
    private String storageSign;

    public Book(){}

    public Book(String isbn, String title, String author, String publisher, int releaseDate, int pages, List<String> subjects, String storageSign) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.subjects = subjects;
        this.storageSign = storageSign;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getStorageSign() {
        return storageSign;
    }

    public void setStorageSign(String storageSign) {
        this.storageSign = storageSign;
    }
}
