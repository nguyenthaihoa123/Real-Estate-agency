package com.example.real_estate_agency.models.payment;

import com.example.real_estate_agency.models.property.Properties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transaction_type")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "transactionType", cascade = CascadeType.ALL)
    private List<Properties> properties;
    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public TransactionType() {
        // Constructor mặc định
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho trường id và name

    // Các phương thức khác nếu cần

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }
}
