package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.repository.payment.TransactionTypeRepository;
import com.example.real_estate_agency.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Override
    public TransactionType save(TransactionType transactionType) {
        try {
            return  transactionTypeRepository.save(transactionType);
        }
        catch (Exception e){
            return null;
        }
    }
}
