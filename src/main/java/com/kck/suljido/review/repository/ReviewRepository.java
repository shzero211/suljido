package com.kck.suljido.review.repository;

import com.kck.suljido.review.entity.Review;
import com.kck.suljido.store.entity.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review r LEFT join fetch r.images where r.store.id=:storeId ")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
    @Query("select id, rating, category, content, createdAt from Review r where r.user.id=:userId")
    Page<Review> findMyReviewByUserId(@Param("userId") Long userId,Pageable pageable);

    @Query("SELECT r.category from Review r " +
            "WHERE r.store.id = :storeId " +
            "GROUP BY r.category " +
            "ORDER BY COUNT(r) DESC " +
            "LIMIT 1 ")
    Category findTopCategoryByStoreId(@Param("storeId") Long id);
}
