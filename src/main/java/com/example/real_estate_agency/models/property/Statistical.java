package com.example.real_estate_agency.models.property;

import jakarta.persistence.*;

@Entity
@Table(name = "statistical")
public class Statistical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_save")
    private int totalSave;

    @Column(name = "total_booking")
    private int totalBooking;

    @OneToOne(mappedBy = "statistical")
    private Properties properties;

    // Constructors, getters, setters và các phương thức khác

    // Constructors
    public Statistical() {
        // Constructor mặc định
    }

    public Statistical(int totalSave, int totalBooking) {
        this.totalSave = totalSave;
        this.totalBooking = totalBooking;
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

    public int getTotalSave() {
        return totalSave;
    }

    public void setTotalSave(int totalSave) {
        this.totalSave = totalSave;
    }

    public int getTotalBooking() {
        return totalBooking;
    }

    public void setTotalBooking(int totalBooking) {
        this.totalBooking = totalBooking;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void upSave(){
        this.totalSave = this.totalSave +1;
    }
}
