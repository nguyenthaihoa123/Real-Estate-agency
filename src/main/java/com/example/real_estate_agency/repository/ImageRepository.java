package com.example.real_estate_agency.repository;

import com.example.real_estate_agency.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.property.id = :propertyId")
    void deleteByPropertyId(Long propertyId);
}
