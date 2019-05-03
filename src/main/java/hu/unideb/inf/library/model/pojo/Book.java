package hu.unideb.inf.library.model.pojo;

import java.time.LocalDateTime;
import java.util.List;

public class Book {
    private int id;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    private LocalDateTime releaseDate;

    private int pages;

    private List<String> subjects;

    private String storageSign;

    public Book(){}

    public Book(String isbn, String title, String author, String publisher, LocalDateTime releaseDate, int pages, List<String> subjects, String storageSign) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.subjects = subjects;
        this.storageSign = storageSign;
    }

    public int getId() {
        return id;
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

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
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
