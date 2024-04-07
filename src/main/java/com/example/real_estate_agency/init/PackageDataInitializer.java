package com.example.real_estate_agency.init;

import com.example.real_estate_agency.models.payment.PackagePosting;
import com.example.real_estate_agency.repository.payment.PackagePostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PackageDataInitializer implements ApplicationRunner {
    @Autowired
    private PackagePostingRepository packagePostingRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Kiểm tra xem có dữ liệu gói trong cơ sở dữ liệu chưa
        if (packagePostingRepository.count() == 0) {
            // Nếu không có, thêm 3 gói mới
            addDefaultPackages();
        }
    }

    private void addDefaultPackages() {
        // Tạo danh sách 3 gói mặc định
        List<PackagePosting> defaultPackages = new ArrayList<>();
        defaultPackages.add(new PackagePosting("Gói 1", "2 lượt đăng", 2, 100000.0));
        defaultPackages.add(new PackagePosting("Gói 2", "4 lượt đăng", 4, 200000.0));
        defaultPackages.add(new PackagePosting("Gói 3", "6 lượt đăng", 6, 300000.0));

        // Lưu danh sách gói vào cơ sở dữ liệu
        packagePostingRepository.saveAll(defaultPackages);
    }
}
