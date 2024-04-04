package com.example.real_estate_agency.repository.user;

import com.example.real_estate_agency.models.user.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findByEmail(String email);
}
