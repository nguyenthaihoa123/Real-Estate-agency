package com.example.real_estate_agency.DTO;

public class FeedBackDTO {
    private String nameUser;
    private String content;

    public FeedBackDTO() {
    }

    public FeedBackDTO(String nameUser, String content) {
        this.nameUser = nameUser;
        this.content = content;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
