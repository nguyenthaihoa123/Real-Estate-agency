package com.example.real_estate_agency.models.property;

import com.example.real_estate_agency.models.property.Properties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Properties> properties;
    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Category() {
        // Constructor mặc định
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho trường id và name

    // Các phương thức khác nếu cần
}
