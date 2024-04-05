package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.repository.user.AgentRepository;
import com.example.real_estate_agency.service.AgentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AgentServiceImpl implements AgentService {
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

    @Override
    public Agent changePassword(Long agentId, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public Agent getDetailsById(Long agentId) {
        return null;
    }

    @Override
    public List<Properties> getAllPropertiesById(Long id) {
        return null;
    }

    @Override
    public List<BookTour> getBookTourByAgentId(Long id) {
        return null;
    }
}
