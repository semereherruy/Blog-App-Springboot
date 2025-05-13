package com.medco.BlogApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medco.BlogApp.Entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    Optional<Tag> findByKeyword(String keyword);
}
