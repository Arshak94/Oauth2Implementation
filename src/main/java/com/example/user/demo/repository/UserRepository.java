package com.example.user.demo.repository;

import com.example.user.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    public User findById(Long id);
}
