package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.DTO.*;
import com.example.real_estate_agency.mapper.PropertyMapper;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.RateReport;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.CategoryService;
import com.example.real_estate_agency.service.ClientService;
import com.example.real_estate_agency.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AgentService agentService;


    @GetMapping("/")
    public String Home_Client(Model model,@RequestParam(defaultValue = "0") int page,@AuthenticationPrincipal UserDetails userDetails) {
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

        if(clientService.isAdmin(userDetails.getUsername())){
            model.addAttribute("isAdmin", true);
        }


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
        List<PropertyDTO> propertiesList = propertiesPage.getContent().stream().map(PropertyMapper::modelToDTO).toList();
        List<CategoryDTO> categories = categoryService.getAll();

        model.addAttribute("categories", categories);
        model.addAttribute("properties", propertiesList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", propertiesPage.getTotalPages());
        return "test/property-list"; // Trả về tên của file HTML (ở đây là "index.html")
    }
    @GetMapping("/properties/{id}")
    public String showPropertyDetail(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        // Tìm kiếm thuộc tính theo ID
        try {

            Optional<Properties> propertyOptional = Optional.ofNullable(propertyService.getById(id));
            Client client = clientService.getByEmail(userDetails.getUsername());
            if (propertyOptional.isPresent()) {
                PropertyDTO property = PropertyMapper.modelToDTO(propertyOptional.get());
                List<CategoryDTO> categories = categoryService.getAll();
                Agent  agent = property.getAgent();
                boolean isSave = propertyService.getInfoSavePost(client.getId(),property.getId());
                boolean statusRent = propertyService.checkInfoRent(PropertyMapper.DtoToModel(property));



                model.addAttribute("property", property);
                model.addAttribute("categories", categories);
                model.addAttribute("isSave", isSave);
                model.addAttribute("statusRent", statusRent);
                model.addAttribute("agent", agent);


                 return "property/detail";
            } else {
                // Trả về trang lỗi hoặc xử lý lỗi khác tùy thuộc vào yêu cầu
                return "test/404"; // Ví dụ: trang lỗi 404
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "test/404";
        }

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

    @GetMapping("/agentDetail/{agentId}")
    public String detailAgent(@PathVariable Long agentId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Tìm kiếm thuộc tính theo email
            Agent agent = agentService.findById(agentId);
            DecimalFormat df = new DecimalFormat("#.##");
            agent.setRateStar(Double.parseDouble(df.format(agent.getRateStar())));

            Client client = clientService.getByEmail(userDetails.getUsername());
            List<PropertyDTO> saleList = propertyService.getAllPropertySale(agent).stream().map(PropertyMapper::modelToDTO).toList();
            List<PropertyDTO> rentList = propertyService.getAllPropertyRent(agent).stream().map(PropertyMapper::modelToDTO).toList();
            List<RateReport> rateReportList = agentService.getAllRateByAgent(agent);
            boolean checkRate = clientService.checkRate(client.getUsername(),agent);
            model.addAttribute("agent", agent);
            model.addAttribute("rentList",rentList);
            model.addAttribute("saleList",saleList);
            model.addAttribute("rateList",rateReportList);
            model.addAttribute("checkRate",checkRate);
            System.out.println(checkRate);
            model.addAttribute("isEdit",false);
            return "user/agent/personal-page";
            // return "agent/detail";

        }catch (Exception e){
            e.printStackTrace();
            return "test/404";
        }


        //return "test/client/infoAgent";
    }



}
