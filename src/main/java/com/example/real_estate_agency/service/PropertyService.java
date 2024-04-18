package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyService {
    Properties save(Properties properties);
    void deletePropetty(Long id);
    Image saveImage(Image image);

    List<Properties> getAll();

    List<Properties> getAllByAgent(Agent agent);

    Properties getById(Long id);

    Page<Properties> getAllForPage(Pageable pageable,String name);

    Page<Properties> getAllByTitleAndCategoryAndTransactionTypeName(Pageable pageable, String title, String category, String transactionTypeName);

    void imageDeleteByProID(Long id);

    void savePost(SavePost savePost,Properties properties);
    boolean getInfoSavePost(Long clientID, Long PropertyID);

    List<Properties> getAllPropertyRent(Agent agent);
    List<Properties> getAllPropertySale(Agent agent);

    boolean checkInfoRent(Properties properties);
    boolean checkInfoSale(Properties properties);

    InfoRentProperty saveContractRent(InfoRentProperty infoRentProperty);

    void saveContractSale(InfoSaleProperty infoSaleProperty);

    InfoRentProperty getInforRent(Properties properties);
    InfoSaleProperty getInforSale(Properties properties);

    boolean deleteRentContract(Long  id);

}
