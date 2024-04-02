package com.example.real_estate_agency.service;

import com.example.real_estate_agency.DTO.FeedBackDTO;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.FeedBack;
import com.example.real_estate_agency.models.property.Properties;

import java.util.List;

public interface ClientService {
    boolean save(Client user);
    Client getById(Long id);

    boolean deleteById(Long id);

    Client getByEmail(String email);

    Client getByUserName(String name);

    FeedBack addFeedBack(Long id, FeedBack feedBack);

    List<FeedBackDTO> getAllFeedBack();

    BookTour createBookTour(BookTour bookTour, Properties properties);
}
