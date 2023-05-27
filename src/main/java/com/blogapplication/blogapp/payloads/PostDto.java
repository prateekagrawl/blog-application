package com.blogapplication.blogapp.payloads;

import com.blogapplication.blogapp.entity.Category;
import com.blogapplication.blogapp.entity.Comment;
import com.blogapplication.blogapp.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PostDto {

//    private Integer id;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();

}
