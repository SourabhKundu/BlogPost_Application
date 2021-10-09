package com.mountblue.blogpost.services;

import com.mountblue.blogpost.repositories.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagServiceImpl implements PostTagService{

    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    public void deleteTagByPostId(int id) {
        postTagRepository.deleteTagByPostId(id);
    }
}
