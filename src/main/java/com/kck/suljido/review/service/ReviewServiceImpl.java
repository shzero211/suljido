package com.kck.suljido.review.service;

import com.kck.suljido.common.Address;
import com.kck.suljido.review.dto.ReviewDto;
import com.kck.suljido.review.entity.Review;
import com.kck.suljido.review.entity.ReviewImage;
import com.kck.suljido.review.repository.ReviewRepository;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import com.kck.suljido.store.repository.StoreRepository;
import com.kck.suljido.store.repository.elasticsearch.StoreSearchRepository;
import com.kck.suljido.user.entity.User;
import com.kck.suljido.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final StoreSearchRepository storeSearchRepository;
    @Override
    public void createReview(User loginUser,ReviewDto.ReviewCreateRequest request, List<MultipartFile> images) {
        //주소 처리
       Address address= Address.builder()
                .fullAddress(request.getStoreAddress())
               .province(request.getProvince())
                .city(request.getCity())
                .district(request.getDistrict())
                .build();

        log.info("StoreName() : ${}",request.getStoreName().trim());
        log.info("fullAddress : ${}",request.getStoreAddress().trim());
        //가게 처리
        Store store=storeRepository.findByNameAndAddress_FullAddress(request.getStoreName().trim(),request.getStoreAddress().trim()).orElseGet(()->{

            //위도,경도 처리
            GeometryFactory geometryFactory =new GeometryFactory();
            Point location=geometryFactory.createPoint(new Coordinate(request.getLongitude(),request.getLatitude()));

            Store newStore= Store.builder()
                    .name(request.getStoreName())
                    .address(address)
                    .location(location)
                    .build();
            storeRepository.save(newStore);

            //ElasticSearch 에 검색용 가게정보 추가 로직 구현
            try {
                StoreDocument doc=StoreDocument.builder()
                        .id(newStore.getId())
                        .name(request.getStoreName())
                        .address(address)
                        .searchAddress(request.getStoreAddress())
                        .location(new GeoPoint(request.getLatitude(),request.getLongitude()))
                        .build();

                storeSearchRepository.save(doc);
            }catch (Exception e){
                log.error("ES sync failed",e);
            }
            return null;
        });

        //리뷰 처리
        Review review=Review.builder()
                .store(store)
                .content(request.getContent())
                .rating(request.getRating())
                .category(request.getCategory())
                .visitTime(request.getVisitTime())
                .user(loginUser)
                .build();

        //리뷰 이미지 처리
        if(images!=null && !images.isEmpty()){
            for(MultipartFile file:images){
                if(file.isEmpty()) continue;
                try {
                    //Byte[]는 DB 에서 가져올 때 부하가 커서 S3로 URL 만 저장하도록 추후 수정 필요
                    byte[] fileBytes=file.getBytes();
                    ReviewImage reviewImage= ReviewImage.builder()
                            .imageData(fileBytes)
                            .build();
                    review.addImage(reviewImage);
                }catch (IOException e){
                    throw new RuntimeException("이미지 처리 중 오류가 발생했습니다.",e);
                }
            }
        }

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto.FindStoreAllReviewResponse> findStoreAllReview(Long storeId) {
        Pageable pageable= PageRequest.of(0,10, Sort.by("createdAt").descending());
        Page<Review> reviews=reviewRepository.findByStoreId(storeId,pageable);
        return reviews.stream().map(ReviewDto.FindStoreAllReviewResponse::from)
                .collect(Collectors.toList());
    }
}
