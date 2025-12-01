package com.kck.suljido.store.service.impl;

import com.kck.suljido.store.dto.StoreDto;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.repository.StoreRepository;
import com.kck.suljido.store.service.StoreService;
import lombok.RequiredArgsConstructor;
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
}
