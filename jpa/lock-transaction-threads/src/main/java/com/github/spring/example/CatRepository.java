package com.github.spring.example;

import com.github.spring.example.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CatRepository extends JpaRepository<Cat, UUID> {
}