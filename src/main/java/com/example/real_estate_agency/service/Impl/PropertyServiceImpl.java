package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.repository.ImageRepository;
import com.example.real_estate_agency.repository.payment.TransactionTypeRepository;
import com.example.real_estate_agency.repository.property.PropertyRepository;
import com.example.real_estate_agency.repository.property.SavePostRepository;
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
    @Autowired
    private SavePostRepository savePostRepository;
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
    public Properties getById(Long id) {
        try {
            return propertyRepository.findById(id).get();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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

    @Override
    public Page<Properties> getAllByTitleAndCategoryAndTransactionTypeName(Pageable pageable, String title, String category, String transactionTypeName) {
        if (title != null && category != null && transactionTypeName != null) {
            return propertyRepository.findByTitleContainingAndCategoryNameContainingAndTransactionTypeNameContaining(pageable, title, category, transactionTypeName);
        } else if (title != null && transactionTypeName != null) {
            return propertyRepository.findByTitleContainingAndTransactionTypeNameContaining(pageable, title, transactionTypeName);
        } else if (category != null && transactionTypeName != null) {
            return propertyRepository.findByCategoryNameContainingAndTransactionTypeNameContaining(pageable, category, transactionTypeName);
        } else if (title != null && category != null) {
            return propertyRepository.findByTitleContainingAndCategoryNameContaining(pageable, title, category);
        } else if (title != null) {
            return propertyRepository.findByTitleContaining(pageable, title);
        } else if (category != null) {
            return propertyRepository.findByCategoryNameContaining(pageable, category);
        } else if (transactionTypeName != null) {
            return propertyRepository.findByTransactionTypeNameContaining(pageable, transactionTypeName);
        } else {
            return propertyRepository.findAll(pageable);
        }
    }

    @Override
    public void imageDeleteByProID(Long id) {
        try {
            imageRepository.deleteByPropertyId(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void savePost(SavePost savePost,Properties properties) {
        try {
            Statistical statistical = properties.getStatistical();
            statistical.upSave();
            properties.setStatistical(statistical);
            savePostRepository.save(savePost);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
