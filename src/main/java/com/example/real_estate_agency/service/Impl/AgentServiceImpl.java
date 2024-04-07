package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.payment.PackagePosting;
import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.repository.payment.PackagePostingRepository;
import com.example.real_estate_agency.repository.payment.PaymentRepository;
import com.example.real_estate_agency.repository.user.AgentRepository;
import com.example.real_estate_agency.service.AgentService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private PackagePostingRepository packagePostingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
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
    public Agent findById(Long id) {
        try {
            return agentRepository.findById(id).get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PackagePosting findPackageByPrice(double price) {
        try {
            return packagePostingRepository.findByPrice(price);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Payment savePaymet(Payment payment) {
        try {
            return paymentRepository.save(payment);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
