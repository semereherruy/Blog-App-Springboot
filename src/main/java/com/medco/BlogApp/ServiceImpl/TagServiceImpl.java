package com.medco.BlogApp.ServiceImpl;

import com.medco.BlogApp.Entity.Tag;
import com.medco.BlogApp.Repository.TagRepository;
import com.medco.BlogApp.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag){
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
}
