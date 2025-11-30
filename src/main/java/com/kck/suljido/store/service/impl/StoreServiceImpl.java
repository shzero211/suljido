package com.kck.suljido.store.service.impl;

import com.kck.suljido.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl {
    private final StoreRepository storeRepository;
}
