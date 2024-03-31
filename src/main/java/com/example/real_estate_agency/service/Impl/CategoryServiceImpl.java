package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.repository.property.CategoryRepository;
import com.example.real_estate_agency.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName(), category.getProperties().size()))
                .collect(Collectors.toList());
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }
}
