package com.kck.suljido.store.service.impl;

import com.kck.suljido.store.dto.StoreDto;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.entity.enums.Category;
import com.kck.suljido.store.repository.StoreRepository;
import com.kck.suljido.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    public List<StoreDto.SearchAllResponse> searchAll(){
        List<Store> entities=storeRepository.findAll();
        return entities.stream().map(StoreDto.SearchAllResponse::from).toList();
    }

    public List findStoresNearby(double lat, double lng, double km) {
        GeometryFactory geometryFactory=new GeometryFactory();
        Point myLocation=geometryFactory.createPoint(new Coordinate(lng,lat));
       double radiusMeters=km*1000;
        List<Store> entities=storeRepository.findStoresDistance(myLocation,radiusMeters);
        return entities.stream().map(StoreDto.FindNearByStoresResponse::from).toList();
    }

    @Override
    public List findStoresByRadiusAndCategory(double lat, double lng, double km, Category category) {
        List<Store> entities=storeRepository.findStoresByRadiusAndCategory(lat, lng, km, category);
        return entities.stream().map(StoreDto.FindRadiusCategoryStoreResponse::from).toList();
    }
}
