package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.User;
import com.mountblue.blogpost.services.CommentService;
import com.mountblue.blogpost.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class CommentController {

    @Autowired
    private PostController postController;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/comment")
    public String saveComment(@RequestParam("comment") String data,
                              @RequestParam("id") String postId,
                              @RequestParam("name") String name,
                              Model model){
        Timestamp time = Timestamp.from(Instant.now());
        User user = userServiceImpl.getCurrentUser();

        int id = Integer.parseInt(postId);
        Comment comment = new Comment();
        comment.setComment(data);
        comment.setName(name);
        comment.setCreatedAt(time);
        comment.setPostId(id);
        comment.setEmail(user.getEmail());
        comment.setUpdatedAt(time);

        commentService.saveComment(comment);

        return postController.showPostByID(id, model);
    }

    @GetMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable("commentId") int commentId,
                                Model model){
        this.commentService.deleteCommentByPostId(commentId);
        return "redirect:/";
    }

//    @PostMapping("/update/{id}")
//    public String updateComment(@RequestParam("title") String title,
//                             @PathVariable("id") int id,
//                             @RequestParam("content") String content,
////                             @RequestParam("tags") String tags,
//                             Model model){
//        Timestamp time = Timestamp.from(Instant.now());
//
//        Post post = postService.getPostById(id);
//        User user = userServiceImpl.getCurrentUser();
//
//        post.setTitle(title);
//        post.setContent(content);
//        post.setAuthor(user.getName());
//        post.setUpdatedAt(time);
//        post.setExcerpt(content.substring(0));
//
//        postService.savePost(post);
//        return "post";
//    }
}
