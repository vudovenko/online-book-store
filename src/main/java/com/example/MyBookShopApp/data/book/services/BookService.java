package com.example.MyBookShopApp.data.book.services;

import com.example.MyBookShopApp.data.book.entities.Book;
import com.example.MyBookShopApp.data.book.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorsWhoseFirstNameContains(authorName);
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findBooksByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price) {
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxDiscount() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestsellers() {
        return bookRepository.getBestsellers();
    }

    public Page<Book> getPageRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    public Page<Book> getBooksByDatesBetween(String from, String to,
                                             Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date fromDate = parseDate(from, dateFormat);
        Date toDate = parseDate(to, dateFormat);

        if (fromDate == null && toDate == null) {
            return bookRepository.findBooksByOrderByPubDateDesc(nextPage);
        } else if (fromDate == null) {
            return bookRepository.findBooksByPubDateBeforeOrderByPubDateDesc(toDate, nextPage);
        } else if (toDate == null) {
            return bookRepository.findBooksByPubDateAfterOrderByPubDateDesc(fromDate, nextPage);
        }
        return bookRepository.findBookByPubDateBetweenOrderByPubDateDesc(fromDate, toDate, nextPage);
    }

    private Date parseDate(String date, SimpleDateFormat dateFormat) {
        if (date != null && !date.isEmpty()) {
            try {
                return dateFormat.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public List<Book> getRecentBooksForLastMonth() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String fromDate = startDate.format(formatter);
        String toDate = endDate.format(formatter);

        return getBooksByDatesBetween(fromDate, toDate, 0, 20).getContent();
    }
}
