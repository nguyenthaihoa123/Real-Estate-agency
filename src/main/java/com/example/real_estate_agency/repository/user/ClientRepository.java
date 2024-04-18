package com.example.real_estate_agency.repository.user;

import com.example.real_estate_agency.models.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByEmail(String email);
    List<Client> findByRolesName(String roleName);

}
