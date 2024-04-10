package com.example.real_estate_agency.models.property;

import jakarta.persistence.*;

import java.util.Date;

@Entity
//@Table(name = "property_management")
public class InfoRentProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thông tin chủ nhà
    private String ownerID; // CCCD chủ nhà
    private String ownerName; // Tên chủ nhà
    private String ownerPhone; // Số điện thoại chủ nhà

    // Thông tin người thuê
    private String tenantID; // CCCD người thuê
    private String tenantName; // Tên người thuê
    private Date tenantDOB; // Ngày sinh người thuê
    private String tenantPhone; // Số điện thoại người thuê

    // Thông tin hợp đồng thuê
    private double unitPrice; // Đơn giá
    private double compensationLevel; // Mức bồi thường
    private double deposit; // Tiền đặt cọc
    private Date contractStartDate; // Ngày bắt đầu hợp đồng
    private Date contractEndDate; // Ngày kết thúc hợp đồng

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Properties property;

    public InfoRentProperty() {
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

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Date getTenantDOB() {
        return tenantDOB;
    }

    public void setTenantDOB(Date tenantDOB) {
        this.tenantDOB = tenantDOB;
    }

    public String getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(String tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getCompensationLevel() {
        return compensationLevel;
    }

    public void setCompensationLevel(double compensationLevel) {
        this.compensationLevel = compensationLevel;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public Properties getProperty() {
        return property;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }
}
