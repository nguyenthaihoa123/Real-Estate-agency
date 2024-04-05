package com.example.real_estate_agency.models.property;

import com.example.real_estate_agency.models.*;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.user.Agent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
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



}
