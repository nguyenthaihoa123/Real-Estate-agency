package com.example.real_estate_agency.repository;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookTourRepository extends JpaRepository<BookTour, Long> {
    List<BookTour> findByClient(Client client);
    BookTour findByClient_IdAndProperty_Id(Long clientId, Long propertyId);
    List<BookTour> findByProperty_Agent(Agent agent);
}
