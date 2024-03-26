package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.user.Agent;

import java.util.List;

public interface AgentService {
    Agent save(Agent agent);
    List<Agent> getAll();
}
