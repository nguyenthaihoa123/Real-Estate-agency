package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.models.user.Role;
import com.example.real_estate_agency.repository.RoleRepository;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AgentService agentService;


    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/agent/login_agent")
    public String showLoginForm_agent() {
        return "login_agent";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new Client());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Client user, RedirectAttributes redirectAttributes) {
        // Kiểm tra xem đã tồn tại tài khoản với địa chỉ email này chưa
        Client existing = clientService.getByEmail(user.getEmail());
        if (existing != null) {
            // Nếu đã tồn tại tài khoản với địa chỉ email này, thêm thông báo lỗi vào RedirectAttributes
            redirectAttributes.addFlashAttribute("error", "emailExists");
            return "redirect:/register";
        }

        // Lưu người dùng mới vào cơ sở dữ liệu
        if (clientService.save(user)) {
            // Nếu đăng ký thành công, chuyển hướng đến trang đăng nhập với thông báo đăng ký thành công
            return "redirect:/login?registered";
        } else {
            // Nếu có lỗi xảy ra trong quá trình đăng ký, chuyển hướng đến trang đăng ký với thông báo lỗi
            return "redirect:/register?error";
        }
    }

    @GetMapping("/agent/register_agent")
    public String showRegistrationForm_Agent(Model model) {
        model.addAttribute("agent", new Agent());
        return "register_agent";
    }

    @PostMapping("/agent/register_agent")
    public String registerAgent(@ModelAttribute("agent") Agent agent, RedirectAttributes redirectAttributes) {
        // Kiểm tra xem đã tồn tại tài khoản với địa chỉ email này chưa
        Agent existing = agentService.findByEmail(agent.getEmail());
        if (existing != null) {
            // Nếu đã tồn tại tài khoản với địa chỉ email này, thêm thông báo lỗi vào RedirectAttributes
            redirectAttributes.addFlashAttribute("error", "emailExists");
            return "redirect:/agent/register_agent";
        }

        agent.setStatus("active");
        agent.setNumOfPost(0);
        // Lưu agent mới vào cơ sở dữ liệu
        if (agentService.save(agent) != null) {
            // Nếu đăng ký thành công, chuyển hướng đến trang đăng nhập agent với thông báo đăng ký thành công
            return "redirect:/agent/login_agent?registered";
        } else {
            // Nếu có lỗi xảy ra trong quá trình đăng ký, chuyển hướng đến trang đăng ký agent với thông báo lỗi
            return "redirect:/agent/register_agent?error";
        }
    }


}
