package com.example.real_estate_agency.models.property;

import com.example.real_estate_agency.models.*;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.user.Agent;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String address;
    private String thumbnail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionType transactionType;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Image> images;
    @JsonIgnore
    @OneToOne(mappedBy = "property")
    private Post post;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "statistical_id")
    private Statistical statistical;
    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SavePost> savedPosts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookTour> bookedTours = new HashSet<>();

    // Mối quan hệ 1-1 với PropertyRentManagement
    @JsonIgnore
    @OneToOne(mappedBy = "property", cascade = CascadeType.DETACH)
    private InfoRentProperty infoRentProperty;

    @JsonIgnore
    @OneToOne(mappedBy = "property", cascade = CascadeType.DETACH)
    private InfoSaleProperty infoSaleProperty;

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


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }



    public void setPrice(double price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Statistical getStatistical() {
        return statistical;
    }

    public void setStatistical(Statistical statistical) {
        this.statistical = statistical;
    }

    public Set<SavePost> getSavedPosts() {
        return savedPosts;
    }

    public void setSavedPosts(Set<SavePost> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public Set<BookTour> getBookedTours() {
        return bookedTours;
    }

    public void setBookedTours(Set<BookTour> bookedTours) {
        this.bookedTours = bookedTours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public InfoRentProperty getInfoRentProperty() {
        return infoRentProperty;
    }

    public void setInfoRentProperty(InfoRentProperty infoRentProperty) {
        this.infoRentProperty = infoRentProperty;
    }

    public InfoSaleProperty getInfoSaleProperty() {
        return infoSaleProperty;
    }

    public void setInfoSaleProperty(InfoSaleProperty infoSaleProperty) {
        this.infoSaleProperty = infoSaleProperty;
    }
}
