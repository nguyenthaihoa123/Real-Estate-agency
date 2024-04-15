package com.example.real_estate_agency.controller;


import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
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

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/agent/profile")
public class AgentController {
    private AgentService agentService;
    private PropertyService propertyService;
    private CategoryService categoryService;
    private TransactionTypeService transactionTypeService;

    private Agent getCurrAngent(@AuthenticationPrincipal UserDetails userDetails){
        return  agentService.findByEmail(userDetails.getUsername());
    }

    @GetMapping("/detail")
    public String showDetailAgent(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Đưa ID của đại lý vào model
        Agent agent = getCurrAngent(userDetails);
        if(agent.getAvatar().isEmpty()){
            agent.setAvatar("/img/default-agent.jpg");
        }
        model.addAttribute("agent",agent);
        model.addAttribute("isEdit",true);
        return "agent/detail";
    }

    @GetMapping("/update")
    public String showUpdateProfileAgent(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model
        Agent agent = getCurrAngent(userDetails);
        model.addAttribute("agent",agent);
        return "agent/update";
    }
    @GetMapping("/list-property")
    public String showListProperty( Model model, @AuthenticationPrincipal UserDetails userDetails) {

        Agent agent = getCurrAngent(userDetails);
        List<Properties> properties = propertyService.getAllByAgent(agent);
        List<CategoryDTO> categories = categoryService.getAll();
        List<TransactionType> transactionTypes = transactionTypeService.getAll();

        model.addAttribute("transactionTypes",transactionTypes);
        model.addAttribute("categories",categories);
        model.addAttribute("agent",agent);
        model.addAttribute("properties", properties);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "agent/list-property";
    }

    @GetMapping("/list-rent-property")
    public String showListRentProperty( Model model, @AuthenticationPrincipal UserDetails userDetails) {

        Agent agent = getCurrAngent(userDetails);
        List<Properties> properties = propertyService.getAllByAgent(agent);
        List<CategoryDTO> categories = categoryService.getAll();
        List<TransactionType> transactionTypes = transactionTypeService.getAll();

        model.addAttribute("transactionTypes",transactionTypes);
        model.addAttribute("categories",categories);
        model.addAttribute("agent",agent);
        model.addAttribute("properties", properties);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/list-rent-property";
    }

}
