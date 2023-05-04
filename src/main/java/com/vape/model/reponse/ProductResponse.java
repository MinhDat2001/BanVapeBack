package com.vape.model.reponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.vape.entity.Category;
import com.vape.entity.Image;
import com.vape.entity.ProductDetail;
import com.vape.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    @SerializedName("id")
    @JsonProperty("id")
    private Long id;

    @SerializedName("name")
    @JsonProperty("name")
    private String name;

    @SerializedName("avatar")
    @JsonProperty("avatar")
    private String avatar;

    @SerializedName("quantity")
    @JsonProperty("quantity")
    private int quantity;

    @SerializedName("price")
    @JsonProperty("price")
    private int price;

    @SerializedName("description")
    @JsonProperty("description")
    private String description;

    @SerializedName("product_details")
    @JsonProperty("product_details")
    private List<ProductDetail> productDetails;

    @SerializedName("categories")
    @JsonProperty("categories")
    private Set<Category> categories;

    @SerializedName("images")
    @JsonProperty("images")
    private List<Image> images;

    @SerializedName("votes")
    @JsonProperty("votes")
    private Set<Vote> votes;
}
