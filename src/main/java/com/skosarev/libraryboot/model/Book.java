package com.skosarev.libraryboot.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "title")
    @NotEmpty(message = "Book title should not be empty")
    @Size(min = 1, max = 100, message = "Book title should be between 1 and 100 characters")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Book author should not be empty")
    @Size(min = 5, max = 100, message = "Book author should be between 5 and 100 characters")
    private String author;

    @Column(name = "year")
    @NotNull(message = "Book year should not be empty")
    @Min(value = 1500, message = "Year should be greater than 1500")
    private int year;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private Date takenAt;

    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(Person person, String title, String author, int year) {
        this.person = person;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        long diff = (new Date()).getTime() - takenAt.getTime();
        expired = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) >= 10;
        return expired;
    }
}
