package com.example.MyBookShopApp.data.book.repositories;

import com.example.MyBookShopApp.data.book.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select b\n" +
            "from Book b\n" +
            "\tjoin Book2AuthorEntity b2a \n" +
            "\ton b.id = b2a.bookId\n" +
            "\t\tjoin Author a\n" +
            "\t\ton a.id = b2a.authorId\n" +
            "where LOWER(a.firstName) like CONCAT('%', LOWER(:authorsFirstName), '%')")
    List<Book> findBooksByAuthorsWhoseFirstNameContains(String authorsFirstName);

    List<Book> findBooksByTitleContainingIgnoreCase(String bookTitle);

    List<Book> findBooksByPriceOldBetween(int min, int max);

    List<Book> findBooksByPriceOldIs(int price);

    @Query("from Book where isBestseller = 1")
    List<Book> getBestsellers();

    @Query("from Book b where b.price = (select MAX(b2.price) from Book b2)")
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    Page<Book> findBooksByOrderByPubDateDesc(Pageable nextPage);

    Page<Book> findBooksByPubDateAfterOrderByPubDateDesc(Date from, Pageable nextPage);

    Page<Book> findBooksByPubDateBeforeOrderByPubDateDesc(Date to, Pageable nextPage);

    Page<Book> findBookByPubDateBetweenOrderByPubDateDesc(Date from, Date to, Pageable nextPage);
}
