package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.vape.entity.Product;
import com.vape.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @JsonProperty("parent_id")
    @SerializedName("parent_id")
    Long parentId;

    @JsonProperty("product_id")
    @SerializedName("product_id")
    Long productId;

    @JsonProperty("content")
    @SerializedName("content")
    String content;

    public boolean isValid() {
        return parentId != null && productId != null && content != null && !content.isEmpty();
    }
}
