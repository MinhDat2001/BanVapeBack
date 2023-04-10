package com.vape.controller;

import com.vape.Constant.Constant;
import com.vape.entity.User;
import com.vape.entity.Vote;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.VoteRequest;
import com.vape.service.UserService;
import com.vape.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class VoteController {
    @Autowired
    VoteService voteService;

    @Autowired
    UserService userService;

    @PostMapping("/vote/{productId}")
    public VapeResponse<Object> createVoteProduct(
            @PathVariable("productId") Long productId,
            @RequestBody VoteRequest request
            ) {
        if (!request.isValid()) return VapeResponse.newInstance(Error.INVALID_PARAM, "Dữ liệu truyền lên không hợp lệ");
        User user = getUserNameLoggingIn();
        Vote isVoter = voteService.getVoteByUserAndProduct(user, productId);
        if (isVoter != null) return VapeResponse.newInstance(Error.EXISTED, "Bạn đã đánh giá product này rồi");
        Vote vote = voteService.createUserVote(user, productId, request);
        return vote != null
                ? VapeResponse.newInstance(Error.OK, vote)
                : VapeResponse.newInstance(Error.NOT_OK, "Vote không thành công");
    }

    @PutMapping("/vote/{productId}")
    public VapeResponse<Object> updateVoteProduct(
            @PathVariable("productId") Long productId,
            @RequestBody VoteRequest request
    ) {
        if (!request.isValid()) return VapeResponse.newInstance(Error.INVALID_PARAM, "Dữ liệu truyền lên không hợp lệ");
        User user = getUserNameLoggingIn();
        Vote isVoter = voteService.getVoteByUserAndProduct(user, productId);
        if (isVoter == null) return VapeResponse.newInstance(Error.NOT_EXISTED, "Không tồn tại đánh giá");
        if (isVoter.getVoter().equals(user.getEmail())
                || user.getRole().equals(Constant.Role.ADMIN)
                || user.getRole().equals(Constant.Role.EDITOR)) {
            Vote vote = voteService.updateVote(user, productId, request);
            return vote != null
                    ? VapeResponse.newInstance(Error.OK, vote)
                    : VapeResponse.newInstance(Error.NOT_OK, "Update vote không thành công");
        } else {
            return VapeResponse.newInstance(Error.NOT_AUTHORIZED, "Không được phép cập nhật");
        }
    }

    @DeleteMapping("/vote/{productId}")
    public VapeResponse<Object> deleteVote(
            @PathVariable("productId") Long productId
    ) {
        User user = getUserNameLoggingIn();
        Vote isVoter = voteService.getVoteByUserAndProduct(user, productId);
        if (isVoter == null) return VapeResponse.newInstance(Error.NOT_EXISTED, "Không tồn tại đánh giá");
        if (isVoter.getVoter().equals(user.getEmail())
                || user.getRole().equals(Constant.Role.ADMIN)
                || user.getRole().equals(Constant.Role.EDITOR)) {
            boolean isRemoveSuccess = voteService.deleteVote(user, productId);
            return isRemoveSuccess
                    ? VapeResponse.newInstance(Error.OK, "Xóa thành công đánh giá")
                    : VapeResponse.newInstance(Error.NOT_OK, "Xóa thất bại");
        } else {
            return VapeResponse.newInstance(Error.NOT_AUTHORIZED, "Không được phép xóa");
        }
    }

    private User getUserNameLoggingIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getUserByEmail(username);
    }
}
