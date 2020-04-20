package com.kedacom.category.service;


import com.kedacom.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category findOne(Long id);

    Category save(Category category);

    List<Category> findByName(String name);

    Category decrease(Category category);
}
