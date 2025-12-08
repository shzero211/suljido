package com.kck.suljido.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReviewDto {
    @Getter
    @NoArgsConstructor
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
}
