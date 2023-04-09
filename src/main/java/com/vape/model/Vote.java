package com.vape.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    private Long id;
    private double point;
    private String review;
}
