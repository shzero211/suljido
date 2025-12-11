package com.kck.suljido.review.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.review.dto.ReviewDto;
import com.kck.suljido.review.service.ReviewService;
import com.kck.suljido.store.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<Void> createReview (@RequestPart(value = "data")ReviewDto.ReviewCreateRequest request,
                                      @RequestPart(value = "image",required = false) List<MultipartFile> images){

        reviewService.createReview(request,images);
        return new Result<>(0,null);
    }

    @GetMapping("/{storeId}")
    public Result<List<ReviewDto.FindStoreAllReviewResponse>> findAllReview(@PathVariable(name = "storeId")Long StoreId){
        List<ReviewDto.FindStoreAllReviewResponse> reviews=reviewService.findStoreAllReview(StoreId);
        return new Result<>(0,reviews);
    }
}
