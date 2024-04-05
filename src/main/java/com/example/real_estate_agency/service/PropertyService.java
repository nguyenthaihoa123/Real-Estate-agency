package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.property.Properties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyService {
    Properties save(Properties properties);
    Image saveImage(Image image);

    List<Properties> getAll();

    Properties getById(Long id);

    Page<Properties> getAllForPage(Pageable pageable,String name);

    Page<Properties> getAllByTitleAndCategoryAndTransactionTypeName(Pageable pageable, String title, String category, String transactionTypeName);

    void imageDeleteByProID(Long id);

    void savePost(SavePost savePost,Properties properties);
    boolean getInfoSavePost(Long clientID, Long PropertyID);

}
