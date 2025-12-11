package com.kck.suljido.review.repository;

import com.kck.suljido.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByStoreId(Long storeId, Pageable pageable);
}
