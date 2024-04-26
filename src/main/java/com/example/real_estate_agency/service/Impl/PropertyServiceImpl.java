package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.repository.ImageRepository;
import com.example.real_estate_agency.repository.payment.TransactionTypeRepository;
import com.example.real_estate_agency.repository.property.*;
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
    @Autowired
    private SalePropertyRepository salePropertyRepository;

    @Autowired
    private RentPropertyRepository rentPropertyRepository;
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
    public void deleteProperty(Long id) {
        try {
            propertyRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
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
    public List<Properties> getAllByAgent(Agent agent) {
        try {
            return propertyRepository.findByAgent(agent);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
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
            if(imageRepository.findById(id).isPresent()){
                imageRepository.deleteByPropertyId(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void savePost(SavePost savePost,Properties properties) {
        try {
            Statistical statistical = properties.getStatistical();
            SavePost savePost1 = savePostRepository.findByClientIdAndPropertyId(savePost.getClient().getId(),savePost.getProperty().getId());
            if(savePost1!=null){
                statistical.downSave();
                savePostRepository.delete(savePost1);
            }
            else {
                statistical.upSave();
                savePostRepository.save(savePost);
            }
            properties.setStatistical(statistical);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean getInfoSavePost(Long clientID, Long PropertyID) {
        try{
            return savePostRepository.findByClientIdAndPropertyId(clientID, PropertyID) != null;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Properties> getAllPropertyRent(Agent agent) {
        try {
            TransactionType transactionType = transactionTypeRepository.findByName("Rent");
            return propertyRepository.findByAgentAndTransactionType(agent,transactionType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Properties> getAllPropertySale(Agent agent) {
        try {
            TransactionType transactionType = transactionTypeRepository.findByName("Sale");
            return propertyRepository.findByAgentAndTransactionType(agent,transactionType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkInfoRent(Properties properties) {
        try {
            return rentPropertyRepository.findByProperty(properties) != null;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkInfoSale(Properties properties) {
        try {
            return salePropertyRepository.findByProperty(properties) != null;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public InfoRentProperty saveContractRent(InfoRentProperty infoRentProperty) {
        try {
            return rentPropertyRepository.save(infoRentProperty);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveContractSale(InfoSaleProperty infoSaleProperty) {
        try {
            salePropertyRepository.save(infoSaleProperty);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public InfoRentProperty getInforRent(Properties properties) {
        try {
            return rentPropertyRepository.findByProperty(properties);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InfoSaleProperty getInforSale(Properties properties) {
        try {
            return salePropertyRepository.findByProperty(properties);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteRentContract(Long id) {
        try {
            InfoRentProperty infoRentProperty = rentPropertyRepository.findById(id).get();
            rentPropertyRepository.delete(infoRentProperty);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
