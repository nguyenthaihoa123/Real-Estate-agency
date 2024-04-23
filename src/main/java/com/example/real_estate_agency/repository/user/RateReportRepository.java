package com.example.real_estate_agency.repository.user;

import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.RateReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RateReportRepository extends JpaRepository<RateReport, Long> {
    List<RateReport> findByAgent(Agent agent);
    @Query("SELECT r FROM RateReport r WHERE r.agent = :agent AND r.name_Client = :nameClient")
    List<RateReport> findByAgentAndNameClient(@Param("agent") Agent agent, @Param("nameClient") String nameClient);
}
