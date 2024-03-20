package com.example.real_estate_agency.models.property;

import com.example.real_estate_agency.models.*;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.user.Agent;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private double price;
    private double area;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToOne(mappedBy = "property")
    private Post post;

    @OneToOne
    @JoinColumn(name = "statistical_id")
    private Statistical statistical;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SavePost> savedPosts = new HashSet<>();

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookTour> bookedTours = new HashSet<>();

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Properties() {
        // Constructor mặc định
    }

    public Properties(String title, String description, double price, double area, String status, Date createdAt, Date updatedAt) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.area = area;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần
}
