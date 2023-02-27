package com.blogapplication.blogapp.controllers;

import com.blogapplication.blogapp.config.AppConstants;
import com.blogapplication.blogapp.entity.Post;
import com.blogapplication.blogapp.payloads.ApiResponse;
import com.blogapplication.blogapp.payloads.PostDto;
import com.blogapplication.blogapp.payloads.PostResponse;
import com.blogapplication.blogapp.services.PostService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,@PathVariable Integer categoryId){
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
                                                    @RequestParam(value= "pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value= "sortBy",defaultValue = AppConstants.SORT_BY, required = false)String sortBy,
                                                    @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)   {
        //pageNumber starts from 0
        PostResponse posts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getSinglePostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getSinglePostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto post = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post is deleted successfully!", true);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> postDtoList = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

}
