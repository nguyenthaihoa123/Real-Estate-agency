package com.example.real_estate_agency.mapper;


import com.example.real_estate_agency.DTO.PropertiesApiDTO;
import com.example.real_estate_agency.DTO.PropertiesHomeDTO;
import com.example.real_estate_agency.DTO.PropertyDTO;
import com.example.real_estate_agency.models.property.Properties;

import java.text.DecimalFormat;

public class PropertyMapper {
    public static PropertyDTO modelToDTO(Properties properties) {
        PropertyDTO tmp = new PropertyDTO();
        tmp.setId(properties.getId());
        tmp.setTitle(properties.getTitle());
        tmp.setDescription(properties.getDescription());
//        convert price to VND
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formartPrice = decimalFormat.format(properties.getPrice())+" VND";
        tmp.setPrice(formartPrice);
        tmp.setArea(properties.getArea());
        tmp.setStatus(properties.getStatus());
        tmp.setAddress(properties.getAddress());
        tmp.setTransactionType(properties.getTransactionType());
        tmp.setCategory(properties.getCategory());
        tmp.setThumbnail(properties.getThumbnail());
        tmp.setAgent(properties.getAgent());
        tmp.setTransactionType(properties.getTransactionType());
        tmp.setStatistical(properties.getStatistical());
        return tmp;
    }
    public static Properties DtoToModel(PropertyDTO model) {
        Properties dto = new Properties();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        //covert VND to double
        String priceWithoutCommas = model.getPrice().replace(",", "").replace(" VND", "");
        dto.setPrice(Double.parseDouble(priceWithoutCommas));
        dto.setArea(model.getArea());
        dto.setStatus(model.getStatus());
        dto.setAddress(model.getAddress());
        dto.setTransactionType(model.getTransactionType());
        dto.setCategory(model.getCategory());
        dto.setThumbnail(model.getThumbnail());
        dto.setAgent(model.getAgent());
        dto.setTransactionType(model.getTransactionType());
        dto.setStatistical(model.getStatistical());
        return dto;
    }
}
