package com.example.real_estate_agency.repository.property;

import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentPropertyRepository extends JpaRepository<InfoRentProperty, Long> {
    InfoRentProperty findByProperty(Properties properties);
}
