package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.repository.property.CategoryRepository;
import com.example.real_estate_agency.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category save(Category category) {
        try {
            return categoryRepository.save(category);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
