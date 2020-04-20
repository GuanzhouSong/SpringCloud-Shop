package com.kedacom.category.service.impl;

import com.kedacom.category.model.Category;
import com.kedacom.category.repository.CategoryRepository;
import com.kedacom.category.service.CategoryService;
import com.kedacom.commons.api.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findOne(Long id) {
        Category category = categoryRepository.findOne(id);
        if (category == null) {
            throw new ResourceNotFoundException(id);
        }
        return category;
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
