package com.example.real_estate_agency.models.payment;

import com.example.real_estate_agency.models.user.Agent;
import jakarta.persistence.*;

import java.util.Date;

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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Payment() {
        // Constructor mặc định
    }

    public Payment(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PackagePosting getPackagePosting() {
        return packagePosting;
    }

    public void setPackagePosting(PackagePosting packagePosting) {
        this.packagePosting = packagePosting;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần
}
