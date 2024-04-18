package com.example.real_estate_agency.controller;


import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.DTO.PropertyRentAgentDTO;
import com.example.real_estate_agency.DTO.PropertySaleAgentDTO;
import com.example.real_estate_agency.models.BookTour;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.CategoryService;
import com.example.real_estate_agency.service.PropertyService;
import com.example.real_estate_agency.service.TransactionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/agent/profile")
public class AgentController {
    private AgentService agentService;
    private PropertyService propertyService;
    private CategoryService categoryService;
    private TransactionTypeService transactionTypeService;
    @GetMapping("/detail")
    public String showDetailAgent(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Đưa ID của đại lý vào model
//        model.addAttribute("agentId", id);
        Agent agent = agentService.findByEmail(userDetails.getUsername());
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
    @GetMapping("/list-property")
    public String showListProperty( Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model

        Agent agent = agentService.findByEmail(userDetails.getUsername());
        List<Properties> properties = propertyService.getAllByAgent(agent);
        List<CategoryDTO> categories = categoryService.getAll();
        List<TransactionType> transactionTypes = transactionTypeService.getAll();

        model.addAttribute("transactionTypes",transactionTypes);
        model.addAttribute("categories",categories);
        model.addAttribute("agent",agent);
        model.addAttribute("properties", properties);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/list-property";
    }

    @GetMapping("/list-rent-property")
    public String showListRentProperty( Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model

        Agent agent = agentService.findByEmail(userDetails.getUsername());
        List<Properties> properties = propertyService.getAllPropertyRent(agent);

        List<PropertyRentAgentDTO> propertyRentAgentDTOS = new ArrayList<>();

        for(Properties property : properties){
            boolean tmpIsRent = propertyService.checkInfoRent(property);
            InfoRentProperty infoRentProperty = null;
            if (tmpIsRent){
                infoRentProperty = propertyService.getInforRent(property);
                Date currentDate = new Date();
                Date contractEndDate = infoRentProperty.getContractEndDate();
                int daysUntilEnd = getDaysBetween(currentDate, contractEndDate);
                propertyRentAgentDTOS.add(new PropertyRentAgentDTO(property,tmpIsRent,infoRentProperty,daysUntilEnd));

            }else {
                propertyRentAgentDTOS.add(new PropertyRentAgentDTO(property,tmpIsRent,infoRentProperty));
            }
        }
        // Sắp xếp danh sách propertyRentAgentDTOS
        Collections.sort(propertyRentAgentDTOS, new Comparator<PropertyRentAgentDTO>() {
            @Override
            public int compare(PropertyRentAgentDTO o1, PropertyRentAgentDTO o2) {
                // Đưa các phần tử có tmpIsRent = true lên đầu
                if (o1.isRent() && !o2.isRent()) {
                    return -1;
                } else if (!o1.isRent() && o2.isRent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        model.addAttribute("agent",agent);
        model.addAttribute("propertiesDTO", propertyRentAgentDTOS);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/list-rent-property";
    }

    @GetMapping("/list-sale-property")
    public String showListSaleProperty( Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model

        Agent agent = agentService.findByEmail(userDetails.getUsername());
        List<Properties> properties = propertyService.getAllPropertySale(agent);

        List<PropertySaleAgentDTO> propertySaleAgentDTOS = new ArrayList<>();

        for(Properties property : properties){
            boolean tmpIsSale = propertyService.checkInfoSale(property);
            InfoSaleProperty infoSaleProperty = propertyService.getInforSale(property);
            propertySaleAgentDTOS.add(new PropertySaleAgentDTO(property, tmpIsSale, infoSaleProperty));
        }
        // Sắp xếp danh sách propertyRentAgentDTOS


        model.addAttribute("agent",agent);
        model.addAttribute("propertiesDTO", propertySaleAgentDTOS);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/list-sale-property";
    }

    @GetMapping("/list-book")
    public String showListBookTour( Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Đưa ID của đại lý vào model

        Agent agent = agentService.findByEmail(userDetails.getUsername());
        // Comparator để so sánh các book tour dựa trên trường isCancel
        Comparator<BookTour> bookTourComparator = new Comparator<BookTour>() {
            @Override
            public int compare(BookTour b1, BookTour b2) {
                // Nếu isCancel của b1 là false và của b2 là true, đặt b1 lên trên
                if (!b1.isCancel() && b2.isCancel()) {
                    return -1;
                }
                // Nếu isCancel của b1 là true và của b2 là false, đặt b2 lên trên
                else if (b1.isCancel() && !b2.isCancel()) {
                    return 1;
                }
                // Nếu cả hai đều cùng là true hoặc cùng là false, giữ nguyên thứ tự
                else {
                    return 0;
                }
            }
        };

// Lấy danh sách các book tour từ service
        List<BookTour> bookTours = agentService.getAllBookTour(agent);

// Sắp xếp danh sách book tour sử dụng Comparator đã tạo
        Collections.sort(bookTours, bookTourComparator);

// Đưa danh sách đã sắp xếp vào model
        model.addAttribute("bookTours", bookTours);

        // Trả về tên của template HTML để hiển thị trang chi tiết đại lý
        return "test/agent/list-book";
    }

    private int getDaysBetween(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }




}
