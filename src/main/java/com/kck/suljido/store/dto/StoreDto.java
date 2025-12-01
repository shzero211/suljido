package com.kck.suljido.store.dto;

import com.kck.suljido.store.entity.Store;
import org.locationtech.jts.geom.Point;

public class StoreDto {
    public record SearchAllRequest(){}
    public record SearchAllResponse(String name, String roadAddress, String jibunAddress, Double lat,Double lng,String thumbnailImage){
        public static SearchAllResponse from(Store store){
            return new SearchAllResponse(store.getName(),store.getRoadAddress(),store.getJibunAddress(),store.getLocation().getY(),store.getLocation().getX(),store.getThumbnailImage());
        }
    }
}
