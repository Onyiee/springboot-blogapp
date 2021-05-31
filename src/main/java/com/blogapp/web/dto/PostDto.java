package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

import javax.persistence.Column;
@Data
public class PostDto {
    @NotNull(message = "title cannot be empty")
    private String title;


    private String content;

    private MultipartFile imageFile;



}
