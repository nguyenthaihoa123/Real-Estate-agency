package com.example.real_estate_agency.DTO;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.models.user.Agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PropertiesApiDTO {
    private String title;
    private String description;
    private double price;
    private double area;
    private String status;

    private Date createdAt;


    private Date updatedAt;


    private TransactionType transactionType;

    private Category category;

    private Statistical statistical;
    private Agent agent;

    private List<Image> images;

    public PropertiesApiDTO() {
    }

    public PropertiesApiDTO(String title, String description, double price, double area, String status, Date createdAt, Date updatedAt, TransactionType transactionType, Category category, Statistical statistical, Agent agent, List<Image> images) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.area = area;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.transactionType = transactionType;
        this.category = category;
        this.statistical = statistical;
        this.agent = agent;
        this.images = images;
    }

    public PropertiesApiDTO(Properties property) {
        this.title = property.getTitle();
        this.description = property.getDescription();
        this.price = property.getPrice();
        this.area = property.getArea();
        this.status = property.getStatus();
        this.createdAt = property.getCreatedAt();
        this.updatedAt = property.getUpdatedAt();
        this.transactionType = property.getTransactionType();
        this.category = property.getCategory();
        this.statistical = property.getStatistical();
        this.agent = property.getAgent();
        this.images = convertImageListToDTOList(property.getImages());
    }

    private List<Image> convertImageListToDTOList(List<Image> images) {
        List<Image> imageDTOList = new ArrayList<>();
        for (Image image : images) {
            imageDTOList.add(new Image(image.getUrl()));
        }
        return imageDTOList;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Statistical getStatistical() {
        return statistical;
    }

    public void setStatistical(Statistical statistical) {
        this.statistical = statistical;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Properties toProduct() {
        Properties product = new Properties();
        product.setTitle(this.title);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setArea(this.area);
        product.setStatus(this.status);
        product.setCreatedAt(this.createdAt);
        product.setUpdatedAt(this.updatedAt);
        product.setTransactionType(this.transactionType);
        product.setCategory(this.category);
        product.setStatistical(this.statistical);
        product.setAgent(this.agent);
        product.setThumbnail(this.images.get(0).getUrl());
        return product;
    }
}
