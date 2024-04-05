package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AgentService {
    Agent save(Agent agent);
    List<Agent> getAll();

    Agent findByEmail(String email);

    Agent changePassword(Long agentId,String oldPassword, String newPassword);

    Agent getDetailsById(Long agentId);
    List<Properties> getAllPropertiesById(Long id);

    List<BookTour> getBookTourByAgentId(Long id);









}
