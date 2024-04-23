package com.example.real_estate_agency.controller;

import com.example.real_estate_agency.DTO.CategoryDTO;
import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.service.AgentService;
import com.example.real_estate_agency.service.CategoryService;
import com.example.real_estate_agency.service.PropertyService;
import com.example.real_estate_agency.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/agent")
public class AgentPropertyController {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TransactionTypeService transactionTypeService;
    @Autowired
    private AgentService agentService;


    @GetMapping("")
    public String Home_Agent(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String name = userDetails.getUsername();
        Agent agent = agentService.findByEmail(name);
        model.addAttribute("userName", agent);
        return "agent";
    }

    @GetMapping("/property/rentContract/{id}")
    public String Property_rent_contract_Agent(@PathVariable("id") Long id,@AuthenticationPrincipal UserDetails userDetails, Model model){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);

        model.addAttribute("property",properties);
        model.addAttribute("user", agent);


        return "agent/contract/rent-contract";
    }
    @GetMapping("/properties/detail/{id}")
    public String showPropertyDetail(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Properties> propertyOptional = Optional.ofNullable(propertyService.getById(id));
        if (propertyOptional.isPresent()) {
            Agent agent = agentService.findById(propertyOptional.get().getAgent().getId());
            Properties property = propertyOptional.get();
            List<CategoryDTO> categories = categoryService.getAll();
            model.addAttribute("property", property);
            model.addAttribute("categories", categories);

            return "/agent/property/detail";
        } else {
            return "test/404";
        }
    }

    @GetMapping("/property/saleContract/{id}")
    public String Property_sale_contract_Agent(@PathVariable("id") Long id,@AuthenticationPrincipal UserDetails userDetails, Model model){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);

        model.addAttribute("property",properties);
        model.addAttribute("user", agent);


        return "test/property/contractSale";
    }

    @GetMapping("/property/update-rentContract/{id}")
    public String Update_Property_rent_contract_Agent(@PathVariable("id") Long id,@AuthenticationPrincipal UserDetails userDetails, Model model){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);
        InfoRentProperty infoRentProperty = propertyService.getInforRent(properties);
        model.addAttribute("property",properties);
        model.addAttribute("user", agent);
        model.addAttribute("infoRent",infoRentProperty);


        return "test/property/updateContractRent";
    }
    @GetMapping("/property/show-saleContract/{id}")
    public String show_Property_sale_contract_Agent(@PathVariable("id") Long id,@AuthenticationPrincipal UserDetails userDetails, Model model){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);
        InfoSaleProperty infoSaleProperty = propertyService.getInforSale(properties);
        model.addAttribute("property",properties);
        model.addAttribute("user", agent);
        model.addAttribute("infoSale",infoSaleProperty);


        return "test/property/showContractSale";
    }
    @GetMapping("/properties/add")
    public String showAddPropertyForm(Model model) {
        List<CategoryDTO> categories = categoryService.getAll();
        List<TransactionType> transactionTypes = transactionTypeService.getAll();
        model.addAttribute("categories",categories);
        model.addAttribute("transactionTypes",transactionTypes);
        model.addAttribute("property",new Properties());
        return "agent/property/add"; // Trả về tên của template HTML để hiển thị trang thêm property
    }

    @GetMapping("/properties/update/{id}")
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

    @PostMapping("/properties/update/{id}")
    public ResponseEntity<String> updateProperty(@PathVariable Long id, @RequestBody Map<String, Object> propertyJson) {
        Properties properties = propertyService.getById(id);
        if (properties == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property not found");
        }

        try {
//            properties.setImages();
            for (Image image: properties.getImages()){
                System.out.println(image.getUrl());
            }
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
//            properties.getImages().clear();
            // Lấy danh sách các URL hình ảnh từ propertyJson
            List<String> urlImageStr = (List<String>) propertyJson.get("images");
            System.out.println(urlImageStr.size());

            // Tạo danh sách các đối tượng Image
            List<Image> imageList = new ArrayList<>();

            // Kiểm tra xem danh sách hình ảnh hiện tại có rỗng không
            if (!urlImageStr.isEmpty()) {
                // Duyệt qua mỗi URL hình ảnh và tạo đối tượng Image tương ứng
                for (String imageUrl : urlImageStr) {
                    Image image = new Image();
                    image.setUrl(imageUrl);
                    image.setProperty(properties); // Gán thuộc tính property cho mỗi hình ảnh
                    imageList.add(image); // Thêm hình ảnh vào danh sách
                }
            }

//            // Nếu danh sách hình ảnh mới không rỗng, thay thế danh sách hình ảnh hiện tại bằng danh sách mới
            if (!imageList.isEmpty()) {
//                System.out.println(imageList.size());

                // Xóa tất cả các hình ảnh liên kết với bất động sản hiện tại
                properties.getImages().clear();

            // Thêm hình ảnh mới vào danh sách images của Properties
                properties.getImages().addAll(imageList);
            }

            propertyService.save(properties);

            return ResponseEntity.ok("Property updated successfully");
        } catch (ClassCastException | NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid property data");
        }
    }


    @PostMapping("/properties/add")
    public ResponseEntity<String> addProperty(@RequestBody Map<String, Object> propertyData,
                              @AuthenticationPrincipal UserDetails userDetails) {

        // Xử lý logic để lưu property vào cơ sở dữ liệu
        String agentEmail = userDetails.getUsername();
        Agent agent = agentService.findByEmail(agentEmail);
        Date currentDate = new Date();
        if(agent.getNumOfPost() > 0){
            agent.setNumOfPost(agent.getNumOfPost() - 1);
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
//                case "status":
//                    property.setStatus((String) value);
//                    break;
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
            property.setStatus("UnAvailable");
            agent.setNumOfProperty(agent.getNumOfProperty() + 1);
            agentService.save(agent);
            propertyService.save(property);
            return ResponseEntity.ok("Property added successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough posts remaining. Please purchase additional package.");
        }
    }

    @PostMapping("/property/rentContract/{id}")
    public ResponseEntity<String> createContractRent(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody InfoRentProperty request){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);
        InfoRentProperty infoRentProperty = new InfoRentProperty();

        infoRentProperty.setOwnerID(request.getOwnerID());
        infoRentProperty.setOwnerName(request.getOwnerName());
        infoRentProperty.setOwnerPhone(request.getOwnerPhone());
        infoRentProperty.setTenantID(request.getTenantID());
        infoRentProperty.setTenantName(request.getTenantName());
        infoRentProperty.setTenantDOB(request.getTenantDOB());
        infoRentProperty.setTenantPhone(request.getTenantPhone());
        infoRentProperty.setTenantEmail(request.getTenantEmail());
        infoRentProperty.setUnitPrice(request.getUnitPrice());
        infoRentProperty.setCompensationLevel(request.getCompensationLevel());
        infoRentProperty.setDeposit(request.getDeposit());
        infoRentProperty.setContractStartDate(request.getContractStartDate());
        infoRentProperty.setContractEndDate(request.getContractEndDate());
        infoRentProperty.setProperty(properties);

        try {
            InfoRentProperty infoRentProperty1 = propertyService.saveContractRent(infoRentProperty);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok("Property updated successfully");
    }

    @PostMapping("/property/update-rentContract/{id}")
    public ResponseEntity<String> updateContractRent(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody InfoRentProperty request){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);
        InfoRentProperty infoRentProperty = new InfoRentProperty();

        infoRentProperty.setId(request.getId());
        infoRentProperty.setOwnerID(request.getOwnerID());
        infoRentProperty.setOwnerName(request.getOwnerName());
        infoRentProperty.setOwnerPhone(request.getOwnerPhone());
        infoRentProperty.setTenantID(request.getTenantID());
        infoRentProperty.setTenantName(request.getTenantName());
        infoRentProperty.setTenantDOB(request.getTenantDOB());
        infoRentProperty.setTenantPhone(request.getTenantPhone());
        infoRentProperty.setTenantEmail(request.getTenantEmail());
        infoRentProperty.setUnitPrice(request.getUnitPrice());
        infoRentProperty.setCompensationLevel(request.getCompensationLevel());
        infoRentProperty.setDeposit(request.getDeposit());
        infoRentProperty.setContractStartDate(request.getContractStartDate());
        infoRentProperty.setContractEndDate(request.getContractEndDate());
        infoRentProperty.setProperty(properties);

        System.out.println("ID contract: "+infoRentProperty.getId());
        try {
            InfoRentProperty updatedContract = propertyService.saveContractRent(infoRentProperty);
            if (updatedContract != null) {
                return ResponseEntity.ok("Rent contract updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to update rent contract");
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating rent contract");
        }
    }


    @PostMapping("/property/delete-rentContract/{id}")
    public ResponseEntity<String> deleteContractRent(@PathVariable("id") Long id){
        Properties properties = propertyService.getById(id);
        InfoRentProperty infoRentProperty = propertyService.getInforRent(properties);
        System.out.println("ID contract: "+infoRentProperty.getId());
        try {
            if(propertyService.deleteRentContract(infoRentProperty.getId())){
                System.out.println("complete");
                return ResponseEntity.ok("successfully!");
            } else {
                System.out.println("false");
                return ResponseEntity.badRequest().body("Failed to delete rent contract");
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting rent contract");
        }
    }

    @PostMapping("/property/saleContract/{id}")
    public ResponseEntity<String> createContractSale(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody InfoSaleProperty request){
        Agent agent = agentService.findByEmail(userDetails.getUsername());
        Properties properties = propertyService.getById(id);
        InfoSaleProperty infoSaleProperty = new InfoSaleProperty();

        infoSaleProperty.setOwnerID(request.getOwnerID());
        infoSaleProperty.setOwnerName(request.getOwnerName());
        infoSaleProperty.setOwnerPhone(request.getOwnerPhone());

        infoSaleProperty.setBuyerID(request.getBuyerID());
        infoSaleProperty.setBuyerName(request.getBuyerName());
        infoSaleProperty.setBuyerDOB(request.getBuyerDOB());
        infoSaleProperty.setBuyerPhone(request.getBuyerPhone());
        infoSaleProperty.setBuyerEmail(request.getBuyerEmail());

        infoSaleProperty.setSellingPrice(request.getSellingPrice());
        infoSaleProperty.setCommission(request.getCommission());
        infoSaleProperty.setContractDate(request.getContractDate());

        infoSaleProperty.setProperty(properties);

        propertyService.saveContractSale(infoSaleProperty);

        return ResponseEntity.ok("Property updated successfully");
    }
}
