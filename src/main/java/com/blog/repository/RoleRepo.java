package com.blog.repository;

import com.blog.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Roles , Integer> {
}
