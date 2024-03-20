package com.example.real_estate_agency.models;

import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Client;
import jakarta.persistence.*;

@Entity
@Table(name = "save_post")
public class SavePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Properties property;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public SavePost() {
        // Constructor mặc định
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho trường id

    // Các phương thức khác nếu cần
}
