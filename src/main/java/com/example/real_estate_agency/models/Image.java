package com.example.real_estate_agency.models;

import com.example.real_estate_agency.models.property.Properties;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Properties property;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Image() {
        // Constructor mặc định
    }

    public Image(String url) {
        this.url = url;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần
}
