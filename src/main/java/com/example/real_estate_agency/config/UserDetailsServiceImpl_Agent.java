package com.example.real_estate_agency.config;


import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.Role;
import com.example.real_estate_agency.repository.user.AgentRepository;
import com.example.real_estate_agency.repository.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service("agentUserDetailsService")
public class UserDetailsServiceImpl_Agent implements UserDetailsService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Agent user = agentRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities());
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        // Trả về một danh sách quyền mặc định nếu roles là null hoặc rỗng
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_AGENT"));
    }
}