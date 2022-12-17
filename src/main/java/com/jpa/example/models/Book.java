package com.jpa.example.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table( name = "Book" )
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="Id")
    public String Id;

    @Column(name="Title", unique = true)
    @Size(max = 150)
    public String Title;

    @Column(name="PubDate", columnDefinition = "DATE")
    public LocalDate PubDate;

    @ManyToOne //(targetEntity = Author.class)
    //@JoinColumn(name = "AuthorId")
    public Author Author;

    // public Author getAuthor() {
    //     return Author;
    // }

    // public void setAuthor(Author author) {
    //     Author = author;
    // }

    public Book() {}

    @Override
    public String toString () {

        return "Book{ Id='" + Id + "', Title='" + Title + "', PubDate='" + PubDate  + "' }";

    }

    // public Book(String title, Instant pubDate, Author author) {
    //     this.title = title;
    //     this.pubDate = pubDate;
    //     this.author = author;
    // }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getTitle() {
    //     return title;
    // }

    // public void setTitle(String title) {
    //     this.title = title;
    // }

    // public Instant getPubDate() {
    //     return pubDate;
    // }

    // public void setPubDate(Instant pubDate) {
    //     this.pubDate = pubDate;
    // }

    // public Author getAuthor() {
    //     return author;
    // }

    // public void setAuthor(Author author) {
    //     this.author = author;
    // }
}