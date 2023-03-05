package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BookService {

    private final JdbcTemplate jdbcTemplate;
    private final AuthorService authorService;

    Logger logger = Logger.getLogger(AuthorService.class.getName());

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate, AuthorService authorService) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorService = authorService;
    }

    public List<Book> getBooksData() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(getAuthorByAuthorId(rs.getInt("author_id")));
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getInt("price_old"));
            book.setPrice(rs.getInt("price"));
            return book;
        });
        return new ArrayList<>(books);
    }

    private String getAuthorByAuthorId(int authorId) {
        List<Author> authors = jdbcTemplate.query("SELECT * FROM authors WHERE authors.id=" + authorId,
                (ResultSet rs, int rowNum) -> {
                    Author author = new Author();
                    author.setId(rs.getInt("id"));
                    author.setFirstName(rs.getString("first_name"));
                    author.setLastName(rs.getString("last_name"));
                    return author;
                });
        return authors.get(0).toString();
    }
}
