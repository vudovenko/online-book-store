package com.example.MyBookShopApp.data.book.entities;

import com.example.MyBookShopApp.data.author.entities.Author;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "books")
@ApiModel(description = "entity representing a book")
public class Book {

    @Id
    @ApiModelProperty("id generated by database automatically")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pub_date")
    @ApiModelProperty("date of book publication")
    private Date pubDate;

    @Column(name = "is_bestseller")
    @ApiModelProperty("if isBestseller = 1 so the book is considered to be bestseller " +
            "and  if 0 the book is not a bestseller")
    private Integer isBestseller;

    @Column(name = "slug")
    @ApiModelProperty("mnemonic identity sequence of characters")
    private String slug;

    @Column(name = "title")
    @ApiModelProperty("book title")
    private String title;

    @Column(name = "image")
    @ApiModelProperty("image url")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    @ApiModelProperty("book description text")
    private String description;

    @Column(name = "price")
    @JsonProperty("price")
    @ApiModelProperty("book price without discount")
    private Integer priceOld;

    @Column(name = "discount")
    @JsonProperty("discount")
    @ApiModelProperty("discount value for book")
    private Double price;

    @Column(name = "number_buys")
    @ApiModelProperty("number of times book was bought")
    private Double numberBuys;

    @Column(name = "basket_quantity")
    @ApiModelProperty("number of times book was added to basket")
    private Double basketQuantity;

    @Column(name = "number_deferred")
    @ApiModelProperty("number of times book was deferred")
    private Double numberDeferred;

    @JsonIgnore
    @ManyToMany(mappedBy = "books",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<Author> authors;

    @JsonIgnore
    @ManyToMany(mappedBy = "books",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<GenreEntity> genres;


    @JsonIgnore
    @ManyToMany(mappedBy = "books",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<UserEntity> users;

    @JsonIgnore
    @ManyToMany(mappedBy = "booksDownloadedByUser",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<UserEntity> usersWhoDownloadedBook;

    @JsonIgnore
    @ManyToMany(mappedBy = "booksBoughtByUser",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<UserEntity> usersWhoBoughtBook;

    @JsonIgnore
    @ManyToMany(mappedBy = "booksReviewedByUser",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<UserEntity> usersWhoReviewedBook;
}
