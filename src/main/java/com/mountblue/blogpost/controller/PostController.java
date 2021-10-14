package com.mountblue.blogpost.controller;

import java.util.*;
import java.time.Instant;
import java.sql.Timestamp;

import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.model.User;
import com.mountblue.blogpost.services.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.mountblue.blogpost.model.Post;
import com.mountblue.blogpost.model.Comment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.mountblue.blogpost.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private CommentService commentService;

    @ModelAttribute
    public void modelAttribute(Model model) {
        model.addAttribute("sessionUser", userService.findUserByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()));
        model.addAttribute("admin", hasRole("ROLE_ADMIN"));
    }

    @RequestMapping("/search")
    public String showSearchResult(@RequestParam("keyword") String keyword, Model model) {
//        return showHomePage(keyword, model);
        return "search done";
    }

    @RequestMapping("/")
    public List<Post> showHomePage(String keyword) {
        return showPagination(1, "title", "desc", keyword);
    }

    @GetMapping("post/{id}")
    public Post showPostByID(@PathVariable("id") String id, Model model) {
        int ids = Integer.parseInt(id);
        Post post = postService.getPostById(ids);
        model.addAttribute("post", post);
        List<Comment> comments = commentRepository.getCommentsByPostId(ids);
        model.addAttribute("comments", comments);
        User user = userServiceImpl.getCurrentUser();
        model.addAttribute("user", user);

        return post;

//        return "post";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPost(@PathVariable(name = "id") Integer id) {
        ModelAndView editView = new ModelAndView("editPost");
        if (id != null) {
            Post post = postService.getPostById(id);
            post.setId(id);
            editView.addObject("post", post);
        }
        return editView;
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable(value = "id") int id) {
        try {
            postTagService.deleteTagByPostId(id);
            commentService.deleteCommentByPostId(id);
        } catch (Exception e) {
            System.out.println("Unable to delete the post");
        } finally {
            postService.deletePostById(id);
        }
        return "Post Deleted";
//        return "redirect:/";
    }

    @GetMapping("/newPost")
    public String redirect() {

        return "index";
    }

    @GetMapping("/save")
    public String savePost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("tags") String tags,
                           Model model) {
        Timestamp time = Timestamp.from(Instant.now());

        Post post = new Post();
        User user = userServiceImpl.getCurrentUser();

        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(time);
//        post.setAuthor(user.getName());
        post.setPublishedAt(time);
        post.setUpdatedAt(time);
        post.setExcerpt(content.substring(0));
        post.setIsPublished(true);
//        post.setAuthorId(user.getId());

        String[] tagsArray = tags.split(" ");
        List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tagsArray) {
            Tag tagObject = new Tag();

            if (tagService.checkTagWithName(tag)) {
                tagList.add(tagService.getTagByName(tag));
            } else {
                tagObject.setName(tag);
                tagObject.setCreatedAt(time);
                tagObject.setUpdatedAt(time);
                tagList.add(tagObject);
            }
        }
        post.setTags(tagList);
        postService.savePost(post);
        return "Post Saved";
//        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updatePost(@RequestParam("title") String title,
                             @PathVariable("id") int id,
                             @RequestParam("content") String content,
                             @RequestParam("tags") String tags,
                             Model model) {
        Timestamp time = Timestamp.from(Instant.now());
        Post post = postService.getPostById(id);

        post.setTitle(title);
        post.setContent(content);
        post.setUpdatedAt(time);
        post.setExcerpt(content.substring(0));

        String[] tagsArray = tags.split(" ");
        List<Tag> tagList = new ArrayList<Tag>();
        for (String tag : tagsArray) {
            Tag tagObject = new Tag();

            if (tagService.checkTagWithName(tag)) {
                tagList.add(tagService.getTagByName(tag));
            } else {
                tagObject.setName(tag);
                tagObject.setCreatedAt(time);
                tagObject.setUpdatedAt(time);
                tagList.add(tagObject);
            }
        }
        post.setTags(tagList);
        postService.savePost(post);
//        return showPostByID(id, model);
        return "Post Updated";
    }

    @GetMapping("/page/{pageNo}")
    public List<Post> showPagination(@PathVariable("pageNo") int pageNo,
                                 @RequestParam(value = "sortField", defaultValue = "publishedAt") String sortField,
                                 @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                 String keyword) {

        int pageSize = 3;
        List<User> users = userService.getAllUser();
        List<Tag> tags = tagService.getAllTags();
        Page<Post> page = postService.findPaginated(pageNo, pageSize, sortField, sortDirection, keyword);
        List<Post> posts = page.getContent();

        return posts;
//        return "users";
    }

    @GetMapping("/filter")
    public List<Post> getFilteredPosts(@RequestParam(value = "authorId", required = false) Optional<Long> authorId,
                                   @RequestParam(value = "tagId", required = false) Optional<Integer> tagId,
                                   String keyword, Model model) {

        if (authorId.isPresent()) {
            List<Long> authorIds = Collections.singletonList(authorId.get());
            List<Integer> postIdByAuthorId = postService.getPostIdByAuthor(authorIds);
            if (tagId.isPresent()) {
                List<Integer> tagIds = Collections.singletonList(tagId.get());
                List<Integer> postsByTagId = postService.getAllPostIdByTagId(tagIds);
                List<Integer> postIds = new ArrayList<>();
                for (int postId : postsByTagId) {
                    if (postIdByAuthorId.contains(postId)) {
                        postIds.add(postId);
                    }
                }
                List<Post> posts = postService.getAllPostsById(postIds);
                model.addAttribute("posts", posts);
            } else {
                List<Post> posts = postService.getAllPostsById(postIdByAuthorId);
                model.addAttribute("posts", posts);
            }
        } else {
            List<Integer> tagIds = Collections.singletonList(tagId.get());
            List<Integer> postsByTagId = postService.getAllPostIdByTagId(tagIds);
            List<Post> posts = postService.getAllPostsById(postsByTagId);
            model.addAttribute("posts", posts);
        }
        return (List<Post>) model.getAttribute("posts");
//        return showHomePage(keyword, model);
    }

    public static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
}