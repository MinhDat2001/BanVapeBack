package com.vape.repository;

import com.vape.entity.Image;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image save(Image image);

    List<Image> findAllByLinkIn(List<String> links);

    List<Image> findAllByProduct(Product product);

    Image findByLink(String link);
}
