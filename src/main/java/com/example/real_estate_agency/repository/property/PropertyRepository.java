package com.example.real_estate_agency.repository.property;

import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Properties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Properties,Long> {
    List<Properties> findByTransactionType(TransactionType transactionType);
    Page<Properties> findByTransactionType(TransactionType transactionType, Pageable pageable);
}
