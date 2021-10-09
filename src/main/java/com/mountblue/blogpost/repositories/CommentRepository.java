package com.mountblue.blogpost.repositories;

import java.util.List;
import com.mountblue.blogpost.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

        @Query("SELECT c from Comment c WHERE c.postId=?1")
        List<Comment> getCommentsByPostId(int postId);
}
