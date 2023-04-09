package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    @JsonProperty("point")
    @SerializedName("point")
    private Double point;

    @JsonProperty("review")
    @SerializedName("review")
    private String review;

    @JsonIgnore
    public boolean isValid() {
        return point != null && 0 <= point && point <= 5;
    }
}
