package com.example.real_estate_agency.repository.property;

import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalePropertyRepository extends JpaRepository<InfoSaleProperty, Long> {
    InfoSaleProperty findByProperty(Properties properties);
}
