package com.example.MyBookShopApp.data.book.dto;

import com.example.MyBookShopApp.data.book.entities.Book;
import lombok.Data;

import java.util.List;
// todo проверить везде наличие Lombok аннотаций
@Data
public class BooksPageDto {

    private Integer count;
    private List<Book> books;

    public BooksPageDto(List<Book> books) {
        this.books = books;
        this.count = books.size();
    }
}
