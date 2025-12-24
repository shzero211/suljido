package com.kck.suljido.store.service;

import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreSearchService {
    List<StoreDocument> searchStores(String keyword, String category, Double lat, Double lon, Pageable pageable);

    List<StoreDocument> autocomplete(String keyword);
}
