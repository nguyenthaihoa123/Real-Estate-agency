package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.DTO.FeedBackDTO;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.property.Post;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.FeedBack;
import com.example.real_estate_agency.models.user.Role;
import com.example.real_estate_agency.repository.BookTourRepository;
import com.example.real_estate_agency.repository.property.PropertyRepository;
import com.example.real_estate_agency.repository.user.ClientRepository;
import com.example.real_estate_agency.repository.RoleRepository;
import com.example.real_estate_agency.repository.user.FeedBackRepository;
import com.example.real_estate_agency.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.real_estate_agency.models.property.Properties;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private BookTourRepository bookTourRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    @Override
    public boolean save(Client user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            Role role = roleRepository.findByName("ROLE_USER");
//            if(role == null){
//                role = checkRoleExist();
//            }
//            user.setRoles(Arrays.asList(role));
            clientRepository.save(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Client getById(Long id) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client getByUserName(String name) {
        return null;
    }

    @Override
    public FeedBack addFeedBack(Long id, FeedBack feedBack) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));

        // Liên kết feedback với client
        feedBack.setClient(client);

        // Lưu feedback vào cơ sở dữ liệu
        return feedBackRepository.save(feedBack);
    }

    @Override
    public List<FeedBackDTO> getAllFeedBack() {
        try {
            List<FeedBackDTO> result = new ArrayList<>();
            List<FeedBack> allFeed = feedBackRepository.findAll();
            for (FeedBack feedBack: allFeed){
                result.add(new FeedBackDTO(feedBack.getClient().getUsername(),feedBack.getContent()));
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BookTour createBookTour(BookTour bookTour, Properties properties) {
        try{
            Statistical statistical = properties.getStatistical();
            statistical.upBook();
            properties.setStatistical(statistical);
            return bookTourRepository.save(bookTour);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Post getAllPostSaveByClientId(Long id) {
        return null;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
