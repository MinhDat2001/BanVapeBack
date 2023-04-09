package com.vape.entity;

import com.vape.entity.embeddable.VoteId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
    @EmbeddedId
    private VoteId id;

    private double point;

    private String review;
}