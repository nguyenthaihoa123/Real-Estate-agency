package com.example.real_estate_agency.models.user;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.SavePost;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FeedBack> feedbacks = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SavePost> savedPosts = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookTour> bookedTours = new HashSet<>();

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Client() {
        // Constructor mặc định
    }

    public Client(Long id, String username, String password, String email, List<Role> roles, Set<SavePost> savedPosts, Set<BookTour> bookedTours) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.savedPosts = savedPosts;
        this.bookedTours = bookedTours;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    // Getters và Setters
    // Đảm bảo rằng bạn đã tạo getters và setters cho tất cả các trường

    // Các phương thức khác nếu cần


    public Set<FeedBack> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<FeedBack> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
