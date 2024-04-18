package com.example.real_estate_agency;

import com.example.real_estate_agency.config.email.EmailSenderService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class RealEstateAgencyApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealEstateAgencyApplication.class, args);
	}

}
