package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    void savePost(Post post);

    Post getPostById(int id);

    Page<Post> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword);

    void deletePostById(int id);

    List<Integer> getAllPostIdByTagId(List<Integer> tagIds);

    List<Integer> getPostIdByAuthor(List<Long> authorIds);

    List<Post> getAllPostsById(List<Integer> postIds);
}
