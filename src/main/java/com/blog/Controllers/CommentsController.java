package com.blog.Controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.ServiceComments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/")
public class CommentsController {
    @Autowired
    private ServiceComments serviceComments;

    @PostMapping("/post/{commentId}/comments")
    public ResponseEntity<CommentDto> createComments(@RequestBody CommentDto commentDto , @PathVariable Integer commentId) {
        CommentDto commentDto1 = this.serviceComments.createComments(commentDto, commentId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }
    // delete the comments
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComments(@PathVariable Integer commentId){
        this.serviceComments.deleteComments(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfull!!" ,true) , HttpStatus.OK);
    }
}
