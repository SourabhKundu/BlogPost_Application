package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Comment;

public interface CommentService {
    void saveComment(Comment comment);

    void deleteCommentByPostId(int id);

    Comment getCommentById(int id);
}
