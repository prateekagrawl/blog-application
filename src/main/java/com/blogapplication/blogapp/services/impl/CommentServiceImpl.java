package com.blogapplication.blogapp.services.impl;

import com.blogapplication.blogapp.entity.Comment;
import com.blogapplication.blogapp.entity.Post;
import com.blogapplication.blogapp.exceptions.ResourceNotFoundException;
import com.blogapplication.blogapp.payloads.CommentDto;
import com.blogapplication.blogapp.repository.CommentRepo;
import com.blogapplication.blogapp.repository.PostRepo;
import com.blogapplication.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);

        comment.setContent(commentDto.getContent());
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));

        this.commentRepo.delete(comment);
    }
}
