package com.vape.service;

import com.vape.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    Category createCategory(String name, String description);

    Category updateCategory(Long id, String name, String description);

    boolean deleteCategory(Long id);

    List<Category> getAll();
}
