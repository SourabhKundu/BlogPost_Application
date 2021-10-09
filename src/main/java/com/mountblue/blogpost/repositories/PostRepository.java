package com.mountblue.blogpost.repositories;

import com.mountblue.blogpost.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;


public interface PostRepository extends JpaRepository<Post,Integer> {
    @Transactional
    @Query("SELECT p.id FROM Post p WHERE p.authorId IN (?1)")
    List<Integer> getPostIdsByAuthorIds(int authorId);

    @Query("SELECT p FROM Post p WHERE lower(p.title) LIKE %?1%"
            +" OR lower(p.content) LIKE %?1%"
            +" OR lower(p.author) LIKE %?1%")
    Page<Post> findAll(String keyword, Pageable pageable);


}
