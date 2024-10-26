package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repository.UserRepo;
import com.blog.repository.categoryRepo;
import com.blog.repository.postRepo;
import com.blog.services.servicePost;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements servicePost {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private postRepo PostRepo;
    @Autowired
    private categoryRepo cateRepo;
    @Autowired
    private UserRepo usersRepo;
    @Override
    public PostDto createPost(PostDto postDto , Integer userId , Integer categoryId) {
      Category  category = this.cateRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
      User updateUser = this.usersRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "userID" , userId));

      Post post = this.modelMapper.map(postDto ,Post.class );
      post.setImagName("default.png");
      post.setAddDate(new Date());
      post.setUser(updateUser);
      post.setCategory(category);
      Post newPost = this.PostRepo.save(post);
      return this.modelMapper.map(newPost , PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" , "PostId" , postId));
        //Category  category = this.cateRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
      //  User updateUser = this.usersRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "userID" , userId));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImagName(postDto.getImageName());
        //post.setAddDate(new Date());
        Post updatedPost = this.PostRepo.save(post);
        PostDto postDto1 = this.modelMapper.map(updatedPost , PostDto.class);
        return postDto1;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post Id",postId));
        this.PostRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber,pageSize , sort);
        Page<Post> pagePost = this.PostRepo.findAll(p);
        List<Post> allPost  = pagePost.getContent();
        List<PostDto> postDtos = allPost.stream().map((post) -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPages(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" , "postId" ,postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat =this.cateRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","category id" ,categoryId));
        List<Post> posts = this.PostRepo.findByCategory(cat);
        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.usersRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "user id", userId) );
        List<Post> posts = this.PostRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> allpost = this.PostRepo.findByTitleContaining(keyword);
    List<PostDto> postDtos  =allpost.stream().map((post) -> this.modelMapper.map(post , PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
