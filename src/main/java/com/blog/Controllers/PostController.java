package com.blog.Controllers;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.servicePost;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private servicePost serPost;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    // Create Post Apis
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto , @PathVariable Integer userId , @PathVariable Integer categoryId){
         PostDto postDto1 = this.serPost.createPost(postDto, userId, categoryId);
         return new ResponseEntity<PostDto>(postDto1, HttpStatus.CREATED);
    }
    // get by Users
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtos = this.serPost.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos , HttpStatus.OK);
    }
    // get by
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = this.serPost.getPostsByCategory(categoryId);
        return new ResponseEntity<>(posts,HttpStatus.OK);

    }

    // Get All Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER,required = false)
            Integer pageNumber , @RequestParam(value = "pageSize" ,defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY, required = false) String sortBy ,
                                                    @RequestParam(value = "sortDir" , defaultValue =AppConstants.SORT_DIR ,required = false) String sortDir
                                                    ){
        return ResponseEntity.ok(this.serPost.getAllPost(pageNumber , pageSize , sortBy , sortDir));
    }

    // Get Post by Id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return ResponseEntity.ok(this.serPost.getPostById(postId));
    }
    // delete post by id
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId){
        this.serPost.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true),HttpStatus.OK);
    }
    // update the Posts
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId){
        PostDto updatePost = this.serPost.updatePost(postDto , postId);
        return ResponseEntity.ok(updatePost);
    }

    // search post by keyword
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
        List<PostDto> result = this.serPost.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(result , HttpStatus.OK);
    }

    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {PostDto postDto = this.serPost.getPostById(postId);
     String filename1  = this.fileService.uploadImage(path , image);
     postDto.setImageName(filename1);
     PostDto updatePost = this.serPost.updatePost(postDto , postId);
     return new ResponseEntity<>(updatePost , HttpStatus.OK);
    }

    // methods to serve file
    @GetMapping(value = "/post/image/{imageName}" , produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName , HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path , imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource , response.getOutputStream());

    }

}
