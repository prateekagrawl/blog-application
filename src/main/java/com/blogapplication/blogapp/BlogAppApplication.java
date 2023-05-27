package com.blogapplication.blogapp;

import com.blogapplication.blogapp.config.AppConstants;
import com.blogapplication.blogapp.entity.Role;
import com.blogapplication.blogapp.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			System.out.println(this.passwordEncoder.encode("raj12"));
//			String[] beans = applicationContext.getBeanDefinitionNames();
//			Arrays.sort(beans);
//			for (String bean : beans) {
//				System.out.println(bean);
//			}

//			System.out.println(SecurityContextHolder.getContext().getAuthentication());

			//used to create new roles at the start of the application
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ADMIN_USER");

			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");
			List<Role> roles = List.of(role1,role2);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r -> System.out.println(r.getName()));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
