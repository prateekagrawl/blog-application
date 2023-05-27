package com.blogapplication.blogapp.services;

import com.blogapplication.blogapp.entity.Category;
import com.blogapplication.blogapp.entity.Post;
import com.blogapplication.blogapp.payloads.PostDto;
import com.blogapplication.blogapp.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getSinglePostById(Integer postId);

    //get all posts by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get all posts by user
    List<PostDto> getPostsByUser(Integer userId);

    //search posts by keyword
    List<PostDto> searchPosts(String keyword);
}
