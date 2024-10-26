package com.blog.repository;

import com.blog.entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comments , Integer> {
}
