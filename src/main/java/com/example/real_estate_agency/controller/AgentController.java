package com.example.real_estate_agency.controller;


import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.service.AgentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/agent/profile")
public class AgentController {
    private AgentService agentService;
    @GetMapping("/detail/{id}")
    public String showDetailAgent(@PathVariable("id") Long id, Model model) {
        // Đưa ID của đại lý vào model
//        model.addAttribute("agentId", id);
        Agent agent = agentService.findById(id);
        model.addAttribute("agent",agent);
        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/detail";
    }

    @GetMapping("/update")
    public String showUpdateProfileAgent(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        model.addAttribute("agent",agent);
        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/update";
    }
}
