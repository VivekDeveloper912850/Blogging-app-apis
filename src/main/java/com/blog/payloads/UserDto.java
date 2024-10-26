package com.blog.payloads;

//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
//    @NotEmpty
//    @Size(min = 3  , message = "Username must be 3 characters !!")
    private String name;
//    @Email(message = "Email Address is not valid")
    private String email;
//    @NotEmpty
//    @Size(min = 4 , max = 10 , message = "Password must be min of 4 chars and max of 10 chars !")
    private String password;
//    @NotEmpty
    private String about;
}
