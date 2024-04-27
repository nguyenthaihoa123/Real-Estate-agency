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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

        return "admin/index";
    }

    @GetMapping("/manage-agent")
    public String showManageAgent(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Agent> agents = agentService.getAll();
        agents.sort(Comparator.comparing(Agent::getUsername));
        model.addAttribute("agents",agents);
        return "admin/agents";
    }

    @GetMapping("/manage-client")
    public String showManageClient(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Client> clients = clientService.getAllClient();
        clients.sort(Comparator.comparing(Client::getUsername));
        model.addAttribute("clients",clients);
        return "admin/clients";
    }

    @GetMapping("/manage-money")
    public String showManageMoney(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Payment> payments = agentService.getAllPayment();
        reverseList(payments);
        model.addAttribute("payments",payments);
        return "admin/moneys";
    }

    @GetMapping("/manage-money/chart")
    public String showManageMoney_Chart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Payment> payments = agentService.getAllPayment();
        model.addAttribute("payments",payments);
        Map<String,Double> listByDay = getTotalAmountByDay(payments);
        Map<String,Double> listByQuarter = getTotalAmountByQuarter(payments);
        Map<Integer,Double> listByYear = getTotalAmountByYear(payments);

        //summary
        model.addAttribute("byDay", listByDay);
        model.addAttribute("byQuarter",listByQuarter);
        model.addAttribute("byYear",listByYear);

        //chart
        model.addAttribute("chartYear", listByYear);
        model.addAttribute("chartQuarter", listByQuarter);
        model.addAttribute("chartDay", listByDay);




        return "admin/charts";
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
        return "admin/properties";
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

    @GetMapping("/manage-money/chart/export")
    public ResponseEntity<byte[]> exportChartToExcel(@AuthenticationPrincipal UserDetails userDetails) {
        // Lấy dữ liệu từ cơ sở dữ liệu
        List<Payment> payments = agentService.getAllPayment();

        // Tạo workbook Excel
        try (Workbook workbook = new XSSFWorkbook()) {
            // Tạo một sheet Excel mới
            Sheet sheet = workbook.createSheet("Chart Data");

            // Tạo dòng header cho dữ liệu theo ngày
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Day");
            headerRow.createCell(1).setCellValue("Total Amount");

            // Thêm dữ liệu theo ngày
            int rowNum = 1;
            Map<String, Double> listByDay = getTotalAmountByDay(payments);
            for (Map.Entry<String, Double> entry : listByDay.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            // Thêm dòng trống
            rowNum++;

            // Tạo dòng header cho dữ liệu theo quý
            headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Quarter");
            headerRow.createCell(1).setCellValue("Total Amount");

            // Thêm dữ liệu theo quý
            Map<String, Double> listByQuarter = getTotalAmountByQuarter(payments);
            for (Map.Entry<String, Double> entry : listByQuarter.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            // Thêm dòng trống
            rowNum++;

            // Tạo dòng header cho dữ liệu theo năm
            headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Year");
            headerRow.createCell(1).setCellValue("Total Amount");

            // Thêm dữ liệu theo năm
            Map<Integer, Double> listByYear = getTotalAmountByYear(payments);
            for (Map.Entry<Integer, Double> entry : listByYear.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            // Tạo ByteArrayOutputStream để lưu workbook vào bộ nhớ
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            // Chuyển đổi workbook thành mảng byte
            byte[] excelBytes = outputStream.toByteArray();

            // Thiết lập HTTP header cho tệp Excel
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "chart_statistics.xlsx");

            // Trả về tệp Excel
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // Trả về lỗi nếu có vấn đề xảy ra trong quá trình tạo Excel
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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
