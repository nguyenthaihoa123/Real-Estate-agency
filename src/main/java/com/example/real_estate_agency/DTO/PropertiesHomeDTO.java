package com.example.real_estate_agency.DTO;

import java.text.DecimalFormat;

public class PropertiesHomeDTO {
    private String id;
    private String title;
    private String description;
    private double price;
    private double area;
    private String status;
    private String address;


    private String transactionType;

    private String category;


    private String thumbnail;

    public PropertiesHomeDTO() {
    }

    public PropertiesHomeDTO(String id, String title, String description, double price, double area, String status, String address, String transactionType, String category, String thumbnail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.area = area;
        this.status = status;
        this.address = address;
        this.transactionType = transactionType;
        this.category = category;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPrice() {
        int intValue = (int) price;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(intValue)+" VND";
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getArea() {
        return String.valueOf(area+" m³");
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
