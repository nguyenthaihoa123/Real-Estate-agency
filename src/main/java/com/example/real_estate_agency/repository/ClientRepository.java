package com.example.real_estate_agency.repository;

import com.example.real_estate_agency.models.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByEmail(String email);
}
