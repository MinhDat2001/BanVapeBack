package com.vape.controller;

import com.vape.entity.Category;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.CategoryRequest;
import com.vape.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category/create")
    public VapeResponse<Boolean> createCategory(@RequestBody CategoryRequest request) {
        if (!request.isValid()) {
            return VapeResponse.newInstance(Error.INVALID_PARAM.getErrorCode(), Error.INVALID_PARAM.getMessage(), false);
        }
        boolean isSuccess = categoryService.createCategory(request.getName(), request.getDescription()) != null;
        return isSuccess
                ? VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), true)
                : VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), false);
    }

    @PutMapping("/category/update")
    public VapeResponse<Category> updateCategory(@RequestBody CategoryRequest request) {
        if (!request.isValid() || request.getId() == null) {
            return VapeResponse.newInstance(Error.INVALID_PARAM.getErrorCode(), Error.INVALID_PARAM.getMessage(), null);
        }
        Category category = categoryService.updateCategory(request.getId(), request.getName(), request.getDescription());
        return category != null
                ? VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), category)
                : VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
    }

    @DeleteMapping("/category/delete/{id}")
    public VapeResponse<Boolean> deleteCategory(@PathVariable Long id) {
        boolean isSuccess = categoryService.deleteCategory(id);
        return isSuccess
                ? VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), true)
                : VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), false);
    }

    @GetMapping("category/getAll")
    public VapeResponse<List<Category>> getAllCategory() {
        List<Category> categories = categoryService.getAll();
        return (categories != null && !categories.isEmpty())
                ? VapeResponse.newInstance(Error.OK, categories)
                : VapeResponse.newInstance(Error.NOT_OK, categories);
    }
}
