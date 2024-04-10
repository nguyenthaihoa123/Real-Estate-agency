package com.example.real_estate_agency.DTO;

import com.example.real_estate_agency.models.property.Properties;

public class PropertySavePostClientDTO {

    private Properties properties;
    private boolean isSave;

    public PropertySavePostClientDTO() {
    }

    public PropertySavePostClientDTO(Properties properties, boolean isSave) {
        this.properties = properties;
        this.isSave = isSave;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }
}
