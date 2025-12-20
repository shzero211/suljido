package com.kck.suljido.review.repository;

import com.kck.suljido.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review r join fetch r.images where r.store.id=:storeId ")
    Page<Review> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
