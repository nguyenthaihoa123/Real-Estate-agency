package com.example.real_estate_agency.repository.property;

import com.example.real_estate_agency.models.property.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
