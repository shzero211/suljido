package com.kck.suljido.review.service;

import com.kck.suljido.review.dto.ReviewDto;
import com.kck.suljido.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {
    void createReview(User loginUser, ReviewDto.ReviewCreateRequest request, List<MultipartFile> images);

    List<ReviewDto.FindStoreAllReviewResponse> findStoreAllReview(Long storeId);
}
