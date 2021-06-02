package com.blogapp.service.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

//    @Test
//    void uploadImage(){
//        File file = new File("C:\\Users\\USER\\Desktop\\blogapp\\src\\main\\resources" +
//                "\\static\\asset\\images\\blog-image1.jpg");
//        assertThat(file.exists()).isTrue();
//        Map<Object, Object> params = new HashMap<>();
//        params.put("public_id","blogapp");
//        params.put("overwrite", "true");
//        params.put("folder", "blogapp");
//        try {
//            cloudStorageServiceImpl.uploadImage(file,params);
//        }catch (IOException e){
//            log.error("An error has occurred-->{}", e.getMessage());
//        }
//    }
}