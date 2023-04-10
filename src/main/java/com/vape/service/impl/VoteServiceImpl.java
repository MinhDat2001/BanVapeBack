package com.vape.service.impl;

import com.vape.entity.Product;
import com.vape.entity.User;
import com.vape.entity.Vote;
import com.vape.entity.embeddable.VoteId;
import com.vape.model.request.VoteRequest;
import com.vape.repository.ProductRepository;
import com.vape.repository.VoteRepository;
import com.vape.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Vote createUserVote(User user, Long productId, VoteRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại product ID = " + productId));
        VoteId voteId = VoteId.builder().user(user).product(product).build();
        Vote vote = Vote.builder()
                .point(request.getPoint())
                .review(request.getReview())
                .id(voteId)
                .voter(user.getEmail())
                .createTime(new Date())
                .build();

        return voteRepository.save(vote);
    }

    @Override
    public Vote getVoteByUserAndProduct(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại product ID = " + productId));
        VoteId voteId = VoteId.builder().user(user).product(product).build();
        return voteRepository.findById(voteId).orElse(null);
    }

    @Override
    public Vote updateVote(User user, Long productId, VoteRequest request) {
        Vote vote = getVoteByUserAndProduct(user, productId);
        if (vote == null) {
            return null;
        }
        vote.setUpdateTime(new Date());
        vote.setPoint(request.getPoint());
        vote.setReview(request.getReview());

        return voteRepository.save(vote);
    }

    @Override
    public boolean deleteVote(User user, Long productId) {
        try {
            Vote vote = getVoteByUserAndProduct(user, productId);
            if (vote == null) {
                throw new RuntimeException("Không tồn tại đánh giá của user có username = " + user.getEmail() + " đánh giá product có ID = " + productId);
            }
            voteRepository.delete(vote);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
