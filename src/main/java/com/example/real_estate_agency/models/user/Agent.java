package com.example.real_estate_agency.models.user;

import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.property.Post;
import com.example.real_estate_agency.models.property.Properties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String company;
    private String status;

    @OneToMany(mappedBy = "agent")
    private List<Payment> payments;
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private List<Properties> properties;

    @OneToMany(mappedBy = "agent")
    private List<Post> posts;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Agent() {
        // Constructor mặc định
    }

    public Agent(String username, String password, String email, String phone, String address, String company, String status) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.company = company;
        this.status = status;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần
}
