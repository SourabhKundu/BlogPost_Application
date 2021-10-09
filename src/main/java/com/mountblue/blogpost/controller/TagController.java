//package com.mountblue.blogpost.controller;
//
//import com.mountblue.blogpost.bean.Comment;
//import com.mountblue.blogpost.services.CommentService;
//import com.mountblue.blogpost.services.TagService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.sql.Timestamp;
//import java.time.Instant;
//
//@Controller
//public class TagController {
//
//    @Autowired
//    private PostsController posts;
//
//    @Autowired
//    private TagService tagService;
//
//
//}
//
////
////List<Tag> listOfTags = new ArrayList<>();
////        String[] tags1 = tags.split(" ");
////        for(String tagByUser : tags1){
////        Tag tag = new Tag();
////        tag.setCreatedAt(timestamp);
////        tag.setUpdatedAt(timestamp);
////        tag.setName(tagByUser);
////
////        listOfTags.add(tag);
////        }
////
////        List<Tag> newSavedTags = tagService.saveAllTags(listOfTags);
////        Post savedPost = postService.savePost(post);
////        postTagsService.savePostTags(newSavedTags, savedPost);
//List<PostTags> postTags = new ArrayList<>();
//        Timestamp timestamp = Timestamp.from(Instant.now());
//
//        for(Tag tag: tags){
//        PostTags newPostTags = new PostTags();
//        newPostTags.setPostId(post.getId());
//        newPostTags.setTagId(tag.getId());
//        newPostTags.setCreatedAt(timestamp);
//        newPostTags.setUpdatedAt(timestamp);
//
//        postTags.add(newPostTags);
//        }
//        return postTagsRepo.saveAll(postTags);
//                }