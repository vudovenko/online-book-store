package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.entities.Book;
import com.example.MyBookShopApp.data.book.services.BookService;
import com.example.MyBookShopApp.data.book.services.BooksRatingAndPopularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final BooksRatingAndPopularityService booksRatingAndPopularityService;

    @Autowired
    public MainPageController(BookService bookService, BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.bookService = bookService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getBooksByDatesBetween("", "", 0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return booksRatingAndPopularityService.getPopularBooks(0, 6).getContent();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
