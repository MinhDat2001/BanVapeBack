package com.vape.entity.embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vape.entity.Product;
import com.vape.entity.User;
import lombok.*;

import javax.persistence.*;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
