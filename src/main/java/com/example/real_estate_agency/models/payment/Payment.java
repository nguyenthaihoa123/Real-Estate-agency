package com.example.real_estate_agency.models.payment;

import com.example.real_estate_agency.models.user.Agent;
import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @ManyToOne
    @JoinColumn(name = "package_posting_id")
    private PackagePosting packagePosting;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Payment() {
        // Constructor mặc định
    }

    public Payment(String status) {
        this.status = status;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần
}
