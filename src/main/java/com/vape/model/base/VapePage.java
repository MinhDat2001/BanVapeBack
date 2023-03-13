package com.vape.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VapePage<T> {
    public interface Converter<X, Y> {
        Y convert(X object);
    }

    @JsonProperty("total_pages")
    @SerializedName("total_pages")
    @Builder.Default
    public int totalPages = -1;

    @JsonProperty("total_elements")
    @SerializedName("total_elements")
    @Builder.Default
    public long totalElements = -1;

    @JsonProperty("elements")
    @SerializedName("elements")
    public List<T> elements;

    public static <X> VapePage<X> paging(List<X> objects, long total, int pageSize) {
        return objects == null || objects.isEmpty()
                ? VapePage.<X>builder()
                    .totalElements(0)
                    .totalPages(0)
                    .build()
                : VapePage.<X>builder()
                    .totalElements(total)
                    .totalPages(pageSize == 0 ? 1 : (int) (total / pageSize + (total % pageSize == 0 ? 0 : 1)))
                    .elements(objects)
                    .build();
    }

    public static <X, Y> VapePage<Y> paging(List<X> objects, long total, int pageSize, Converter<X, Y> converter) {
        return objects == null || objects.isEmpty()
                ? VapePage.<Y>builder()
                    .totalElements(0)
                    .totalPages(0)
                    .build()
                : VapePage.<Y>builder()
                    .totalElements(total)
                    .totalPages(pageSize == 0 ? 1 : (int) (total / pageSize + (total % pageSize == 0 ? 0 : 1)))
                    .elements(objects.stream().map(converter::convert).collect(Collectors.toList()))
                    .build();

    }
}
