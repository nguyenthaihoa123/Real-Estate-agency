package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.config.email.EmailSenderService;
import com.example.real_estate_agency.models.payment.Payment;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.service.AgentService;
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
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private EmailSenderService senderService;
    @GetMapping("/home")
    public String showHome_Admin(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        return "test/admin/index";
    }

    @GetMapping("/manage-Agent")
    public String showManageAgent(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Agent> agents = agentService.getAll();
        agents.sort(Comparator.comparing(Agent::getUsername));
        model.addAttribute("agents",agents);
        return "test/admin/manageAgent";
    }

    @GetMapping("/manage-client")
    public String showManageClient(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Client> clients = clientService.getAllClient();
        clients.sort(Comparator.comparing(Client::getUsername));
        model.addAttribute("clients",clients);
        return "test/admin/manageClient";
    }

    @GetMapping("/manage-money")
    public String showManageMoney(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Payment> payments = agentService.getAllPayment();
        reverseList(payments);
        model.addAttribute("payments",payments);
        return "test/admin/manageMoney";
    }

    @GetMapping("/manage-money/chart")
    public String showManageMoney_Chart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Payment> payments = agentService.getAllPayment();
        model.addAttribute("payments",payments);
        Map<String,Double> listByDay = getTotalAmountByDay(payments);
        Map<String,Double> listByQuarter = getTotalAmountByQuarter(payments);
        Map<Integer,Double> listByYear = getTotalAmountByYear(payments);

        model.addAttribute("byDay", listByDay);
        model.addAttribute("byQuarter",listByQuarter);
        model.addAttribute("byYear",listByYear);

        return "test/admin/chart-money";
    }

    @GetMapping("/manage-property")
    public String showManageProperty(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Properties> propertiesList = propertyService.getAll();
        // Tách ra danh sách các property có status là "UnAvailable"
        List<Properties> unavailableProperties = propertiesList.stream()
                .filter(property -> "UnAvailable".equals(property.getStatus()))
                .collect(Collectors.toList());

        // Loại bỏ những property có status là "UnAvailable" ra khỏi danh sách chính
        propertiesList.removeAll(unavailableProperties);

        // Thêm các property có status là "UnAvailable" vào đầu danh sách
        propertiesList.addAll(0, unavailableProperties);
        model.addAttribute("properties",propertiesList);
        return "test/admin/manageProperty";
    }

    @GetMapping("/manage-client/update/{id}")
    public String showUpdateClient(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails, Model model) {
        System.out.println(id);
        Client client = clientService.getById(id);
        model.addAttribute("client",client);
        return "test/admin/updatePassClient";
    }

    @GetMapping("/manage-agent/update/{id}")
    public String showUpdateAgent(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails, Model model) {
        System.out.println(id);
        Agent agent = agentService.findById(id);
        model.addAttribute("agent",agent);
        return "test/admin/updatePassAgent";
    }
    // Thống kê theo ngày trong tháng


    public Map<String, Double> getTotalAmountByDay(List<Payment> payments) {
        // Tạo một LinkedHashMap để lưu tổng số tiền cho mỗi ngày với thứ tự chèn được duy trì
        Map<String, Double> totalAmountByDay = new LinkedHashMap<>();

        // Lặp qua danh sách thanh toán và lấy ra tất cả các ngày
        for (Payment payment : payments) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(payment.getCreatedAt());

            // Định dạng ngày tháng năm thành chuỗi
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateKey = dateFormat.format(cal.getTime());

            // Tính tổng số tiền cho mỗi ngày và cập nhật vào Map
            double amount = payment.getPackagePosting().getPrice();
            totalAmountByDay.put(dateKey, totalAmountByDay.getOrDefault(dateKey, 0.0) + amount);
        }

        // Đảo ngược thứ tự của Map
        Map<String, Double> reversedMap = new LinkedHashMap<>();
        List<String> keys = new ArrayList<>(totalAmountByDay.keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            String key = keys.get(i);
            reversedMap.put(key, totalAmountByDay.get(key));
        }

        return reversedMap;
    }

    public Map<String, Double> getTotalAmountByQuarter(List<Payment> payments) {
        Map<String, Double> totalAmountByQuarter = new HashMap<>();

        // Lặp qua danh sách thanh toán và tính tổng số tiền cho mỗi quý
        for (Payment payment : payments) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(payment.getCreatedAt());
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            // Tính quý từ tháng (1-3 là quý 1, 4-6 là quý 2, 7-9 là quý 3, 10-12 là quý 4)
            int quarter = (month / 3) + 1;
            String quarterKey = "Q" + quarter + "-" + year;

            double amount = payment.getPackagePosting().getPrice();
            totalAmountByQuarter.put(quarterKey, totalAmountByQuarter.getOrDefault(quarterKey, 0.0) + amount);
        }
        return totalAmountByQuarter;
    }

    public Map<Integer, Double> getTotalAmountByYear(List<Payment> payments) {
        Map<Integer, Double> totalAmountByYear = new HashMap<>();

        // Lặp qua danh sách thanh toán và tính tổng số tiền cho mỗi năm
        for (Payment payment : payments) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(payment.getCreatedAt());
            int year = cal.get(Calendar.YEAR);

            double amount = payment.getPackagePosting().getPrice();
            totalAmountByYear.put(year, totalAmountByYear.getOrDefault(year, 0.0) + amount);
        }

        // Lấy danh sách các năm từ Map
        Set<Integer> years = totalAmountByYear.keySet();

        // Tính tổng số tiền cho các năm trước nếu chưa có dữ liệu
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear - 3; year <= currentYear; year++) {
            if (!years.contains(year)) {
                totalAmountByYear.put(year, 0.0);
            }
        }

        return totalAmountByYear;
    }
    private void reverseList(List<?> list) {
        int size = list.size();
        for (int i = 0; i < size / 2; i++) {
            int j = size - 1 - i;
            if (i < j) {
                Collections.swap(list, i, j);
            }
        }
    }

}
