package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.user.Client;

public interface ClientService {
    boolean save(Client user);
    Client getById(Long id);

    boolean deleteById(Long id);

    Client getByEmail(String email);

    Client getByUserName(String name);
}
