package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.repository.user.AgentRepository;
import com.example.real_estate_agency.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;
    @Override
    public Agent save(Agent agent) {
        try {
            return agentRepository.save(agent);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Agent> getAll() {
        try {
            return agentRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Agent findByEmail(String email) {
        try {
            return agentRepository.findByEmail(email);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
