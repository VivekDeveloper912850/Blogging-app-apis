package com.blog.Security;


import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)  {

        // loading user from database by username
        User user = this.userRepo.findByEmail(username);


        return user;
    }

}
