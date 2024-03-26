package com.example.real_estate_agency.api;

import com.example.real_estate_agency.DTO.PropertiesApiDTO;
import com.example.real_estate_agency.DTO.PropertiesHomeDTO;
import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.service.PropertyService;
import com.example.real_estate_agency.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) String name) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Properties> propertiesPage = propertyService.getAllForPage(pageable,name);

            List<PropertiesHomeDTO> propertiesHomeDTOS = propertiesPage.getContent().stream()
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
}
