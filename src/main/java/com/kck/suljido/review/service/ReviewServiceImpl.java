package com.kck.suljido.review.service;

import com.kck.suljido.common.Address;
import com.kck.suljido.review.dto.ReviewDto;
import com.kck.suljido.review.entity.Review;
import com.kck.suljido.review.entity.ReviewImage;
import com.kck.suljido.review.repository.ReviewRepository;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    @Override
    public void createReview(ReviewDto.ReviewCreateRequest request, List<MultipartFile> images) {
        //주소 처리
        Address address= Address.builder()
                .fullAddress(request.getStoreAddress())
                .province(request.getProvince())
                .city(request.getCity())
                .district(request.getDistrict())
                .build();



        //가게 처리
        Store store=storeRepository.findByNameAndAddress(request.getStoreName(),address).orElseGet(()->{

            //위도,경도 처리
            GeometryFactory geometryFactory =new GeometryFactory();
            Point location=geometryFactory.createPoint(new Coordinate(request.getLongitude(),request.getLatitude()));

            Store newStore= Store.builder()
                    .name(request.getStoreName())
                    .address(address)
                    .location(location)
                    .build();
            return storeRepository.save(newStore);
        });


        //리뷰 처리
        Review review=Review.builder()
                .store(store)
                .content(request.getContent())
                .rating(request.getRating())
                .category(request.getCategory())
                .visitTime(request.getVisitTime())
                .build();

        //리뷰 이미지 처리
        if(images!=null && !images.isEmpty()){
            for(MultipartFile file:images){
                if(file.isEmpty()) continue;
                //String uploadedUrl=fileUploadService.upload(file); //이미지 S3 같은 저장소에 업로드
                String uploadUrl="";
                ReviewImage reviewImage= ReviewImage.builder()
                        .url(uploadUrl)
                        .build();
                review.addImage(reviewImage);
            }
        }
        reviewRepository.save(review);
    }
}
