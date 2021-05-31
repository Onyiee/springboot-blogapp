package com.blogapp.web.controllers;

import com.blogapp.service.post.PostService;
import com.blogapp.web.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
@Slf4j
public class PostController {
    @Autowired
    PostService postServiceImpl;


    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/create")
    public String getPostForm(Model model){
        model.addAttribute("post", new PostDto());
       return "create";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute @Valid PostDto postDto){
        log.info("Post dto received-->{}", postDto);
        return "index";
    }
}
