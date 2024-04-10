package com.example.real_estate_agency.models;

import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "save_post")
public class SavePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Properties property;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public SavePost() {
        // Constructor mặc định
    }

    public SavePost(Long id, Client client, Properties property) {
        this.id = id;
        this.client = client;
        this.property = property;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Properties getProperty() {
        return property;
    }

    public void setProperty(Properties property) {
        this.property = property;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho trường id

    // Các phương thức khác nếu cần
}
