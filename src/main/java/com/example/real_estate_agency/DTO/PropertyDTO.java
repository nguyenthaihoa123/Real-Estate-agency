package com.example.real_estate_agency.DTO;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.models.user.Agent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {
    private Long id;
    private String title;
    private String description;
    private String price;
    private double area;
    private String status;
    private String address;
    private Date createdAt;
    private Date updatedAt;
    private TransactionType transactionType;
    private Category category;
    private Statistical statistical;
    private Agent agent;
    private List<Image> images;
    private String thumbnail;
}
