package com.example.real_estate_agency.repository.property;

import com.example.real_estate_agency.models.payment.TransactionType;
import com.example.real_estate_agency.models.property.Category;
import com.example.real_estate_agency.models.property.Properties;
import com.example.real_estate_agency.models.user.Agent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Properties,Long> {
    List<Properties> findByTransactionType(TransactionType transactionType);
    Page<Properties> findByTransactionType(TransactionType transactionType, Pageable pageable);

    // Tìm kiếm theo tiêu đề (title) và danh mục (category) và tên loại giao dịch (transactionTypeName)
    Page<Properties> findByTitleContainingAndCategoryNameContainingAndTransactionTypeNameContaining(Pageable pageable, String title, String category, String transactionTypeName);
    Page<Properties> findByTitleContainingAndTransactionTypeNameContaining(Pageable pageable, String title, String transactionTypeName);
    Page<Properties> findByCategoryNameContainingAndTransactionTypeNameContaining(Pageable pageable, String category, String transactionTypeName);
    Page<Properties> findByTitleContainingAndCategoryNameContaining(Pageable pageable, String title, String category);
    Page<Properties> findByTitleContaining(Pageable pageable, String title);
    Page<Properties> findByCategoryNameContaining(Pageable pageable, String category);
    Page<Properties> findByTransactionTypeNameContaining(Pageable pageable, String transactionTypeName);
//    ============================================
    List<Properties> findByAgent(Agent agent);
    List<Properties> findByAgentAndCategory(Agent agent, Category category);
    List<Properties> findByAgentAndTransactionType(Agent agent, TransactionType transactionType);



}
