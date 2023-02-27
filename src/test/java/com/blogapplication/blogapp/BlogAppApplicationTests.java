package com.blogapplication.blogapp;

import com.blogapplication.blogapp.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	void repoTest(){
		String className = this.userRepo.getClass().getName();
		System.out.println(className);
	}
}
