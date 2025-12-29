package com.kck.suljido.store.service;

import com.kck.suljido.store.dto.StoreDto;
import com.kck.suljido.store.entity.enums.Category;

import java.util.List;

public interface StoreService {
    public List<StoreDto.SearchAllResponse> searchAll();

    List findStoresNearby(double lat, double lng, double km);

    List findStoresByRadiusAndCategory(double lat, double lng, double km, Category category);
}
