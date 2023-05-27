package com.blogapplication.blogapp.services.impl;

import com.blogapplication.blogapp.entity.Category;
import com.blogapplication.blogapp.entity.Post;
import com.blogapplication.blogapp.entity.User;
import com.blogapplication.blogapp.exceptions.ResourceNotFoundException;
import com.blogapplication.blogapp.payloads.PostDto;
import com.blogapplication.blogapp.payloads.PostResponse;
import com.blogapplication.blogapp.repository.CategoryRepo;
import com.blogapplication.blogapp.repository.PostRepo;
import com.blogapplication.blogapp.repository.UserRepo;
import com.blogapplication.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        Post savedPost = this.postRepo.save(post);
        return this.modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                       .orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id", postId));

        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        //operation for sorting in ascending or descending order depending upon the option selected
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();

        //logic to implement pagination
        Pageable p = PageRequest.of(pageNumber,pageSize, sort); //get Pageable objects
        Page<Post> pagePost = this.postRepo.findAll(p); //contains all the information related to posts and page

        List<Post> allPosts = pagePost.getContent(); //get content of that specific page

        List<PostDto> postsDto = allPosts.stream()
                .map(post->this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());

        //return all the content as a PostResponse
        PostResponse postResponse = new PostResponse();

        //set page response
        postResponse.setContent(postsDto); //all posts are present in content variable
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getSinglePostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));

        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Post> postsOfCategory = this.postRepo.findByCategory(category);

        List<PostDto> postDtos = postsOfCategory.stream()
                .map(post -> this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));

        List<Post> postsByUser = this.postRepo.findByUser(user);

        List<PostDto> postsDtoByUser = postsByUser.stream()
                                        .map(post -> this.modelMapper.map(post,PostDto.class))
                                        .collect(Collectors.toList());

        return postsDtoByUser;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%"); //% is added for querying as a like operator

        List<PostDto> postDto = posts.stream().map(post->this.modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        return postDto;
    }

}
