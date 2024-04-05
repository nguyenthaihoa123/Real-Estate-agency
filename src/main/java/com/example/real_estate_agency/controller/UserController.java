package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.FeedBack;
import com.example.real_estate_agency.service.ClientService;
import com.example.real_estate_agency.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PropertyService propertyService;

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

    @GetMapping("/agentDetail/{id}")
    public String showDetailAgent(@PathVariable("id") Long id, Model model) {
        // Đưa ID của đại lý vào model
        model.addAttribute("agentId", id);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/detail";
    }
    @GetMapping("/testSaveP")
    public String showTest(Model model) {
        // Tìm kiếm thuộc tính theo ID
        return "test/property/testSavePost";
    }

    @GetMapping("/testBooking")
    public String showTestBooking(Model model) {
        // Tìm kiếm thuộc tính theo ID
        return "test/property/testBooking";
    }
}
