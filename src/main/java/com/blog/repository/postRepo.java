package com.blog.repository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface postRepo extends JpaRepository<Post, Integer> {

    List<Post> findByCategory(Category category);
    List<Post> findByUser(User user);
    List<Post> findByTitleContaining(String title);
}
