package com.zemoso.checkr.repository;

import com.zemoso.checkr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   public User findByEmail(String email);
}
