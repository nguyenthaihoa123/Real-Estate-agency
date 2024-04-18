package com.example.real_estate_agency.controller;


import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.DTO.PropertySaleAgentDTO;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.CategoryService;
import com.example.real_estate_agency.service.PropertyService;
import com.example.real_estate_agency.service.TransactionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/agent/profile")
public class AgentController {
    private AgentService agentService;
    private PropertyService propertyService;
    private CategoryService categoryService;
    private TransactionTypeService transactionTypeService;

    private Agent getCurrAngent(@AuthenticationPrincipal UserDetails userDetails) {
        return agentService.findByEmail(userDetails.getUsername());
    }

    @GetMapping("/detail")
    public String showDetailAgent(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Đưa ID của đại lý vào model
        Agent agent = getCurrAngent(userDetails);
        if (agent.getAvatar().isEmpty()) {
            agent.setAvatar("/img/default-agent.jpg");
        }
        model.addAttribute("agent", agent);
        model.addAttribute("isEdit", true);
        return "agent/detail";
    }

    @GetMapping("/update")
    public String showUpdateProfileAgent(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model
        Agent agent = getCurrAngent(userDetails);
        model.addAttribute("agent", agent);
        return "agent/update";
    }


}
