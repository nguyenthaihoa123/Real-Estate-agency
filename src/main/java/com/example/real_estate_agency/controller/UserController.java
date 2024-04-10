package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.DTO.PropertySavePostClientDTO;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.FeedBack;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.ClientService;
import com.example.real_estate_agency.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private AgentService agentService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/personal-page")
    public String showPersonalPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.getByEmail(userDetails.getUsername());

        List<SavePost> savePostList = clientService.getAllSavePost(client);
        List<BookTour> bookTours = clientService.getAllBookTour(client);
//        lay tat ca property trong save
        List<Properties> listPropertySave = new ArrayList<>();
        for(SavePost savePost : savePostList){
            listPropertySave.add(savePost.getProperty());
        }
//        lay tat ca property trong book
        List<Properties> listPropertyBook = new ArrayList<>();
        for(BookTour bookTour : bookTours){
            listPropertyBook.add(bookTour.getProperty());
        }

        model.addAttribute("client", client);
        model.addAttribute("saveList", listPropertySave);
        model.addAttribute("bookList",bookTours);

        return "test/client/personalPage"; // Trả về tên của trang HTML mà bạn muốn hiển thị
    }
    @GetMapping("/feedback")
    public String showUserForm() {
        return "test/feedback"; // Trả về tên của trang HTML mà bạn muốn hiển thị
    }

    @PostMapping("/feedback")
    public String submitFeedback(@RequestParam("content") String content, @AuthenticationPrincipal UserDetails userDetails) {
        // Xử lý logic để lưu phản hồi vào cơ sở dữ liệu
//        feedbackService.saveFeedback(feedback);
        Client client = clientService.getByEmail(userDetails.getUsername());
        FeedBack feedBack = new FeedBack();
        feedBack.setContent(content);
//        System.out.println(content);
        clientService.addFeedBack(client.getId(),feedBack);

        return "redirect:/feedback"; // Trả về thông điệp thành công
    }

    @GetMapping("/save")
    public String showSavePostPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.getByEmail(userDetails.getUsername());
        List<SavePost> savePostList = clientService.getAllSavePost(client);

        List<PropertySavePostClientDTO> propertySavePostClientDTOS = new ArrayList<>();
//        lay tat ca property trong save
        List<Properties> listPropertySave = new ArrayList<>();
        for(SavePost savePost : savePostList){
            Properties properties = savePost.getProperty();
            boolean isSave = propertyService.getInfoSavePost(client.getId(),properties.getId());
            PropertySavePostClientDTO propertySavePostClientDTO = new PropertySavePostClientDTO(properties,isSave);
            propertySavePostClientDTOS.add(propertySavePostClientDTO);
        }

        model.addAttribute("client", client);
        model.addAttribute("saveList", propertySavePostClientDTOS);

        return "test/client/saveDetail"; // Trả về tên của trang HTML mà bạn muốn hiển thị
    }
    @GetMapping("/booking")
    public String showBookingPage(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.getByEmail(userDetails.getUsername());
        List<BookTour> bookTours = clientService.getAllBookTour(client);

        model.addAttribute("client", client);
        model.addAttribute("booktours", bookTours);

        return "test/client/bookingDetail"; // Trả về tên của trang HTML mà bạn muốn hiển thị
    }
    @GetMapping("/updatePass")
    public String updateProfile(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        // Tìm kiếm thuộc tính theo email
        Client client = clientService.getByEmail(userDetails.getUsername());
        model.addAttribute("client",client);
        return "test/client/updateProfile";
    }


//    @GetMapping("/testSaveP")
//    public String showTest(Model model) {
//        // Tìm kiếm thuộc tính theo ID
//        return "test/property/testSavePost";
//    }
//
//    @GetMapping("/testBooking")
//    public String showTestBooking(Model model) {
//        // Tìm kiếm thuộc tính theo ID
//        return "test/property/testBooking";
//    }
}
