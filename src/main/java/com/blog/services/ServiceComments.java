package com.blog.services;

import com.blog.payloads.CommentDto;
import com.blog.payloads.PostDto;

public interface ServiceComments {
    // create comments
    CommentDto createComments(CommentDto commentDto , Integer postId);
    void deleteComments(Integer commentId);
}
