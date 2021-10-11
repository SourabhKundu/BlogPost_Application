package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Comment;
import org.springframework.stereotype.Service;
import com.mountblue.blogpost.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentByPostId(int id) {this.commentRepository.deleteById(id);}

    @Override
    public Comment getCommentById(int id) {
        Optional<Comment> optional = commentRepository.findById(id);
        Comment comment = null;
        if (optional.isPresent()) {
            comment = optional.get();
        } else {
            throw new RuntimeException(" Comment not found for id :: " + id);
        }
        return comment;
    }
}