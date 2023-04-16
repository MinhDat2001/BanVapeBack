package com.vape.view;

import com.vape.view.DetailView;
import com.vape.model.CartStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartView implements Serializable {

    private Long id;
    private String email;
    private int quantity;
    private CartStatus status;

    private DetailView detailView;
}
