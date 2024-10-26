package com.blog.repository;

import com.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepo extends JpaRepository<Category , Integer> {
}
