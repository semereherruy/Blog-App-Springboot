package com.medco.BlogApp.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import com.medco.BlogApp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
