package com.example.real_estate_agency.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.real_estate_agency.models.Image;
import com.example.real_estate_agency.models.SavePost;
import com.example.real_estate_agency.models.property.InfoRentProperty;
import com.example.real_estate_agency.models.property.InfoSaleProperty;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.property.Statistical;
import com.example.real_estate_agency.models.user.Agent;
import com.example.real_estate_agency.models.user.Client;
import com.example.real_estate_agency.repository.ImageRepository;
import com.example.real_estate_agency.repository.payment.TransactionTypeRepository;
import com.example.real_estate_agency.repository.property.*;
import com.example.real_estate_agency.service.Impl.PropertyServiceImpl;
import com.google.api.Property;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PropertyServiceImplTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private StatisticalRepository statisticalRepository;

    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Mock
    private SavePostRepository savePostRepository;

    @Mock
    private SalePropertyRepository salePropertyRepository;

    @Mock
    private RentPropertyRepository rentPropertyRepository;

    @InjectMocks
    private PropertyServiceImpl propertyService;

    public PropertyServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Success() {
        // Tạo đối tượng property
        Properties property = new Properties();

        // Mock cho statisticalRepository.save()
        when(statisticalRepository.save(any())).thenReturn(new Statistical());

        // Mock cho propertyRepository.save()
        when(propertyRepository.save(property)).thenReturn(property);

        // Thực thi phương thức save()
        Properties savedProperty = propertyService.save(property);

        // Kiểm tra kết quả
        assertNotNull(savedProperty);
        assertNotNull(savedProperty.getStatistical());
        verify(statisticalRepository, times(1)).save(any());
        verify(propertyRepository, times(1)).save(property);
    }
    @Test
    void save_Failure() {
        // Mock cho propertyRepository.save() ném ra một RuntimeException
        when(propertyRepository.save(any())).thenThrow(new RuntimeException());

        // Thực thi phương thức save()
        Properties savedProperty = propertyService.save(new Properties());

        // Kiểm tra kết quả
        assertNull(savedProperty);
    }
    // Tiếp tục viết test cho các phương thức khác tương tự như trên
    // ...

    @Test
    void deleteProperty_Success() {
        doNothing().when(propertyRepository).deleteById(1L);

        propertyService.deleteProperty(1L);

        verify(propertyRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAll_Success(){
        List<Properties> propertiesList = Collections.singletonList(new Properties());
        when(propertyRepository.findAll()).thenReturn(propertiesList);

        List<Properties> fetchedProperties = propertyService.getAll();

        assertNotNull(fetchedProperties);
        assertEquals(1, fetchedProperties.size());
        assertEquals(propertiesList, fetchedProperties);
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    void getById_ExistingId_Success() {
        Long id = 1L;
        Properties property = new Properties();
        when(propertyRepository.findById(id)).thenReturn(Optional.of(property));

        Properties fetchedProperty = propertyService.getById(id);

        assertNotNull(fetchedProperty);
        assertEquals(property, fetchedProperty);
        verify(propertyRepository, times(1)).findById(id);
    }

    @Test
    void getById_NonExistingId_ReturnsNull() {
        Long id = 1L;
        when(propertyRepository.findById(id)).thenReturn(Optional.empty());

        Properties fetchedProperty = propertyService.getById(id);

        assertNull(fetchedProperty);
        verify(propertyRepository, times(1)).findById(id);
    }

    @Test
    void getAllByAgent_ExistingAgent_Success() {
        Agent agent = new Agent();
        List<Properties> propertiesList = new ArrayList<>();
        propertiesList.add(new Properties("Property 1", agent));
        propertiesList.add(new Properties("Property 2", agent));

        when(propertyRepository.findByAgent(agent)).thenReturn(propertiesList);
        List<Properties> foundProperties = propertyService.getAllByAgent(agent);

        assertNotNull(foundProperties);
        assertEquals(2, foundProperties.size());
        assertEquals("Property 1", foundProperties.get(0).getTitle());
        assertEquals("Property 2", foundProperties.get(1).getTitle());
        verify(propertyRepository, times(1)).findByAgent(agent);
    }

    @Test
    void getAllByAgent_NonExistingAgent_EmptyList() {
        Agent agent = new Agent();

        when(propertyRepository.findByAgent(agent)).thenReturn(new ArrayList<>());

        List<Properties> foundProperties = propertyService.getAllByAgent(agent);

        assertNotNull(foundProperties);
        assertTrue(foundProperties.isEmpty());
        verify(propertyRepository, times(1)).findByAgent(agent);
    }

    @Test
    void saveImage_Success() {
        // Tạo đối tượng image
        Image image = new Image();

        // Mock cho imageRepository.save()
        when(imageRepository.save(image)).thenReturn(image);

        // Thực thi phương thức saveImage()
        Image savedImage = propertyService.saveImage(image);

        // Kiểm tra kết quả
        assertNotNull(savedImage);
        verify(imageRepository, times(1)).save(image);
    }

    @Test
    void saveImage_Failure() {
        // Mock cho imageRepository.save() ném ra một RuntimeException
        when(imageRepository.save(any())).thenThrow(new RuntimeException());

        // Thực thi phương thức saveImage() và kiểm tra xem nó có ném ra ngoại lệ không
        assertThrows(RuntimeException.class, () -> propertyService.saveImage(new Image()));
    }







    @Test
    void getInfoSavePost_ExistingInfo_Success() {
        Long clientId = 1L;
        Long propertyId = 1L;

        // Mock cho savePostRepository.findByClientIdAndPropertyId()
        when(savePostRepository.findByClientIdAndPropertyId(clientId, propertyId)).thenReturn(new SavePost());

        // Thực thi phương thức getInfoSavePost()
        boolean infoExists = propertyService.getInfoSavePost(clientId, propertyId);

        // Kiểm tra kết quả
        assertTrue(infoExists);
        verify(savePostRepository, times(1)).findByClientIdAndPropertyId(clientId, propertyId);
    }

    @Test
    void getInfoSavePost_NonExistingInfo_Failure() {
        Long clientId = 1L;
        Long propertyId = 1L;

        // Mock cho savePostRepository.findByClientIdAndPropertyId()
        when(savePostRepository.findByClientIdAndPropertyId(clientId, propertyId)).thenReturn(null);

        // Thực thi phương thức getInfoSavePost()
        boolean infoExists = propertyService.getInfoSavePost(clientId, propertyId);

        // Kiểm tra kết quả
        assertFalse(infoExists);
        verify(savePostRepository, times(1)).findByClientIdAndPropertyId(clientId, propertyId);
    }

    @Test
    void saveContractRent_Success() {
        // Tạo đối tượng infoRentProperty
        InfoRentProperty infoRentProperty = new InfoRentProperty();

        // Mock cho rentPropertyRepository.save()
        when(rentPropertyRepository.save(infoRentProperty)).thenReturn(infoRentProperty);

        // Thực thi phương thức saveContractRent()
        InfoRentProperty savedInfoRentProperty = propertyService.saveContractRent(infoRentProperty);

        // Kiểm tra kết quả
        assertNotNull(savedInfoRentProperty);
        verify(rentPropertyRepository, times(1)).save(infoRentProperty);
    }

    @Test
    void saveContractSale_Success() {
        // Tạo đối tượng infoSaleProperty
        InfoSaleProperty infoSaleProperty = new InfoSaleProperty();

        // Thực thi phương thức saveContractSale()
        propertyService.saveContractSale(infoSaleProperty);

        // Kiểm tra kết quả
        verify(salePropertyRepository, times(1)).save(infoSaleProperty);
    }

    @Test
    void getInforRent_ExistingInfo_Success() {
        Properties properties = new Properties();

        // Mock cho rentPropertyRepository.findByProperty()
        when(rentPropertyRepository.findByProperty(properties)).thenReturn(new InfoRentProperty());

        // Thực thi phương thức getInforRent()
        InfoRentProperty infoRentProperty = propertyService.getInforRent(properties);

        // Kiểm tra kết quả
        assertNotNull(infoRentProperty);
        verify(rentPropertyRepository, times(1)).findByProperty(properties);
    }

    @Test
    void getInforRent_NonExistingInfo_Failure() {
        Properties properties = new Properties();

        // Mock cho rentPropertyRepository.findByProperty()
        when(rentPropertyRepository.findByProperty(properties)).thenReturn(null);

        // Thực thi phương thức getInforRent()
        InfoRentProperty infoRentProperty = propertyService.getInforRent(properties);

        // Kiểm tra kết quả
        assertNull(infoRentProperty);
        verify(rentPropertyRepository, times(1)).findByProperty(properties);
    }

    @Test
    void getInforSale_ExistingInfo_Success() {
        Properties properties = new Properties();

        // Mock cho salePropertyRepository.findByProperty()
        when(salePropertyRepository.findByProperty(properties)).thenReturn(new InfoSaleProperty());

        // Thực thi phương thức getInforSale()
        InfoSaleProperty infoSaleProperty = propertyService.getInforSale(properties);

        // Kiểm tra kết quả
        assertNotNull(infoSaleProperty);
        verify(salePropertyRepository, times(1)).findByProperty(properties);
    }

    @Test
    void getInforSale_NonExistingInfo_Failure() {
        Properties properties = new Properties();

        // Mock cho salePropertyRepository.findByProperty()
        when(salePropertyRepository.findByProperty(properties)).thenReturn(null);

        // Thực thi phương thức getInforSale()
        InfoSaleProperty infoSaleProperty = propertyService.getInforSale(properties);

        // Kiểm tra kết quả
        assertNull(infoSaleProperty);
        verify(salePropertyRepository, times(1)).findByProperty(properties);
    }

    @Test
    void deleteRentContract_ExistingContract_Success() {
        Long id = 1L;
        InfoRentProperty infoRentProperty = new InfoRentProperty();

        // Mock cho rentPropertyRepository.findById()
        when(rentPropertyRepository.findById(id)).thenReturn(Optional.of(infoRentProperty));

        // Thực thi phương thức deleteRentContract()
        boolean result = propertyService.deleteRentContract(id);

        // Kiểm tra kết quả
        assertTrue(result);
        verify(rentPropertyRepository, times(1)).findById(id);
        verify(rentPropertyRepository, times(1)).delete(infoRentProperty);
    }

    @Test
    void deleteRentContract_NonExistingContract_Failure() {
        Long id = 1L;

        // Mock cho rentPropertyRepository.findById()
        when(rentPropertyRepository.findById(id)).thenReturn(Optional.empty());

        // Thực thi phương thức deleteRentContract()
        boolean result = propertyService.deleteRentContract(id);

        // Kiểm tra kết quả
        assertFalse(result);
        verify(rentPropertyRepository, times(1)).findById(id);
        verify(rentPropertyRepository, never()).delete(any());
    }

}
