package com.blogapp.data.repository;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void savePostToDBTest(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Praesent sit amet magna sed lorem pellentesque lacinia. titor eu.");
        log.info("Created a blog post--> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwException(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Praesent sit amet magna sed lorem pellentesque lacinia. titor eu.");
        log.info("Created a blog post--> {}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();

        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Praesent sit amet magna sed lorem pellentesque lacinia. titor eu.");
        log.info("Created a blog post--> {}", blogPost2);
        postRepository.save(blogPost);
        assertThrows(DataIntegrityViolationException.class, ()-> postRepository.save(blogPost2));
    }

    @Test
    void whenPostIsSaved_thenSaveAuthor(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Praesent sit amet magna sed lorem pellentesque lacinia. titor eu.");
        log.info("Created a blog post--> {}", blogPost);
        Author author = new Author();
        author.setFirstname("John");
        author.setLastname("Wick");
        author.setEmail("john@.mail.com");
        author.setPhoneNumber("07068080740");

        //map relationships
        blogPost.setAuthor(author);
        author.addPost(blogPost);
        postRepository.save(blogPost);
        log.info("Blog post after saving --> {}", blogPost);

        Post savedPost = postRepository.findByTitle("What is Fintech?");
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo("What is Fintech?");
        assertThat(savedPost.getAuthor()).isNotNull();

    }

    @Test
    void findAllPostsInTheDBTest(){
        List<Post> existingPosts = postRepository.findAll();
        assertThat(existingPosts).isNotNull();
        assertThat(existingPosts).hasSize(5);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deletePostTest(){
        Post savedPost = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("post fetched from database --> {}", savedPost);
        //delete post
        postRepository.deleteById(41);

        Post deletedPost = postRepository.findById(41).orElse(null);
        assertThat(deletedPost).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateSavedPostTest(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Praesent sit amet magna sed lorem pellentesque lacinia. titor eu.");
        log.info("Created a blog post--> {}", blogPost);
       Post test =  postRepository.save(blogPost);

       Post postToUpdate = postRepository.findById(46).orElse(null);
       assertThat(postToUpdate).isNotNull();

       postToUpdate.setTitle("How to read");
       postRepository.save(postToUpdate);
       Post updatedPost = postRepository.findById(46).orElse(null);
       assertThat(updatedPost).isNotNull();
       assertThat(updatedPost.getTitle()).isEqualTo("How to read");

    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updatePostAuthorTest(){

        Post postToUpdate = postRepository.findById(45).orElse(null);
        assertThat(postToUpdate).isNotNull();
        assertThat(postToUpdate.getAuthor()).isNull();

        log.info("saved post object --> {}", postToUpdate);

        Author author = new Author();
        author.setFirstname("Aisha");
        author.setLastname("Ben");
        author.setPhoneNumber("07088992815");
        author.setEmail("aisha@mail.com");
        author.setProfession("Writer");

        postToUpdate.setAuthor(author);
        postRepository.save(postToUpdate);

        Post updatedPost = postRepository.findById(postToUpdate.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
        assertThat(updatedPost.getAuthor().getFirstname()).isEqualTo("Aisha");

    }

    @Test
    @Transactional
    @Rollback(value = false)
    void addCommentToExistingPost() {

        Post savedPost = postRepository.findById(45).orElse(null);
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getComments()).hasSize(0);

        //Create a comment object
        Comment comment1 = new Comment("Peter", "This is new stuff");
        Comment comment2 = new Comment("Billy", "This is great stuff");

        //Map the post and comments
        savedPost.addComment(comment1, comment2);
        // Save the post
        postRepository.save(savedPost);

        Post commentedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(commentedPost).isNotNull();
        assertThat(commentedPost.getComments()).hasSize(2);
        log.info("commented post --> {}", commentedPost);
    }

    @Test
    void findAllPostInDescendingOrder(){
        List<Post> allPosts = postRepository.findByOrderByDateCreatedDesc();
        allPosts.forEach(post -> log.info("post date --> {}", post.getDateCreated()));
        assertTrue(allPosts.get(0).getDateCreated().isAfter(allPosts.get(1).getDateCreated()));
        assertThat(allPosts).isNotEmpty();
    }


}