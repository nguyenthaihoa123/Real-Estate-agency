package com.example.real_estate_agency.service;

import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.models.property.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    Category save(Category category);
    List<CategoryDTO> getAll();

    Category findById(Long id);
}
