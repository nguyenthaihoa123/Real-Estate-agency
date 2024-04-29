package com.example.real_estate_agency.controller;


import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.DTO.PropertyRentAgentDTO;
import com.example.real_estate_agency.DTO.PropertySaleAgentDTO;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.InfoRentProperty;
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
    @GetMapping("/detail")
    public String showDetailAgent(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Đưa ID của đại lý vào model
//        model.addAttribute("agentId", id);
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        model.addAttribute("agent",agent);
        model.addAttribute("isEdit",true);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "agent/detail";
    }

    @GetMapping("/update")
    public String showUpdateProfileAgent(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        model.addAttribute("agent",agent);
        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "agent/update";
    }






}
