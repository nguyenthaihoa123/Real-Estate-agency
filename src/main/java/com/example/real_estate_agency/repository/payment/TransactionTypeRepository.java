package com.example.real_estate_agency.repository.payment;

import com.example.real_estate_agency.models.payment.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType,Long> {
    TransactionType findByName(String name);
}
