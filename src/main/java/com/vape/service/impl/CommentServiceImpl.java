package com.vape.service.impl;

import com.vape.entity.Comment;
import com.vape.entity.Product;
import com.vape.entity.User;
import com.vape.model.request.CommentRequest;
import com.vape.repository.CommentRepository;
import com.vape.repository.ProductRepository;
import com.vape.repository.UserRepository;
import com.vape.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Page<Comment> getAllComment(int pageNumber, int pageSize, String sortField, String sortOrder, Long productId) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Không tồn tại sản phẩm có ID = " + productId));
        return commentRepository.findAllByProduct(product, pageable);
    }

    @Override
    public boolean deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new RuntimeException("Không tồn tại comment có ID = " + commentId)
        );
        commentRepository.delete(comment);
        return true;
    }

    @Override
    public Comment createComment(CommentRequest request, User user) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Không tìm thấy product có ID = " + request.getProductId()));
        Comment comment = Comment.builder()
                .id(0L)
                .content(request.getContent())
                .parentId(request.getParentId())
                .product(product)
                .user(user)
                .time(new Date())
                .updateTime(new Date())
                .emailCreate(user.getEmail())
                .build();
        return commentRepository.save(comment);
    }

    public Comment updateComment(CommentRequest request, Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new RuntimeException("Không tồn tại comment có ID = " + commentId)
        );

        comment.setContent(request.getContent());
        comment.setUpdateTime(new Date());
        comment.setParentId(request.getParentId());

        return commentRepository.save(comment);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
