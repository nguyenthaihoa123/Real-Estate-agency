package com.example.real_estate_agency.models.property;

import com.example.real_estate_agency.models.user.Agent;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String status;

    @OneToOne
    @JoinColumn(name = "property_id")
    private Properties property;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Post() {
        // Constructor mặc định
    }

    public Post(String title, String content, String status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần

}
