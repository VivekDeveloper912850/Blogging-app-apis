package com.blog;

import com.blog.config.AppConstants;
import com.blog.entities.Roles;
import com.blog.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
//import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
public void run(String... args) throws Exception {
	System.out.println(this.passwordEncoder.encode("testing123"));

		try {

			Roles role = new Roles();
			role.setId(AppConstants.ADMIN_USER);
			role.setNames("ROLE_ADMIN");

			Roles role1 = new Roles();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setNames("ROLE_NORMAL");

			List<Roles> roles = List.of(role, role1);

			List<Roles> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getNames());
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}
}
