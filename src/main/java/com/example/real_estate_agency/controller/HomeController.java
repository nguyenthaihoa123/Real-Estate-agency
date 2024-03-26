package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.DTO.FeedBackDTO;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.service.ClientService;
import com.example.real_estate_agency.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/user")
    public String getAllProperties(Model model,@RequestParam(defaultValue = "0") int page) {
        int pageSize = 6; // Số lượng phần tử trên mỗi trang
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Properties> propertiesPage = propertyService.getAllForPage(pageable,"");
        List<Properties> propertiesList = propertiesPage.getContent();



        model.addAttribute("properties", propertiesList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertiesPage.getTotalPages());

        List<FeedBackDTO> feedbackList = clientService.getAllFeedBack();
        model.addAttribute("feedBackList", feedbackList);

        return "test/index"; // Trả về tên của file HTML (ở đây là "index.html")
    }


}
