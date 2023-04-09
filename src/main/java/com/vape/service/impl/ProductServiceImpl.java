package com.vape.service.impl;

import com.vape.cloudinary.CloudinaryService;
import com.vape.entity.*;
import com.vape.model.reponse.ProductResponse;
import com.vape.model.request.CustomProductRequest;
import com.vape.repository.*;
import com.vape.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Page<ProductResponse> getAllProduct(int pageNumber, int pageSize, String sortField, String sortOrder, Long categoryId) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại category có id = " + categoryId));
        List<Category> categories = Collections.singletonList(category);
        return productRepository.findAllByCategoriesIn(categories, pageable).map(product ->
                ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .avatar(product.getAvatar())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .votes(product.getVotes())
                        .images(product.getImages())
                        .productDetails(product.getProductDetails())
                        .build());
    }

    @Override
    public Page<ProductResponse> getAllProductByName(int pageNumber, int pageSize, String sortField, String sortOrder, String key, Long categoryId) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại category có id = " + categoryId));
        List<Category> categories = Collections.singletonList(category);
        return productRepository.findAllByNameContainingIgnoreCaseAndCategoriesIn(key, categories, pageable)
                .map(product ->
                        ProductResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .avatar(product.getAvatar())
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .description(product.getDescription())
                                .votes(product.getVotes())
                                .images(product.getImages())
                                .productDetails(product.getProductDetails())
                                .build()
                );
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Không tồn tại product có ID = " + productId)
        );
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .avatar(product.getAvatar())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .description(product.getDescription())
                .images(product.getImages())
                .votes(product.getVotes())
                .productDetails(product.getProductDetails())
                .build();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Product createProduct(CustomProductRequest request, MultipartFile[] files, MultipartFile avatar) {
        try {
            Set<Category> categories = new HashSet<>(categoryRepository.findByIdIn(request.getCategoryIds()));

            Product product = Product.builder()
                    .name(request.getName())
                    .price(request.getPrice())
                    .description(request.getDescription())
                    .categories(categories)
                    .quantity(request.getQuantity())
                    .build();

            Product productSaved = productRepository.save(product);

            List<Image> images = Arrays.stream(files).map(file -> cloudinaryService.uploadURl(file))
                    .map(url -> Image.builder().link(url).product(productSaved).build())
                    .collect(Collectors.toList());

            images.forEach(image -> System.out.println(image.getLink()));
            List<Image> imagesSaved = imageRepository.saveAll(images);
            product.setImages(imagesSaved);
            product.setAvatar(cloudinaryService.uploadURl(avatar));

            return productRepository.save(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Product updateProduct(Long productId, CustomProductRequest request, MultipartFile[] files, MultipartFile avatar) {

        try {
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy product có ID = " + productId)
            );
            System.out.println("1" + product.getId());
            Set<Category> categories = new HashSet<>(categoryRepository.findByIdIn(request.getCategoryIds()));
            System.out.println(categories.size() + " size");
            List<Image> images = imageRepository.findAllByLinkIn(request.getImages());
            if (images != null) {
                List<Image> imagesRemove = imageRepository.findAllByProduct(product)
                        .stream()
                        .filter(image ->
                                images.stream()
                                        .noneMatch(imaFil -> imaFil.getLink().equals(image.getLink())))
                        .collect(Collectors.toList());
                if (!imagesRemove.isEmpty()) {
                    imagesRemove.forEach(image -> {
                        String response = cloudinaryService.deleteFromUrl(image.getLink());
                        imageRepository.delete(image);
                        System.out.println(response);
                        if (response.equals("ok")) {
                            System.out.println("Đã xóa URl = " + image.getLink() + " trên cloudinary");
                        } else if (response.equals("not found")) {
                            System.out.println("Đường dẫn URL = " + image.getLink() + "không tồn tại đã bị xóa trước đó rồi");
                        } else {
                            System.out.println("URl = " + image.getLink() + " chưa bị xóa trên cloudinary");
                        }
                    });
                }
            }
            System.out.println("ok");

            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product.setDescription(request.getDescription());
            product.setCategories(categories);
            product.setImages(images);
            product.setQuantity(request.getQuantity());

            Product productSaved = productRepository.save(product);
            List<Image> imagesSaved;
            if (files != null && files.length != 0) {
                imagesSaved = Arrays.stream(files).map(file -> cloudinaryService.uploadURl(file))
                        .map(url -> Image.builder().link(url).product(productSaved).build())
                        .collect(Collectors.toList());
                imagesSaved = imageRepository.saveAll(imagesSaved);
                productSaved.getImages().addAll(imagesSaved);
            }

            if (avatar != null) {
                if (request.getAvatar() != null && !request.getAvatar().isEmpty()) {
                    cloudinaryService.deleteFromUrl(request.getAvatar());
                }
                productSaved.setAvatar(cloudinaryService.uploadURl(avatar));
            }

            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Không thể update product: " + e);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Không tìm thấy product có ID = " + productId)
        );
        List<Vote> votes;
        try {
            List<Image> images = imageRepository.findAllByProduct(product);
            if (images != null && !images.isEmpty()) {
                images.forEach(image -> cloudinaryService.deleteFromUrl(image.getLink()));
                imageRepository.deleteAll(images);
            }
            List<ProductCategory> productCategories = productCategoryRepository.findAllByProduct(product);
            if (productCategories != null && !productCategories.isEmpty()) {
                productCategoryRepository.deleteProductCategory(product);
            }
            votes = voteRepository.findByProductId(productId);
            if (!votes.isEmpty()) {
                voteRepository.deleteAll(votes);
            }
            String response = cloudinaryService.deleteFromUrl(product.getAvatar());
            if (response.equals("ok")) {
                System.out.println("Đã xóa thành công URL = " + product.getAvatar() + " trên cloudinary");
            } else if (response.equals("not found")) {
                System.out.println("Không tìm thấy URL = " + product.getAvatar() + " để xóa");
            } else {
                System.out.println("URL = " + product.getAvatar() + " chưa bị xóa trên cloudinary");
            }
            product.getProductDetails().forEach(productDetail -> cloudinaryService.deleteFromUrl(productDetail.getAvatar()));
            List<ProductDetail> productDetails = productDetailRepository.findAllByProduct(product);
            if (productDetails != null && !productDetails.isEmpty()) {
                productDetailRepository.deleteAll(productDetails);
            }
            productRepository.delete(product);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return false;
        }

        return true;
    }
}
