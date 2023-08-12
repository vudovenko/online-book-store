package com.example.MyBookShopApp.data.author.repositories;

import com.example.MyBookShopApp.data.author.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
