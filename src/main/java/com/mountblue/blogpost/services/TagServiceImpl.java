package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Tag;
import com.mountblue.blogpost.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements  TagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> saveAllTags(List<Tag> tags) {

        return tagRepository.saveAll(tags);
    }

    @Override
    public boolean checkTagWithName(String tagName) {
        Tag tag = tagRepository.findTagWithName(tagName);
        if(tag != null){
            return true;
        }
        return false;
    }

    @Override
    public Tag getTagByName(String name) {
         return tagRepository.findTagWithName(name);
    }
}