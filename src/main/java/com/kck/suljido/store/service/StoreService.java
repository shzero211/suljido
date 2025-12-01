package com.kck.suljido.store.service;

import com.kck.suljido.store.dto.StoreDto;

import java.util.List;

public interface StoreService {
    public List<StoreDto.SearchAllResponse> searchAll();
}
