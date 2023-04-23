package com.vape.controller;

import com.vape.Constant.Constant;
import com.vape.entity.Comment;
import com.vape.entity.User;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.CommentRequest;
import com.vape.model.request.CustomPageRequest;
import com.vape.service.CommentService;
import com.vape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @GetMapping("/comments/{productId}")
    public VapeResponse<Page<Comment>> getAllComment(
            @RequestBody CustomPageRequest request,
            @PathVariable("productId") Long productId) {
        request.checkDataComment();
        Page<Comment> comments = commentService.getAllComment(
                request.getPageNumber(),
                request.getPageSize(),
                request.getSortField(),
                request.getSortOrder(),
                productId
        );
        return comments != null && !comments.isEmpty()
                ? VapeResponse.newInstance(Error.OK, comments)
                : VapeResponse.newInstance(Error.EMPTY, comments);
    }

    @DeleteMapping("comments/{commentId}")
    public VapeResponse<Boolean> deleteComment(@PathVariable("commentId") Long commentId) {

        User user = getUserNameLoggingIn();
        if (hasRoleCreateUpdate(user, commentId)) {
            boolean isSuccess = commentService.deleteComment(commentId);
            return VapeResponse.newInstance(isSuccess ? Error.OK : Error.NOT_OK, null);
        } else {
            return VapeResponse.newInstance(Error.NOT_AUTHORIZED, null);
        }
    }

    @PostMapping("comments")
    public VapeResponse<Comment> createComment(@RequestBody CommentRequest request) {
        if (!request.isValid()) {
            return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        } else {
            User user = getUserNameLoggingIn();
            Comment comment = commentService.createComment(request, user);
            return comment != null
                    ? VapeResponse.newInstance(Error.OK, comment)
                    : VapeResponse.newInstance(Error.NOT_OK, null);
        }
    }

    @PutMapping("comments/{commentId}")
    public VapeResponse<Comment> updateComment(@RequestBody CommentRequest request, @PathVariable("commentId") Long commentId) {
        if (!request.isValid()) {
            return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        } else {
            User user = getUserNameLoggingIn();
            if (hasRoleCreateUpdate(user, commentId)) {
                Comment comment = commentService.updateComment(request, commentId, user);
                return comment != null
                        ? VapeResponse.newInstance(Error.OK, comment)
                        : VapeResponse.newInstance(Error.NOT_OK, null);
            } else {
                return VapeResponse.newInstance(Error.NOT_AUTHORIZED, null);
            }
        }
    }

    private User getUserNameLoggingIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getUserByEmail(username);
    }

    private boolean hasRoleCreateUpdate(User user, Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return comment.getEmailCreate().equals(user.getEmail())
                || user.getRole().equals(Constant.Role.ADMIN)
                || user.getRole().equals(Constant.Role.EDITOR);
    }
}
