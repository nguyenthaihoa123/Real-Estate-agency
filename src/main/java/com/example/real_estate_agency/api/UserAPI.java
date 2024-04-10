package com.example.real_estate_agency.api;


import com.example.real_estate_agency.DTO.FeedBackDTO;
import com.example.real_estate_agency.DTO.UpdatePasswordRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserAPI {

    @Autowired
    private AgentService agentService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/agents")
    public ResponseEntity<List<Agent>> getAllAgents() {
        try {
            List<Agent> agents = agentService.getAll();
            if (agents != null && !agents.isEmpty()) {
                return new ResponseEntity<>(agents, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/agents")
    public ResponseEntity<Agent> addAgent(@RequestBody Agent agent) {
        try {
            Agent savedAgent = agentService.save(agent);
            System.out.println(agent.getEmail());
            if (savedAgent != null) {
                return new ResponseEntity<>(savedAgent, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/agents/update")
    public ResponseEntity<Agent> updateAgentProfile(@RequestBody Agent agentDetails, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Tìm đại lý cần cập nhật thông tin trong cơ sở dữ liệu
            Agent agentToUpdate = agentService.findByEmail(userDetails.getUsername());

            // Kiểm tra xem đại lý có tồn tại hay không
            if (agentToUpdate == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Trả về mã lỗi 404 nếu không tìm thấy đại lý
            }

            // Cập nhật thông tin của đại lý với thông tin mới từ agentDetails
            agentToUpdate.setUsername(agentDetails.getUsername());
            agentToUpdate.setEmail(agentDetails.getEmail());
            agentToUpdate.setPhone(agentDetails.getPhone());
            agentToUpdate.setAddress(agentDetails.getAddress());
            agentToUpdate.setCompany(agentDetails.getCompany());
            agentToUpdate.setAvatar(agentDetails.getAvatar());

            // Lưu thông tin đã cập nhật vào cơ sở dữ liệu
            Agent updatedAgent = agentService.save(agentToUpdate);

            return new ResponseEntity<>(updatedAgent, HttpStatus.OK); // Trả về mã 200 OK cùng với thông tin của đại lý đã cập nhật
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Trả về mã lỗi 500 nếu có lỗi xảy ra
        }
    }
    @PostMapping("/feedback/clients/{clientId}")
    public ResponseEntity<FeedBack> addFeedBack(@PathVariable Long clientId, @RequestBody FeedBack feedBack) {
        FeedBack savedFeedBack = clientService.addFeedBack(clientId, feedBack);
        return new ResponseEntity<>(savedFeedBack, HttpStatus.CREATED);
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBack() {
        List<FeedBackDTO> feedbackList = clientService.getAllFeedBack();
        if (feedbackList != null && !feedbackList.isEmpty()) {
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/savePost")
    public ResponseEntity<String> saveProperty(@RequestParam("propertyIdInput") Long id,
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

        // Trả về response với HTTP status code 200 OK và một thông điệp
        return new ResponseEntity<>("Save property successful", HttpStatus.OK);
    }
    @PostMapping("/saveBooking")
    public ResponseEntity<String> saveBooking(@RequestParam("propertyIdInput") Long id,
                                              @RequestParam("messInput") String mess,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Lấy thông tin của tài sản và người dùng từ cơ sở dữ liệu
            Properties properties = propertyService.getById(id);
            Client client = clientService.getByEmail(userDetails.getUsername());

            BookTour existingBooking = clientService.getInfoBooking(client.getId(), properties.getId());

            if (existingBooking != null) {
                // Cập nhật thông tin của đặt tour hiện có
                existingBooking.setMessage(mess);
                clientService.updateBookTour(existingBooking);
                return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
            } else {
                // Tạo một đặt tour mới nếu không có đặt tour tồn tại
                BookTour bookTour = new BookTour();
                bookTour.setClient(client);
                bookTour.setProperty(properties);
                bookTour.setMessage(mess);
                bookTour.setCancel(false);

                // Lưu thông tin đặt tour vào cơ sở dữ liệu
                clientService.createBookTour(bookTour, properties);
                return new ResponseEntity<>("Booking saved successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            // Trả về response với HTTP status code 500 Internal Server Error nếu có lỗi xảy ra
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateBooking")
    public ResponseEntity<String> updateBooking(@RequestParam("clientId") Long clientId,
                                                @RequestParam("propertyId") Long propertyId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Tìm kiếm và cập nhật thông tin đặt tour
            BookTour existingBooking = clientService.getInfoBooking(clientId, propertyId);
            if (existingBooking !=null) {
                existingBooking.setCancel(true);
                clientService.updateBookTour(existingBooking);
                return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Trả về response với HTTP status code 500 Internal Server Error nếu có lỗi xảy ra
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updatePass")
    public ResponseEntity<String> updatePass(@RequestBody UpdatePasswordRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Client client = clientService.getByEmail(userDetails.getUsername());
            // So sánh mật khẩu hiện tại
            if (!passwordEncoder.matches(request.getCurrentPassword(), client.getPassword())) {
                // Mật khẩu hiện tại không khớp
                // Xử lý lỗi hoặc trả về thông báo lỗi
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
            }

            // Kiểm tra mật khẩu mới và mật khẩu xác nhận
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                // Mật khẩu mới và mật khẩu xác nhận không khớp
                // Xử lý lỗi hoặc trả về thông báo lỗi
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirm password do not match");
            }

            // Mã hóa mật khẩu mới trước khi lưu vào DB

            // Lưu mật khẩu mới vào cơ sở dữ liệu
            client.setPassword(request.getNewPassword());
            clientService.save(client);

            // Xử lý thành công, ví dụ: chuyển hướng đến trang thành công
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            // Trả về response với HTTP status code 500 Internal Server Error nếu có lỗi xảy ra
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update booking", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}