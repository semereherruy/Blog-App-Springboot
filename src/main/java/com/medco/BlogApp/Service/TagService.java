package com.medco.BlogApp.Service;

import com.medco.BlogApp.Entity.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(Tag tag);
    List<Tag> getAllTags();
}
