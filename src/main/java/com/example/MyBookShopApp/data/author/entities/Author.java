package com.example.MyBookShopApp.data.author.entities;

import com.example.MyBookShopApp.data.book.entities.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH})
    private List<Book> bookList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
