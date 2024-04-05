package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.CategoryService;
import com.example.real_estate_agency.service.PropertyService;
import com.example.real_estate_agency.service.TransactionTypeService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@AllArgsConstructor
@Controller
@RequestMapping("/property")
public class PropertyController {
    private PropertyService propertyService;
    private CategoryService categoryService;
    private TransactionTypeService transactionTypeService;
    private AgentService agentService;


    @GetMapping("/add")
    public String showAddPropertyForm(Model model) {
        List<CategoryDTO> categories = categoryService.getAll();
        List<TransactionType> transactionTypes = transactionTypeService.getAll();
        model.addAttribute("categories",categories);
        model.addAttribute("transactionTypes",transactionTypes);
        model.addAttribute("property",new Properties());
        return "test/property/add"; // Trả về tên của template HTML để hiển thị trang thêm property
    }

    @GetMapping("/update/{id}")
    public String showUpdatePropertyForm(@PathVariable("id") Long id, Model model) {
        // Tìm kiếm thuộc tính theo ID
        Optional<Properties> propertyOptional = Optional.ofNullable(propertyService.getById(id));

        if (propertyOptional.isPresent()) {
            Properties property = propertyOptional.get();
            List<CategoryDTO> categories = categoryService.getAll();
            List<TransactionType> transactionTypes = transactionTypeService.getAll();

            // Truyền thuộc tính và danh sách categories, transactionTypes vào model
            model.addAttribute("property", property);
            model.addAttribute("categories", categories);
            model.addAttribute("transactionTypes", transactionTypes);

            // Trả về tên của template HTML để hiển thị trang cập nhật property
            return "test/property/update";
        } else {
            // Trả về trang lỗi hoặc xử lý lỗi khác tùy thuộc vào yêu cầu
            return "test/404"; // Ví dụ: trang lỗi 404
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<String> updateProperty(@PathVariable Long id, @RequestBody Map<String, Object> propertyJson) {
        Properties properties = propertyService.getById(id);
        if (properties == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found");
        }

        try {
            properties.setTitle((String) propertyJson.get("title"));
            properties.setDescription((String) propertyJson.get("description"));
            String priceStr = (String) propertyJson.get("price");
            double price = Double.parseDouble(priceStr);
            properties.setPrice(price);
            String areaStr = (String) propertyJson.get("area");
            double area = Double.parseDouble(areaStr);
            properties.setArea(area);
            properties.setAddress((String) propertyJson.get("address"));
            properties.setThumbnail((String) propertyJson.get("thumbnail"));

            String categoryStr = (String) propertyJson.get("category");
            Long categoryId = Long.parseLong(categoryStr);
            String transactionTypStr = (String) propertyJson.get("transactionType");
            Long transactionTypeId = Long.parseLong(transactionTypStr);
            Category category = categoryService.findById(categoryId);
            TransactionType transactionType = transactionTypeService.findById(transactionTypeId);
            if (category == null || transactionType == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category or transaction type");
            }
            properties.setCategory(category);
            properties.setTransactionType(transactionType);
            properties.getImages().clear();
            // Lấy danh sách các URL hình ảnh từ propertyJson
            List<String> urlImageStr = (List<String>) propertyJson.get("images");
            System.out.println(urlImageStr.size());

            // Tạo danh sách các đối tượng Image
            List<Image> imageList = new ArrayList<>();

            // Kiểm tra xem danh sách hình ảnh hiện tại có rỗng không
            if (urlImageStr != null && !urlImageStr.isEmpty()) {
                // Duyệt qua mỗi URL hình ảnh và tạo đối tượng Image tương ứng
                for (String imageUrl : urlImageStr) {
                    Image image = new Image();
                    image.setUrl(imageUrl);
                    image.setProperty(properties); // Gán thuộc tính property cho mỗi hình ảnh
                    imageList.add(image); // Thêm hình ảnh vào danh sách
                }
            }

            // Nếu danh sách hình ảnh mới không rỗng, thay thế danh sách hình ảnh hiện tại bằng danh sách mới
            if (!imageList.isEmpty()) {
                propertyService.imageDeleteByProID(id);
                properties.setImages(imageList);
            }

            propertyService.save(properties);

            return ResponseEntity.ok("Property updated successfully");
        } catch (ClassCastException | NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid property data");
        }
    }


    @PostMapping("/add")
    public String addProperty(@RequestBody Map<String, Object> propertyData,
                              @AuthenticationPrincipal UserDetails userDetails) {

        // Xử lý logic để lưu property vào cơ sở dữ liệu
        String agentEmail = userDetails.getUsername();
        Agent agent = agentService.findByEmail(agentEmail);
        Date currentDate = new Date();

        Properties property = new Properties();

        property.setCreatedAt(currentDate);
        property.setUpdatedAt(currentDate);
        property.setAgent(agent);

        // Đặt các trường từ dữ liệu JSON
        propertyData.forEach((key, value) -> {
            switch (key) {
                case "title":
                    property.setTitle((String) value);
                    break;
                case "description":
                    property.setDescription((String) value);
                    break;
                case "price":
                    property.setPrice(Double.parseDouble((String) value));
                    break;
                case "area":
                    property.setArea(Double.parseDouble((String) value));
                    break;
                case "status":
                    property.setStatus((String) value);
                    break;
                case "address":
                    property.setAddress((String) value);
                    break;
                case "category":
                    // Xử lý logic để lấy đối tượng Category từ giá trị id và gán cho property
                    Category category = categoryService.findById(Long.parseLong((String) value));
                    property.setCategory(category);
                    break;
                case "transactionType":
                    // Xử lý logic tương tự để lấy đối tượng TransactionType và gán cho property
                    TransactionType transactionType = transactionTypeService.findById(Long.parseLong((String) value));
                    property.setTransactionType(transactionType);
                    break;
                case "thumbnail":
                    property.setThumbnail((String) value);
                    break;
                case "images":
                    // Xử lý danh sách các URL của các hình ảnh bổ sung
//                    property.getImages().clear();
                    List<String> imageUrls = (List<String>) value;
                    List<Image> imageList = new ArrayList<>();
                    // Lưu các URL vào danh sách các hình ảnh bổ sung của property
                    // Kiểm tra xem danh sách hình ảnh hiện tại có rỗng không
                    if (imageUrls != null && !imageUrls.isEmpty()) {
                        // Duyệt qua mỗi URL hình ảnh và tạo đối tượng Image tương ứng
                        for (String imageUrl : imageUrls) {
                            Image image = new Image();
                            image.setUrl(imageUrl);
                            image.setProperty(property); // Gán thuộc tính property cho mỗi hình ảnh
                            imageList.add(image); // Thêm hình ảnh vào danh sách
                        }
                    }

                    // Nếu danh sách hình ảnh mới không rỗng, thay thế danh sách hình ảnh hiện tại bằng danh sách mới
                    if (!imageList.isEmpty()) {
                        propertyService.imageDeleteByProID(agent.getId());
                        property.setImages(imageList);
                    }
                    break;
                default:
                    // Xử lý các trường khác nếu cần
            }
        });
//        System.out.println(property.getTransactionType().getName());
        propertyService.save(property);
        return "redirect:/agent/properties/add";
    }


}
