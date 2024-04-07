package com.example.real_estate_agency.repository.payment;

import com.example.real_estate_agency.models.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
