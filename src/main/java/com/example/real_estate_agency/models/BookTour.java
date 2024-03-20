package com.example.real_estate_agency.models;

import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Client;
import jakarta.persistence.*;

@Entity
@Table(name = "book_tour")
public class BookTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Properties property;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public BookTour() {
        // Constructor mặc định
    }

// Getters và Setters
// Đảm bảo rằng bạn đã tạo getters và setters cho trường id và message

// Các phương thức khác
}