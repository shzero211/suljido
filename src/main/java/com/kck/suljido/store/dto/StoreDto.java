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

    public record FindNearByStoresResponse(Double lat,Double lng,String name){
        public static FindNearByStoresResponse from(Store store){
            return new FindNearByStoresResponse(store.getLocation().getY(), store.getLocation().getX(),store.getName());
        }
    }
}
