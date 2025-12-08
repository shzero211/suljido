package com.kck.suljido.review.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.review.dto.ReviewDto;
import com.kck.suljido.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
}
