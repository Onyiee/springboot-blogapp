package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repository.PostRepository;
import com.blogapp.service.cloud.CloudStorageService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class PostServiceImpl implements PostService{
    @Autowired
    CloudStorageService cloudStorageService;

    @Autowired
    PostRepository postRepository;

    @Override
    public Post savePost(PostDto postDto) throws PostObjectIsNullException {
        if (postDto == null){
            throw new PostObjectIsNullException("Post cannot be null");
        }

        Post post = new Post();

        if (postDto.getImageFile() != null && !postDto.getImageFile().isEmpty()){

           try {Map<?, ?> uploadResult =
               cloudStorageService.uploadImage(postDto.getImageFile(),
                   ObjectUtils.asMap(
                           "public_id", "blogapp/" +
                                   extractFileName(Objects.requireNonNull(postDto.getImageFile().getOriginalFilename())),
                           "overwrite", true
                   ));
               post.setCoverImageUrl((String) uploadResult.get("url"));
           }catch (IOException e){
               e.printStackTrace();
           }
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        log.info("Post object before saving --> {}", post);

        try {
            return postRepository.save(post);
        }catch (DataIntegrityViolationException ex){
            log.info("Exception occurred -->{}", ex.getMessage());
            throw ex;
        }

    }
    private String extractFileName(String filename) {
        return filename.split("\\.")[0];
    }
    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }
    @Override
    public List<Post> findAllPostsInDescendingOrder() {
        return postRepository.findByOrderByDateCreatedDesc();
    }

    @Override
    public Post updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public Post findById(Integer id) {
        return null;
    }

    @Override
    public void deletePostById(Integer id) {

    }

    @Override
    public Post addCommentToPost(Integer id, Comment comment) {
        return null;
    }
}
