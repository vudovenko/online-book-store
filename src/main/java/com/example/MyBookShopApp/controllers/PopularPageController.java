package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.entities.Book;
import com.example.MyBookShopApp.data.book.services.BookService;
import com.example.MyBookShopApp.data.book.services.BooksRatingAndPopularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class PopularPageController {

    private final BooksRatingAndPopularityService booksRatingAndPopularityService;

    @Autowired
    public PopularPageController(BookService bookService,
                                 BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return booksRatingAndPopularityService.getPopularBooks(0, 20).getContent();
    }

    @GetMapping("/popular")
    public String popularPage() {
        return "/books/popular";
    }
}
