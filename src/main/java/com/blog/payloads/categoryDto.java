package com.blog.payloads;

//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class categoryDto {

    private  Integer categoryId;

    private String categoryTitle;
//    @NotEmpty
//    @Size(min = 10)
    private String categoryDescription;
}
