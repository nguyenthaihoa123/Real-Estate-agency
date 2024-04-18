package com.example.real_estate_agency.DTO;

import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.Properties;

public class PropertyRentAgentDTO {
    private Properties properties;
    private boolean isRent;

    private InfoRentProperty infoRentProperty;

    private int dayLefts = 0;

    public PropertyRentAgentDTO() {
    }

    public PropertyRentAgentDTO(Properties properties, boolean isRent, InfoRentProperty infoRentProperty) {
        this.properties = properties;
        this.isRent = isRent;
        this.infoRentProperty = infoRentProperty;
    }

    public PropertyRentAgentDTO(Properties properties, boolean isRent, InfoRentProperty infoRentProperty, int dayLefts) {
        this.properties = properties;
        this.isRent = isRent;
        this.infoRentProperty = infoRentProperty;
        this.dayLefts = dayLefts;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public boolean isRent() {
        return isRent;
    }

    public void setRent(boolean rent) {
        isRent = rent;
    }

    public InfoRentProperty getInfoRentProperty() {
        return infoRentProperty;
    }

    public void setInfoRentProperty(InfoRentProperty infoRentProperty) {
        this.infoRentProperty = infoRentProperty;
    }

    public int getDayLefts() {
        return dayLefts;
    }

    public void setDayLefts(int dayLefts) {
        this.dayLefts = dayLefts;
    }
}
