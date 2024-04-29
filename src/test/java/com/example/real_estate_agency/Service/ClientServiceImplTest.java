package com.example.real_estate_agency.Service;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.FeedBack;
import com.example.real_estate_agency.models.user.RateReport;
import com.example.real_estate_agency.repository.BookTourRepository;
import com.example.real_estate_agency.repository.RoleRepository;
import com.example.real_estate_agency.repository.property.PropertyRepository;
import com.example.real_estate_agency.repository.property.SavePostRepository;
import com.example.real_estate_agency.repository.user.ClientRepository;
import com.example.real_estate_agency.repository.user.FeedBackRepository;
import com.example.real_estate_agency.repository.user.RateReportRepository;
import com.example.real_estate_agency.service.ClientService;
import com.example.real_estate_agency.service.Impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private FeedBackRepository feedBackRepository;

    @Mock
    private BookTourRepository bookTourRepository;

    @Mock
    private SavePostRepository savePostRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private RateReportRepository rateReportRepository;

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveClient_Success() {
        Client client = new Client("test_user", "password", "test@example.com");
        when(passwordEncoder.encode(client.getPassword())).thenReturn("hashed_password");
        when(clientRepository.save(client)).thenReturn(client);

        boolean saved = clientService.save(client);

        assertTrue(saved);
        assertEquals("hashed_password", client.getPassword());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void saveClient_Failure() {
        Client client = new Client("test_user", "password", "test@example.com");
        when(passwordEncoder.encode(client.getPassword())).thenReturn("hashed_password");
        when(clientRepository.save(client)).thenThrow(new RuntimeException());

        boolean saved = clientService.save(client);

        assertFalse(saved);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void getById() {
        Client client = new Client("test_user", "password", "test@example.com");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client foundClient = clientService.getById(1L);

        assertEquals(client, foundClient);
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        clientService.deleteById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void getByEmail() {
        Client client = new Client("test_user", "password", "test@example.com");
        when(clientRepository.findByEmail("test@example.com")).thenReturn(client);

        Client foundClient = clientService.getByEmail("test@example.com");

        assertEquals(client, foundClient);
        verify(clientRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void addFeedBack() {
        Client client = new Client("test_user", "password", "test@example.com");
        FeedBack feedBack = new FeedBack();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(feedBackRepository.save(feedBack)).thenReturn(feedBack);

        FeedBack addedFeedBack = clientService.addFeedBack(1L, feedBack);

        assertEquals(feedBack, addedFeedBack);
        assertEquals(client, addedFeedBack.getClient());
        verify(feedBackRepository, times(1)).save(feedBack);
    }

    @Test
    void createBookTour_Success() {
        Client client = new Client("test_user", "password", "test@example.com");
        Properties properties = new Properties();
        BookTour bookTour = new BookTour();
        when(bookTourRepository.save(bookTour)).thenReturn(bookTour);

        BookTour createdBookTour = clientService.createBookTour(bookTour, properties);

        assertEquals(bookTour, createdBookTour);
        verify(bookTourRepository, times(1)).save(bookTour);
    }

    @Test
    void createBookTour_Failure() {
        Client client = new Client("test_user", "password", "test@example.com");
        Properties properties = new Properties();
        BookTour bookTour = new BookTour();
        when(bookTourRepository.save(bookTour)).thenThrow(new RuntimeException());

        BookTour createdBookTour = clientService.createBookTour(bookTour, properties);

        assertNull(createdBookTour);
        verify(bookTourRepository, times(1)).save(bookTour);
    }

    @Test
    void updateBookTour() {
        BookTour bookTour = new BookTour();
        when(bookTourRepository.save(bookTour)).thenReturn(bookTour);

        BookTour updatedBookTour = clientService.updateBookTour(bookTour);

        assertEquals(bookTour, updatedBookTour);
        verify(bookTourRepository, times(1)).save(bookTour);
    }
    @Test
    void getAllSavePost() {
        Client client = new Client("test_user", "password", "test@example.com");
        clientService.getAllSavePost(client);
        verify(savePostRepository, times(1)).findByClient(client);
    }

    @Test
    void getAllBookTour() {
        Client client = new Client("test_user", "password", "test@example.com");
        clientService.getAllBookTour(client);
        verify(bookTourRepository, times(1)).findByClient(client);
    }

    @Test
    void getInfoBooking() {
        clientService.getInfoBooking(1L, 1L);
        verify(bookTourRepository, times(1)).findByClient_IdAndProperty_Id(1L, 1L);
    }

    @Test
    void getAllClient() {
        clientService.getAllClient();
        verify(clientRepository, times(1)).findByRolesName("ROLE_USER");
    }

    @Test
    void createRate() {
        RateReport rateReport = new RateReport();
        clientService.createRate(rateReport);
        verify(rateReportRepository, times(1)).save(rateReport);
    }


}
