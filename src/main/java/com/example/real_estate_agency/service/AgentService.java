package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.payment.PackagePosting;
import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.user.Agent;

import java.util.List;

public interface AgentService {
    Agent save(Agent agent);
    List<Agent> getAll();

    Agent findByEmail(String email);

    Agent findById(Long id);

    PackagePosting findPackageByPrice(double price);

    Payment savePaymet(Payment payment);
}
