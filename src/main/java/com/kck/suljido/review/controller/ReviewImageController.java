package com.kck.suljido.review.controller;

import com.kck.suljido.review.entity.ReviewImage;
import com.kck.suljido.review.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review-images")
@RequiredArgsConstructor
public class ReviewImageController {
    private final ReviewImageRepository reviewImageRepository;
    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> downReviewImage(@PathVariable("imageId") Long imageId){
        ReviewImage reviewImage=reviewImageRepository.findById(imageId).orElseThrow(()-> new IllegalArgumentException("이미지가 없습니다."));
        byte[] imageDate=reviewImage.getImageData();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,"image/jpeg").body(imageDate);
    }
}
