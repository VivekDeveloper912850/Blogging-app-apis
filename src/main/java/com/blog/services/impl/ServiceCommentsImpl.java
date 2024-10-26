package com.blog.services.impl;

import com.blog.entities.Comments;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repository.CommentRepo;
import com.blog.repository.postRepo;
import com.blog.services.ServiceComments;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCommentsImpl implements ServiceComments {
    @Autowired
    private postRepo repo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComments(CommentDto commentDto, Integer postId) {
        Post post  = this.repo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" , "postId", postId));
        Comments comments = this.modelMapper.map(commentDto , Comments.class);
        comments.setPost(post);
        Comments commentSaved = this.commentRepo.save(comments);

        return this.modelMapper.map(commentSaved , CommentDto.class);
    }

    @Override
    public void deleteComments(Integer commentId) {
        Comments comments = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comments","CommentId" ,commentId));
        this.commentRepo.delete(comments);

    }
}
