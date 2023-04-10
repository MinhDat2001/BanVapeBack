package com.vape.repository;

import com.vape.entity.Vote;
import com.vape.entity.embeddable.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {

    @Query("SELECT v FROM Vote v WHERE v.id.product.id = :productId")
    List<Vote> findByProductId(@Param("productId") Long productId);
}
