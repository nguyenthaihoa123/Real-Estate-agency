package com.example.real_estate_agency.models.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

//    @JsonIgnoreProperties("roles")
//    @JsonBackReference
    @ManyToMany(mappedBy="roles")
    private List<Client> users;

    public Role() {
    }

    public Role(Long id, String name, List<Client> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Client> getUsers() {
        return users;
    }

    public void setUsers(List<Client> users) {
        this.users = users;
    }
}