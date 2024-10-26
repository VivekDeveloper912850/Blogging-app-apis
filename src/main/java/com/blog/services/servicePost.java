package com.blog.services;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

import java.util.List;

public interface servicePost {
    // create the Post
    PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);
    // Update the Post
    PostDto updatePost(PostDto postDto , Integer postId);
    // Delete the Post
    void deletePost(Integer postId);
    // get All Post
    PostResponse getAllPost(Integer pageNumber , Integer pageSize ,String sortBy , String sortDir);
    // get Single Post
    PostDto getPostById(Integer postId);
    // get all posts by category
    List<PostDto> getPostsByCategory(Integer categoryId);
    // get all posts by user
    List<PostDto> getPostsByUser(Integer userId);
    // search posts
    List<PostDto> searchPosts(String keyword);
}
