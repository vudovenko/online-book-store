package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class GenresPageController {

    @GetMapping("/genres")
    public String genresPage() {
        return "genres/index";
    }
}
