package com.blogapplication.blogapp.repository;

import com.blogapplication.blogapp.entity.Category;
import com.blogapplication.blogapp.entity.Post;
import com.blogapplication.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    //by default, JPA executes a sql query for searching by title using Like operator. <findBy<fieldName>Containing()
//    List<Post> findByTitleContaining(String title);

    @Query("select p from Post p where p.title like :title")
    List<Post> searchByTitle(String title);  //TO ACHIEVE THE FOLLOWING:select from post where title like "%keyword%"
}
