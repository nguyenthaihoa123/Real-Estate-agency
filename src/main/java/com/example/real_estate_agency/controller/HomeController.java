package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.DTO.FeedBackDTO;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.CategoryService;
import com.example.real_estate_agency.service.ClientService;
import com.example.real_estate_agency.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AgentService agentService;

    @GetMapping("/user")
    public String Home_Client(Model model,@RequestParam(defaultValue = "0") int page) {
        int pageSize = 6; // Số lượng phần tử trên mỗi trang
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Properties> propertiesPage = propertyService.getAllForPage(pageable,"");
        List<Properties> propertiesList = propertiesPage.getContent();
        List<CategoryDTO> categories = categoryService.getAll();

        model.addAttribute("categories", categories);
        model.addAttribute("properties", propertiesList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertiesPage.getTotalPages());

        List<FeedBackDTO> feedbackList = clientService.getAllFeedBack();
        model.addAttribute("feedBackList", feedbackList);

        return "test/index"; // Trả về tên của file HTML (ở đây là "index.html")
    }

    @GetMapping("/about")
    public String Home_About_Client() {
        return "test/about"; // Trả về tên của file HTML (ở đây là "index.html")
    }

    @GetMapping("/propertyList")
    public String Property_List_Client(Model model,@RequestParam(defaultValue = "0") int page) {
        int pageSize = 6; // Số lượng phần tử trên mỗi trang
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Properties> propertiesPage = propertyService.getAllForPage(pageable,"");
        List<Properties> propertiesList = propertiesPage.getContent();
        List<CategoryDTO> categories = categoryService.getAll();

        model.addAttribute("categories", categories);
        model.addAttribute("properties", propertiesList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertiesPage.getTotalPages());
        return "test/property-list"; // Trả về tên của file HTML (ở đây là "index.html")
    }

    @GetMapping("/propertyType")
    public String Property_Type_Client(Model model) {
        List<CategoryDTO> categories = categoryService.getAll();

        model.addAttribute("categories", categories);
        return "test/property-type"; // Trả về tên của file HTML (ở đây là "index.html")
    }

    @GetMapping("/propertyAgent")
    public String Property_Agent_Client() {
        return "test/property-agent"; // Trả về tên của file HTML (ở đây là "index.html")
    }

    @GetMapping("/contact")
    public String Contact_Page_Client() {
        return "test/contact"; // Trả về tên của file HTML (ở đây là "index.html")
    }
    @GetMapping("/agent")
    public String Home_Agent(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String name = userDetails.getUsername();
        Agent agent = agentService.findByEmail(name);
        model.addAttribute("userName", agent);
        return "agent";
    }

    @GetMapping("/test")
    public String showTest(Model model) {
        // Tìm kiếm thuộc tính theo ID
        return "test/property/test";
    }

}