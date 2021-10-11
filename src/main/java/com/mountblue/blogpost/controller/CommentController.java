package com.mountblue.blogpost.controller;

import com.mountblue.blogpost.model.Comment;
import com.mountblue.blogpost.model.User;
import com.mountblue.blogpost.services.CommentService;
import com.mountblue.blogpost.services.UserService;
import com.mountblue.blogpost.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void modelAttribute(Model model) {
        model.addAttribute("sessionUser", userService.findUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()));
        model.addAttribute("admin", hasRole("ROLE_ADMIN"));
    }

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
                                @RequestParam("postId") int postId,
                                Model model){
        this.commentService.deleteCommentByPostId(commentId);
        return postController.showPostByID(postId, model);
    }

    @GetMapping("/showCommentUpdate/{commentId}")
    public String showFormForUpdate(@PathVariable(value = "commentId") int id,
                                    @RequestParam("postId")int postId,Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("editComment", comment);
        return postController.showPostByID(postId, model);
    }

    @PostMapping("/comment/update/{commentId}")
    public String updatePost(@PathVariable("commentId") int id,
                             @RequestParam("comment") String comment,
                             @RequestParam("postId") int postId,
                             Model model) {
        Comment commentObj = commentService.getCommentById(id);
        commentObj.setComment(comment);
        commentService.saveComment(commentObj);
        return postController.showPostByID(postId, model);
    }

    public static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}