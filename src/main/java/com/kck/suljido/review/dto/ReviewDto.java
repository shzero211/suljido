package com.kck.suljido.review.dto;

import com.kck.suljido.review.entity.Review;
import com.kck.suljido.review.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewDto {
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ReviewCreateRequest{
        //가게 정보
        private String storeName;
        //주소 정보
        private String province;
        private String city;
        private String district;
        private String storeAddress; //전체 주소
        private Double latitude;
        private Double longitude;

        //리뷰 정보
        private String category;
        private Integer rating;
        private String content;
        private LocalDateTime visitTime;

    }
    public static class ReviewCreateResponse{

    }
    @Builder
    public record FindStoreAllReviewResponse(Long id, Integer rating, String category, String content, LocalDateTime createdAt, String author,
                                             List<String> imagesUrls) {
        public static FindStoreAllReviewResponse from(Review review){
            List<String> urls=review.getImages().stream().map(img->"/api/review-images/"+img.getId()).toList();
            return FindStoreAllReviewResponse.builder()
                    .id(review.getId())
                    .rating(review.getRating())
                    .category(review.getCategory())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .author(review.getUser().getNickname())
                    .imagesUrls(urls)
                    .build();
        }
    }

    public record FindMyReviewRequest(Long userId){

    }
    @Builder
    public record FindMyReviewResponse(Long id, Integer rating, String category, String content, LocalDateTime createdAt, List<String> imagesUrls) {
        public static FindMyReviewResponse from(Review review){
            List<String> urls=review.getImages().stream().map(reviewImage -> "/api/review-images/"+reviewImage.getId()).toList();
            return FindMyReviewResponse.builder()
                    .id(review.getId())
                    .rating(review.getRating())
                    .category(review.getCategory())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .imagesUrls(urls)
                    .build();
        }
    }
}
