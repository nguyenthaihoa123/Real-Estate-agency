package com.example.real_estate_agency.repository.property;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavePostRepository extends JpaRepository<SavePost, Long> {
    SavePost findByClientIdAndPropertyId(Long clientId, Long propertyId);
    List<SavePost> findByClient(Client client);
}
