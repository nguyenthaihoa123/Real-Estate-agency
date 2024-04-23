package com.example.real_estate_agency.models.user;

import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.property.Post;
import com.example.real_estate_agency.models.property.Properties;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String avatar;
    private int numOfPost;

    private int numOfProperty;

    private double rateStar;

    @JsonIgnore
    @OneToMany(mappedBy = "agent")
    private List<Payment> payments;
    @JsonIgnore
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private List<Properties> properties;

    @OneToMany(mappedBy = "agent")
    private List<Post> posts;

    @OneToMany(mappedBy = "agent")
    private List<RateReport> rateReports;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Properties> getProperties() {
        return properties;
    }

    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getNumOfPost() {
        return numOfPost;
    }

    public void setNumOfPost(int numOfPost) {
        this.numOfPost = numOfPost;
    }

    public int getNumOfProperty() {
        return numOfProperty;
    }

    public void setNumOfProperty(int numOfProperty) {
        this.numOfProperty = numOfProperty;
    }

    public double getRateStar() {
        return rateStar;
    }

    public void setRateStar(double rateStar) {
        this.rateStar = rateStar;
    }
}
