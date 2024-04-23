package com.example.real_estate_agency.models.user;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rate_reports")
public class RateReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rating;
    private String comment;

    private String name_Client;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

    // Constructors, getters, and setters

    public RateReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getName_Client() {
        return name_Client;
    }

    public void setName_Client(String name_Client) {
        this.name_Client = name_Client;
    }
}
