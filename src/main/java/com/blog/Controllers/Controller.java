package com.blog.Controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.serviceUser;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class Controller {
    @Autowired
    private serviceUser userS;
    // Post - create the users
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser( @RequestBody UserDto userD){
       UserDto createDto = this.userS.createUser(userD);
       return  new ResponseEntity<>(createDto , HttpStatus.CREATED);
    }
    // Put - update user
    @PutMapping("/{userId}")
     public ResponseEntity<UserDto> updateUser( @RequestBody UserDto userD , @PathVariable Integer userId){
        UserDto UpdateUserDto = this.userS.updateUser(userD , userId);
        return  ResponseEntity.ok(UpdateUserDto);
     }
    // delete - delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userS.deleteUser(userId);
        return new ResponseEntity( new ApiResponse("User Deleted Successfully" , true), HttpStatus.OK);

    }
        // get - get user
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userS.getAllUsers());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleDto(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userS.getUserById(userId));
    }

}
