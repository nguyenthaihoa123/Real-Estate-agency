package com.example.real_estate_agency.repository.payment;

import com.example.real_estate_agency.models.payment.PackagePosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackagePostingRepository extends JpaRepository<PackagePosting,Long> {
    PackagePosting findByPrice(double price);
}
