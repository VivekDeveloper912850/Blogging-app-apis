package com.blog.payloads;

import com.blog.entities.Category;
import com.blog.entities.Comments;
import com.blog.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {

    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;

    private Set<CommentDto> comments = new HashSet<>();

}
