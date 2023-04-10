package com.vape.service;

import com.vape.entity.User;
import com.vape.entity.Vote;
import com.vape.model.request.VoteRequest;
import org.springframework.stereotype.Service;

@Service
public interface VoteService {
    Vote createUserVote(User user, Long productId, VoteRequest request);

    Vote getVoteByUserAndProduct(User user, Long productId);

    Vote updateVote(User user, Long productId, VoteRequest request);

    boolean deleteVote(User user, Long productId);
}
