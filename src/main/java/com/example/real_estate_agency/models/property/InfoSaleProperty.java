package com.example.real_estate_agency.models.property;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class InfoSaleProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thông tin chủ nhà
    private String ownerID; // CCCD chủ nhà
    private String ownerName; // Tên chủ nhà
    private String ownerPhone; // Số điện thoại chủ nhà

    // Thông tin người mua
    private String buyerID; // CCCD người mua
    private String buyerName; // Tên người mua
    private Date buyerDOB; // Ngày sinh người mua
    private String buyerPhone; // Số điện thoại người mua

    private String buyerEmail;

    // Thông tin hợp đồng bán
    private double sellingPrice; // Giá bán
    private double commission; // Hoa hồng
    private Date contractDate; // Ngày ký hợp đồng

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Properties property;

    // Constructors, getters, setters, và các phương thức khác


    public InfoSaleProperty() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Date getBuyerDOB() {
        return buyerDOB;
    }

    public void setBuyerDOB(Date buyerDOB) {
        this.buyerDOB = buyerDOB;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Properties getProperty() {
        return property;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }
}

