package com.blogapplication.blogapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(length = 10000)
    private String content;

    private String imageName;

    private Date addedDate;

    //many posts in one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; //used to get which category has added the post

    @ManyToOne
    private User user;

}
