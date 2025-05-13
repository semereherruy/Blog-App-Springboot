package com.medco.BlogApp.Controller;

import com.medco.BlogApp.Entity.Tag;
import com.medco.BlogApp.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    public Tag createTag(@RequestBody Tag tag){
        return tagService.createTag(tag);
    }

    @GetMapping
    public List<Tag> getAllTags(){
        return tagService.getAllTags();
    }
}
