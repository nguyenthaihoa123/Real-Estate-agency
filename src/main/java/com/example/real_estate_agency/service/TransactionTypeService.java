package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.payment.TransactionType;

import java.util.List;

public interface TransactionTypeService {
    TransactionType save(TransactionType transactionType);
    List<TransactionType> getAll();

    TransactionType findById(Long id);
}
