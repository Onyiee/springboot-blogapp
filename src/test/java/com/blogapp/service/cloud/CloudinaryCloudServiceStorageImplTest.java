package com.blogapp.service.cloud;

import com.blogapp.web.dto.PostDto;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CloudinaryCloudServiceStorageImplTest {

    @Autowired
    CloudStorageService cloudStorageServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void uploadImage(){
        File file = new File("C:\\Users\\USER\\Desktop\\blogapp\\src\\main\\resources" +
                "\\static\\asset\\images\\blog-image1.jpg");
        assertThat(file.exists()).isTrue();
        Map<Object, Object> params = new HashMap<>();
        params.put("public_id","blogapp");
        params.put("overwrite", "true");
        params.put("folder", "blogapp");
        try {
            cloudStorageServiceImpl.uploadImage(file,params);
        }catch (IOException e){
            log.error("An error has occurred-->{}", e.getMessage());
        }
    }

    @Test
    void uploadMultipartImageFile(){
        File file = new File("C:\\Users\\USER\\Desktop\\blogapp\\src\\main\\resources" +
                "\\static\\asset\\images\\blog-image1.jpg");
        Map<Object, Object> params = new HashMap<>();
        assertThat(file.exists()).isTrue();
        params.put("public_id", UUID.randomUUID().toString());
        params.put("folder", "blogapp");
        try {
            Map<?, ?> result = cloudStorageServiceImpl.uploadImage(file, params);
            log.info("returned map --> {}", result);
        }catch (IOException e){
            log.error("An error occurred-->{}", e.getMessage());
        }
    }

    @Test
    void uploadMultipartImageFileTest() throws IOException {
        PostDto postDto = new PostDto();
        postDto.setTitle("A post");
        postDto.setContent("Some content");

        Path path = Paths.get("C:\\Users\\USER\\Desktop\\writing_images\\amador-loureiro-BVyNlchWqzs-unsplash.jpg");
        MultipartFile multipartFile = new MockMultipartFile("images.jpeg","images.jpeg","img/jpeg",
                Files.readAllBytes(path));

        log.info("Multipart Object created --> {} ", multipartFile);
        assertThat(multipartFile).isNotNull();
        postDto.setImageFile(multipartFile);

        log.info("File name --> {}", postDto.getImageFile().getOriginalFilename());

        cloudStorageServiceImpl.uploadImage(multipartFile, ObjectUtils.asMap(
           "public_id", "blogapp/" + extractFileName(Objects.requireNonNull(postDto.getImageFile().getOriginalFilename()))
        ));

    }

    private String extractFileName(String filename){
        return  filename.split("\\.")[0];
    }
}