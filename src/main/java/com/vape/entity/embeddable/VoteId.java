package com.vape.entity.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vape.entity.Product;
import com.vape.entity.User;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteId implements Serializable {

    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Product product;
}
