package com.kck.suljido.store.dto;

import com.kck.suljido.store.entity.Store;
import lombok.Builder;
import org.locationtech.jts.geom.Point;

public class StoreDto {
    public record SearchAllRequest(){}
    @Builder
    public record SearchAllResponse(String name, String province,String city,String district,String storeAddress, Double lat,Double lng,String thumbnailImage){
        public static SearchAllResponse from(Store store){
            return SearchAllResponse.builder()
                    .name(store.getName())
                    .province(store.getAddress().getProvince())
                    .city(store.getAddress().getCity())
                    .district(store.getAddress().getDistrict())
                    .storeAddress(store.getAddress().getFullAddress())
                    .lat(store.getLocation().getY())
                    .lng(store.getLocation().getX())
                    .thumbnailImage(store.getThumbnailImage())
                    .build();
        }
    }
    @Builder
    public record FindNearByStoresResponse(Long id,String name,String fullAddress,double lng,double lat,String thumbnailImage,Double avgRating,String mainCategory,Integer reviewCount){
        public static FindNearByStoresResponse from(Store store){
            return FindNearByStoresResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .fullAddress(store.getAddress().getFullAddress())
                    .lng(store.getLocation().getX())
                    .lat(store.getLocation().getY())
                    .thumbnailImage(store.getThumbnailImage())
                    .avgRating(store.getAvgRating())
                    .mainCategory(store.getMainCategory().toString())
                    .reviewCount(store.getReviewCount())
                    .build();
        }
    }
    @Builder
    public record FindRadiusCategoryStoreResponse(Long id,String name,String fullAddress,double lng,double lat,String thumbnailImage,Double avgRating,String mainCategory,Integer reviewCount) {
        public static FindRadiusCategoryStoreResponse from(Store store){
            return FindRadiusCategoryStoreResponse.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .fullAddress(store.getAddress().getFullAddress())
                    .lng(store.getLocation().getX())
                    .lat(store.getLocation().getY())
                    .thumbnailImage(store.getThumbnailImage())
                    .avgRating(store.getAvgRating())
                    .mainCategory(store.getMainCategory().toString())
                    .reviewCount(store.getReviewCount())
                    .build();
        }
    }
}
