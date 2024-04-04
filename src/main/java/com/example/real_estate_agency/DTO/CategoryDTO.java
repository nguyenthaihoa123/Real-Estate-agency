package com.example.real_estate_agency.DTO;

public class CategoryDTO {
    private Long id;
    private String name;
    private int numberOfProperties;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name, int numberOfProperties) {
        this.id = id;
        this.name = name;
        this.numberOfProperties = numberOfProperties;
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

    public int getNumberOfProperties() {
        return numberOfProperties;
    }

    public void setNumberOfProperties(int numberOfProperties) {
        this.numberOfProperties = numberOfProperties;
    }
}
