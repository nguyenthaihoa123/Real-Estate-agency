package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.repository.ImageRepository;
import com.example.real_estate_agency.repository.payment.TransactionTypeRepository;
import com.example.real_estate_agency.repository.property.PropertyRepository;
import com.example.real_estate_agency.repository.property.StatisticalRepository;
import com.example.real_estate_agency.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private StatisticalRepository statisticalRepository;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    @Override
    public Properties save(Properties property) {
        try {
            Statistical statistical = new Statistical();
            statistical.setTotalSave(0);
            statistical.setTotalBooking(0);

            property.setStatistical(statistical);
            statisticalRepository.save(statistical);
            // Lưu property
            Properties savedProperty = propertyRepository.save(property);
            return savedProperty;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Properties> getAll() {
        return propertyRepository.findAll();
    }

    @Override
    public List<Properties> getAllBySellorRent(String name) {
        TransactionType transactionType = transactionTypeRepository.findByName(name);
        return propertyRepository.findByTransactionType(transactionType);
    }

    @Override
    public Page<Properties> getAllForPage(Pageable pageable,String name) {
        if (name == null || name.isEmpty()) {
            return propertyRepository.findAll(pageable);
        } else {
            TransactionType transactionType = transactionTypeRepository.findByName(name);
            return propertyRepository.findByTransactionType(transactionType,pageable);
        }
    }


}
