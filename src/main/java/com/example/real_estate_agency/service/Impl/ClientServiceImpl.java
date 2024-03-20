package com.example.real_estate_agency.service.Impl;

import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.Role;
import com.example.real_estate_agency.repository.ClientRepository;
import com.example.real_estate_agency.repository.RoleRepository;
import com.example.real_estate_agency.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


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
        return null;
    }

    @Override
    public Client getByUserName(String name) {
        return null;
    }
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }
}
