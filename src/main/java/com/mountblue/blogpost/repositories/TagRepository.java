package com.mountblue.blogpost.repositories;

import com.mountblue.blogpost.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    @Query(value = "SELECT * FROM tags t WHERE t.name like ?1",nativeQuery = true)
    Tag findTagWithName(@Param("name") String name);
}
