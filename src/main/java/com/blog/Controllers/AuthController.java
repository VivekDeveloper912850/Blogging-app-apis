package com.blog.Controllers;//package com.blog.Controllers;

import com.blog.Security.JWTAuthResponse;
import com.blog.Security.JWTTokenHelper;
import com.blog.entities.User;
import com.blog.exceptions.ApiException;
import com.blog.payloads.JWTAuthRequest;
import com.blog.payloads.UserDto;
import com.blog.services.serviceUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//
//import com.blog.Security.JWTAuthResponse;
//import com.blog.Security.JWTTokenHelper;
//import com.blog.entities.User;
//import com.blog.exceptions.ApiException;
//import com.blog.payloads.JWTAuthRequest;
//import com.blog.payloads.UserDto;
//import com.blog.services.serviceUser;
//import org.modelmapper.ModelMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth/")
//public class AuthController {
//
//
//    @Autowired
//    private JWTTokenHelper jwtTokenHelper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private serviceUser userService;
//    @Autowired
//    private ModelMapper mapper;
//
//    @PostMapping("/login")
//    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
//        this.authenticate(request.getEmail(), request.getPassword());
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//        JWTAuthResponse response = new JWTAuthResponse();
//        response.setToken(token);
//        response.setUser(this.mapper.map((User) userDetails, UserDto.class));
//        return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
//                password);
//
//        try {
//
//            this.authenticationManager.authenticate(authenticationToken);
//
//        } catch (BadCredentialsException e) {
//            System.out.println("Invalid Detials !!");
//            throw new ApiException("Invalid username or password !!");
//        }
//
//    }
//    @ExceptionHandler(BadCredentialsException.class)
//    public String exceptionHandler() {
//        return "Credentials Invalid !!";
//    }
//
//}
@RestController
@RequestMapping("/api/auth/")
public class AuthController{
    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private serviceUser userService;
    @Autowired
    private ModelMapper mapper;


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {
        this.authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtTokenHelper.generateToken(userDetails);

        JWTAuthResponse response = new JWTAuthResponse();
        response.setToken(token);
        response.setUser(this.mapper.map((User) userDetails, UserDto.class));
        System.out.println(response.getToken());
        return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {

            this.authenticationManager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new ApiException("Invalid username or password !!");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser( @RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }



}
