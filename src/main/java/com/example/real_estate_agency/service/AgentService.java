package com.example.real_estate_agency.service;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.payment.PackagePosting;
import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.RateReport;

import java.util.List;

public interface AgentService {
    Agent save(Agent agent);
    List<Agent> getAll();

    Agent findByEmail(String email);

    Agent findById(Long id);

    PackagePosting findPackageByPrice(double price);

    void savePaymet(Payment payment);

    List<Payment> getAllPayment();

    void deleteById(Long id);

    List<BookTour> getAllBookTour(Agent agent);
    List<RateReport> getAllRateByAgent(Agent agent);
}
