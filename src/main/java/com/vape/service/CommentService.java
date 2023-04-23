package com.vape.service;

import com.vape.entity.Comment;
import com.vape.entity.User;
import com.vape.model.request.CommentRequest;
import com.vape.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Page<Comment> getAllComment(int pageNumber, int pageSize, String sortField, String sortOrder, Long productId);

    boolean deleteComment(Long commentId);

    Comment createComment(CommentRequest request, User user);

    Comment updateComment(CommentRequest request, Long commentId, User user);

    Comment getCommentById(Long commentId);
}
