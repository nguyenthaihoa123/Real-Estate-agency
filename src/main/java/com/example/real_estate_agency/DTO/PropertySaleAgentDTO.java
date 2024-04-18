package com.example.real_estate_agency.DTO;

import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;

public class PropertySaleAgentDTO {
    private Properties properties;
    private boolean isSale;

    private InfoSaleProperty infoSaleProperty;

    public PropertySaleAgentDTO() {
    }

    public PropertySaleAgentDTO(Properties properties, boolean isSale, InfoSaleProperty infoSaleProperty) {
        this.properties = properties;
        this.isSale = isSale;
        this.infoSaleProperty = infoSaleProperty;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public InfoSaleProperty getInfoSaleProperty() {
        return infoSaleProperty;
    }

    public void setInfoSaleProperty(InfoSaleProperty infoSaleProperty) {
        this.infoSaleProperty = infoSaleProperty;
    }
}
