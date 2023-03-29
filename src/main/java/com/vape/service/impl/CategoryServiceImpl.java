package com.vape.service.impl;

import com.vape.entity.Category;
import com.vape.repository.CategoryRepository;
import com.vape.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, String description) {
        Category category = categoryRepository.findByName(name);
        System.out.println(category);
        if (category != null) {
            return null;
        }
        return categoryRepository.save(
                Category.builder()
                        .name(name)
                        .description(description)
                        .build());
    }

    @Override
    public Category updateCategory(Long id, String name, String description) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        Category category1 = categoryRepository.findByName(name);
        if (category1 != null) {
            return null;
        }
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        System.out.println(category);
        if (category == null) {
            return false;
        }
        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            return false;
        }
        categoryRepository.delete(category);
        return true;
    }

    @Override
    public List<Category> getAll() {
        return  categoryRepository.findAll();
    }
}
