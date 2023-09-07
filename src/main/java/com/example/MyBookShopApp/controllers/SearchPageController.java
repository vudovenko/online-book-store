package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.dto.BooksPageDto;
import com.example.MyBookShopApp.data.book.dto.SearchWordDto;
import com.example.MyBookShopApp.data.book.entities.Book;
import com.example.MyBookShopApp.data.book.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchPageController {

    private final BookService bookService;

    @Autowired
    public SearchPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false)
                                   SearchWordDto searchWordDto,
                                   Model model) {
        if (searchWordDto != null) {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults",
                    bookService.getPageSearchResultBooks(searchWordDto.getExample(), 0, 5).getContent());
        }
        return "/search/index";
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false)
                                          SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }
}
