package com.kck.suljido.review.service;

import com.kck.suljido.review.dto.ReviewDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewDto.ReviewCreateRequest request, List<MultipartFile> images);

    List<ReviewDto.FindStoreAllReviewResponse> findStoreAllReview(Long storeId);
}
