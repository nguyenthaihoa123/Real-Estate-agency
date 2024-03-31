package com.example.real_estate_agency.controller;

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

    @PostMapping("/savePost")
    public String saveProperty(@RequestParam("propertyIdInput") Long id,
                               @AuthenticationPrincipal UserDetails userDetails) {
        // Thực hiện logic để lấy chi tiết của tài sản từ cơ sở dữ liệu
        // Ví dụ: gọi service để lấy tài sản từ id
        Properties properties = propertyService.getById(id);
        Client client = clientService.getByEmail(userDetails.getUsername());

        SavePost savePost = new SavePost();
        savePost.setClient(client);
        savePost.setProperty(properties);

        // Lưu thông tin vào cơ sở dữ liệu
        propertyService.savePost(savePost, properties);

        // Sau khi lưu thành công, bạn có thể thực hiện hành động khác tại đây (nếu cần)

        // Sau khi xử lý, bạn có thể chuyển hướng đến trang khác hoặc render template khác
        return "redirect:/test";
    }
}
