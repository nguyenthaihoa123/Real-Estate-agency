package com.example.real_estate_agency.api;

import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.DTO.PropertiesApiDTO;
import com.example.real_estate_agency.DTO.PropertiesHomeDTO;
import com.example.real_estate_agency.config.email.EmailSenderService;
import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PropertyAPI {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AgentService agentService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private EmailSenderService senderService;

    @PostMapping("/properties")
    public ResponseEntity<String> addProperty(@RequestBody PropertiesApiDTO propertiesDTO) {
        try {
            // Lưu sản phẩm vào cơ sở dữ liệu
            Properties savedProduct = propertyService.save(propertiesDTO.toProduct());

            // Kiểm tra xem sản phẩm đã được lưu thành công hay không
            if (savedProduct != null) {
                // Lưu danh sách URL hình ảnh vào cơ sở dữ liệu và cập nhật liên kết giữa sản phẩm và hình ảnh
                for (Image imageUrl : propertiesDTO.getImages()) {
                    Image image = new Image();
                    image.setUrl(imageUrl.getUrl());
                    System.out.println(imageUrl.getUrl());
                    image.setProperty(savedProduct);
                    propertyService.saveImage(image);
                }
                return new ResponseEntity<>("Product with images saved successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to save product with images", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while saving product with images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/properties")
    public ResponseEntity<Map<String, Object>> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,@RequestParam(required = false) String title,
            @RequestParam(required = false) String category) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Properties> propertiesPage = propertyService.getAllByTitleAndCategoryAndTransactionTypeName(pageable,title,category,name);

            // Lọc ra chỉ các thuộc tính có trạng thái là "available"
            List<Properties> availableProperties = propertiesPage.getContent().stream()
                    .filter(property -> "Available".equalsIgnoreCase(property.getStatus()))
                    .collect(Collectors.toList());

            List<PropertiesHomeDTO> propertiesHomeDTOS = availableProperties.stream()
                    .map(properties -> {
                        PropertiesHomeDTO tmp = new PropertiesHomeDTO();
                        tmp.setId(String.valueOf(properties.getId()));
                        tmp.setTitle(properties.getTitle());
                        tmp.setDescription(properties.getDescription());
                        tmp.setPrice(properties.getPrice());
                        tmp.setArea(properties.getArea());
                        tmp.setStatus(properties.getStatus());
                        tmp.setAddress(properties.getAddress());
                        tmp.setTransactionType(properties.getTransactionType().getName());
                        tmp.setCategory(properties.getCategory().getName());
                        tmp.setThumbnail(properties.getThumbnail());
                        return tmp;
                    })
                    .collect(Collectors.toList());
            long totalElements = propertiesPage.getTotalElements();
            Map<String, Object> response = new HashMap<>();
            response.put("totalElements", totalElements);
            response.put("properties", propertiesHomeDTOS);
            if (!propertiesPage.isEmpty()) {
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/propertiesDetail/{id}")
    public ResponseEntity<Map<String, Object>> getPropertyDetail(@PathVariable Long id) {
        // Ở đây bạn thực hiện logic để lấy chi tiết của tài sản từ cơ sở dữ liệu
        // Ví dụ: gọi service để lấy tài sản từ id
        Properties properties = propertyService.getById(id);
        int statistical = properties.getStatistical().getTotalBooking();
        String category = properties.getCategory().getName();
        List<String> imageList = new ArrayList<>();
        for(Image image: properties.getImages()){
            imageList.add(image.getUrl());
        }

        // Sau khi lấy chi tiết tài sản, bạn tạo một đối tượng Map chứa thông tin của tài sản
        Map<String, Object> propertyDetail = new HashMap<>();
        // Đặt thông tin của tài sản vào Map
        propertyDetail.put("property",properties);
        propertyDetail.put("statistical",statistical);
        propertyDetail.put("category",category);
        propertyDetail.put("imageList",imageList);
        // Sau khi thiết lập thông tin tài sản vào Map, bạn trả về ResponseEntity chứa Map đó
        return new ResponseEntity<>(propertyDetail, HttpStatus.OK);
    }

    @PostMapping("/transaction-types")
    public ResponseEntity<TransactionType> addTransactionType(@RequestBody TransactionType transactionType) {
        try {
            TransactionType savedTransactionType = transactionTypeService.save(transactionType);
            if (savedTransactionType != null) {
                return new ResponseEntity<>(savedTransactionType, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/agent/list-property")
    public ResponseEntity<?> showListPropertyJson(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long transactionId,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
//            System.out.println(categoryId);
//            System.out.println(transactionId);
            System.out.println("Properties JSON: ");
            Agent agent = agentService.findByEmail(userDetails.getUsername());
            List<Properties> properties = propertyService.getAllByAgent(agent);
            List<CategoryDTO> categories = categoryService.getAll();

            // Lọc danh sách Properties dựa trên categoryId và transactionId
            List<Properties> filteredProperties = properties.stream()
                    .filter(property -> {
                        boolean matchCategory = categoryId == null || categoryId.equals(property.getCategory().getId());
                        boolean matchTransaction = transactionId == null || transactionId.equals(property.getTransactionType().getId());
                        return matchCategory && matchTransaction;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("agent", agent);
            responseData.put("properties", filteredProperties);
            responseData.put("categories", categories);


            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            // Xử lý các trường hợp ngoại lệ và trả về lỗi nếu cần
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }


    @PostMapping("/send_email")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String tenantName = request.get("tenantName");
        String propertyTitle = request.get("propertyTitle");
        String propertyAddress = request.get("propertyAddress");

        Context context = new Context();
        context.setVariable("tenantName", tenantName);
        context.setVariable("propertyTitle", propertyTitle);
        context.setVariable("propertyAddress", propertyAddress);

        try {
            // Gửi email bằng dịch vụ gửi email đã được cấu hình
            senderService.sendEmailWithHtmlTemplate(email,
                    "Information about money for rent property",
                    "test/property/email/index",context);

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    @PostMapping("/send_email_sale")
    public ResponseEntity<String> sendEmail_ThanksSale(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String BuyerName = request.get("BuyerName");

        Context context = new Context();
        context.setVariable("BuyerName", BuyerName);

        System.out.println(email);

        try {
            // Gửi email bằng dịch vụ gửi email đã được cấu hình
            senderService.sendEmailWithHtmlTemplate(email,
                    "Information about contract sale property",
                    "test/property/email/saleThanks",context);

            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    @PostMapping("/property-update/{id}")
    public ResponseEntity<String> processRequest(@PathVariable Long id) {
        try {
            // Xử lý yêu cầu tại đây với id và body được nhận từ client
            System.out.println("Received ID: " + id);
            Properties properties = propertyService.getById(id);
            properties.setStatus("Available");
            propertyService.save(properties);


            Context context = new Context();
            String email = properties.getAgent().getEmail();
            String title = properties.getTitle();
            context.setVariable("title", title);
            try {
                senderService.sendEmailWithHtmlTemplate(email, "Announcement about your property",
                        "test/property/email/ApprovedProperty",context);
            }catch (Exception e){
                e.printStackTrace();
            }
            // Trả về phản hồi thành công nếu quá trình xử lý thành công
            return ResponseEntity.ok("Request processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về phản hồi lỗi nếu có lỗi xảy ra trong quá trình xử lý
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request");
        }
    }

    @DeleteMapping("/property-delete/{id}")
    public ResponseEntity<String> deleteProperty_Admin(@PathVariable Long id) {
        try {
            // Xử lý yêu cầu tại đây với id và body được nhận từ client
            System.out.println("Received ID: " + id);
            propertyService.deleteProperty(id);
            // Trả về phản hồi thành công nếu quá trình xử lý thành công
            return ResponseEntity.ok("Request processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về phản hồi lỗi nếu có lỗi xảy ra trong quá trình xử lý
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the request");
        }
    }

    @PostMapping("/compare")
    public ResponseEntity<List<Properties>> compareProperty(@RequestBody Map<String, String> request) {
        try {
            // Lấy id của hai bất động sản từ request
            Long id1 = Long.valueOf(request.get("idProperty1"));
            Long id2 = Long.valueOf(request.get("idProperty2"));

            // Lấy thông tin của hai bất động sản từ service
            Properties properties1 = propertyService.getById(id1);
            Properties properties2 = propertyService.getById(id2);

            // Tạo danh sách chứa hai bất động sản
            List<Properties> properties = new ArrayList<>();
            properties.add(properties1);
            properties.add(properties2);

            // Trả về danh sách bất động sản trong phản hồi JSON
            return ResponseEntity.ok().body(properties);
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về một phản hồi lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
