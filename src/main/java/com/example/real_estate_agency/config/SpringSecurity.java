package com.example.real_estate_agency.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("agentUserDetailsService")
    private UserDetailsServiceImpl_Agent userDetailsServiceImplAgent;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers("/checkout").authenticated();
                    authorize.requestMatchers("/").permitAll();
                    authorize.requestMatchers("/admin/**").hasAnyRole("ADMIN");
//                    authorize.requestMatchers("/").hasAnyRole("USER");
                    authorize.anyRequest().permitAll();
                })
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home/")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain_Agent(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/agent/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/").permitAll();
                    authorize.requestMatchers("/home/**").permitAll();
                    authorize.requestMatchers("/agent/**").hasAnyRole("AGENT");
                    authorize.requestMatchers("/agent").authenticated();

//                    authorize.requestMatchers("/user").hasAnyRole("USER");
                    authorize.anyRequest().permitAll();
                })
                .formLogin(
                        form -> form
                                .loginPage("/agent/login_agent")
                                .loginProcessingUrl("/agent/login_agent")
                                .defaultSuccessUrl("/agent")
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        auth
                .userDetailsService(userDetailsServiceImplAgent)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}