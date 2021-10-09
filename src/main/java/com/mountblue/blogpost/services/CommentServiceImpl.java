package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Comment;
import org.springframework.stereotype.Service;
import com.mountblue.blogpost.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
}