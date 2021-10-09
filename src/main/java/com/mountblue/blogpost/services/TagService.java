package com.mountblue.blogpost.services;

import com.mountblue.blogpost.model.Tag;

import java.util.List;

public interface TagService {

    void saveTag(Tag tag);

    List<Tag> getAllTags();

    List<Tag> saveAllTags(List<Tag> tags);

    boolean checkTagWithName(String tagName);

    Tag getTagByName(String Name);


}
