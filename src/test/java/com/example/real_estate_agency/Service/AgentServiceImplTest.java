package com.example.real_estate_agency.Service;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.payment.PackagePosting;
import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.RateReport;
import com.example.real_estate_agency.repository.BookTourRepository;
import com.example.real_estate_agency.repository.payment.PackagePostingRepository;
import com.example.real_estate_agency.repository.payment.PaymentRepository;
import com.example.real_estate_agency.repository.user.AgentRepository;
import com.example.real_estate_agency.repository.user.RateReportRepository;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.Impl.AgentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AgentServiceImplTest {
    @Mock
    private AgentRepository agentRepository;

    @Mock
    private PackagePostingRepository packagePostingRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private BookTourRepository bookTourRepository;

    @Mock
    private RateReportRepository rateReportRepository;

    @InjectMocks
    private AgentService agentService = new AgentServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAgent() {
        Agent agent = new Agent("test_user", "password", "test@example.com", "123456789", "Test Address", "Test Company", "Active");
        when(agentRepository.save(agent)).thenReturn(agent);

        Agent savedAgent = agentService.save(agent);

        assertEquals("test_user", savedAgent.getUsername());
        assertEquals("password", savedAgent.getPassword());
        assertEquals("test@example.com", savedAgent.getEmail());
        assertEquals("123456789", savedAgent.getPhone());
        assertEquals("Test Address", savedAgent.getAddress());
        assertEquals("Test Company", savedAgent.getCompany());
        assertEquals("Active", savedAgent.getStatus());

        verify(agentRepository, times(1)).save(agent);
    }

    @Test
    void getAllAgents() {
        List<Agent> agentList = Arrays.asList(
                new Agent("test_user1", "password1", "test1@example.com", "1234567891", "Test Address 1", "Test Company 1", "Active"),
                new Agent("test_user2", "password2", "test2@example.com", "1234567892", "Test Address 2", "Test Company 2", "Inactive")
        );
        when(agentRepository.findAll()).thenReturn(agentList);
        List<Agent> agents = agentService.getAll();
        assertEquals(2, agents.size());
        assertEquals("test_user1", agents.get(0).getUsername());
        assertEquals("password1", agents.get(0).getPassword());
        assertEquals("test1@example.com", agents.get(0).getEmail());
        assertEquals("1234567891", agents.get(0).getPhone());
        assertEquals("Test Address 1", agents.get(0).getAddress());
        assertEquals("Test Company 1", agents.get(0).getCompany());
        assertEquals("Active", agents.get(0).getStatus());

        assertEquals("test_user2", agents.get(1).getUsername());
        assertEquals("password2", agents.get(1).getPassword());
        assertEquals("test2@example.com", agents.get(1).getEmail());
        assertEquals("1234567892", agents.get(1).getPhone());
        assertEquals("Test Address 2", agents.get(1).getAddress());
        assertEquals("Test Company 2", agents.get(1).getCompany());
        assertEquals("Inactive", agents.get(1).getStatus());

        verify(agentRepository, times(1)).findAll();
    }

    @Test
    void findByEmail() {
        Agent agent = new Agent("test_user", "password", "test@example.com", "123456789", "Test Address", "Test Company", "Active");
        when(agentRepository.findByEmail("test@example.com")).thenReturn(agent);

        Agent foundAgent = agentService.findByEmail("test@example.com");

        assertEquals("test_user", foundAgent.getUsername());
        assertEquals("password", foundAgent.getPassword());
        assertEquals("test@example.com", foundAgent.getEmail());
        assertEquals("123456789", foundAgent.getPhone());
        assertEquals("Test Address", foundAgent.getAddress());
        assertEquals("Test Company", foundAgent.getCompany());
        assertEquals("Active", foundAgent.getStatus());

        verify(agentRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void findById() {
        Agent agent = new Agent("test_user", "password", "test@example.com", "123456789", "Test Address", "Test Company", "Active");
        when(agentRepository.findById(1L)).thenReturn(Optional.of(agent));

        Agent foundAgent = agentService.findById(1L);

        assertEquals("test_user", foundAgent.getUsername());
        assertEquals("password", foundAgent.getPassword());
        assertEquals("test@example.com", foundAgent.getEmail());
        assertEquals("123456789", foundAgent.getPhone());
        assertEquals("Test Address", foundAgent.getAddress());
        assertEquals("Test Company", foundAgent.getCompany());
        assertEquals("Active", foundAgent.getStatus());

        verify(agentRepository, times(1)).findById(1L);
    }

    @Test
    void findPackageByPrice() {
        PackagePosting packagePosting = new PackagePosting();
        when(packagePostingRepository.findByPrice(1000.0)).thenReturn(packagePosting);

        PackagePosting foundPackage = agentService.findPackageByPrice(1000.0);

        assertEquals(packagePosting, foundPackage);
        verify(packagePostingRepository, times(1)).findByPrice(1000.0);
    }

    @Test
    void savePayment() {
        Payment payment = new Payment();
        agentService.savePaymet(payment);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void getAllPayments() {
        List<Payment> paymentList = Arrays.asList(
                new Payment(),
                new Payment()
        );
        when(paymentRepository.findAll()).thenReturn(paymentList);
        List<Payment> payments = agentService.getAllPayment();
        assertEquals(2, payments.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void deleteAgentById() {
        agentService.deleteById(1L);
        verify(agentRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllBookTour() {
        Agent agent = new Agent();
        List<BookTour> bookTours = Arrays.asList(new BookTour(), new BookTour());
        when(bookTourRepository.findByProperty_Agent(agent)).thenReturn(bookTours);

        List<BookTour> foundBookTours = agentService.getAllBookTour(agent);

        assertEquals(2, foundBookTours.size());
        verify(bookTourRepository, times(1)).findByProperty_Agent(agent);
    }

    @Test
    void getAllRateByAgent() {
        Agent agent = new Agent();
        List<RateReport> rateReports = Arrays.asList(new RateReport(), new RateReport());
        when(rateReportRepository.findByAgent(agent)).thenReturn(rateReports);

        List<RateReport> foundRateReports = agentService.getAllRateByAgent(agent);

        assertEquals(2, foundRateReports.size());
        verify(rateReportRepository, times(1)).findByAgent(agent);
    }
}
