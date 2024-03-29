package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.dto.BooksPageDto;
import com.example.MyBookShopApp.data.book.entities.Book;
import com.example.MyBookShopApp.data.book.services.BookService;
import com.example.MyBookShopApp.data.book.services.BooksRatingAndPopularityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Api(description = "book data api")
public class BooksRestApiController {

    private final BookService bookService;
    private final BooksRatingAndPopularityService booksRatingAndPopularityService;

    @Autowired
    public BooksRestApiController(BookService bookService,
                                  BooksRatingAndPopularityService booksRatingAndPopularityService) {
        this.bookService = bookService;
        this.booksRatingAndPopularityService = booksRatingAndPopularityService;
    }

    @GetMapping("/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(authorName));
    }

    @GetMapping("/by-title")
    @ApiOperation("get books by book title")
    public ResponseEntity<List<Book>> booksByTitle(@RequestParam("title") String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/by-price-range")
    @ApiOperation("get book by price range from min price to max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@RequestParam("min") Integer min,
                                                      @RequestParam("max") Integer max) {
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
    }

    @GetMapping("/with-max-discount")
    @ApiOperation("get list of book with max price")
    public ResponseEntity<List<Book>> maxDiscountBooks() {
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    @GetMapping("/bestsellers")
    @ApiOperation("get bestseller books (which is_bestseller = 1)")
    public ResponseEntity<List<Book>> bestSellerBooks() {
        return ResponseEntity.ok(bookService.getBestsellers());
    }

    @GetMapping("/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping("/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam(value = "from", required = false) String from,
                                           @RequestParam(value = "to", required = false) String to,
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.
                getBooksByDatesBetween(from, to, offset, limit).getContent());
    }

    @GetMapping("/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit) {
        return new BooksPageDto(booksRatingAndPopularityService.getPopularBooks(offset, limit).getContent());
    }
}
