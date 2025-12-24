package com.kck.suljido.store.controller;

import com.kck.suljido.store.service.StoreSearchService;
import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class StoreSearchController {
    private final StoreSearchService storeSearchService;
    @GetMapping
    public ResponseEntity<List<StoreDocument>> mainPageSearch(
            @RequestParam(required = false,name = "keyword") String keyword,
            @RequestParam(required = false,name = "category") String category,
            @RequestParam(required = false,name = "lat") Double lat,
            @RequestParam(required = false,name = "lon") Double lon,
            @PageableDefault(size=20) Pageable pageable
    ){
        return ResponseEntity.ok(storeSearchService.searchStores(keyword,category,lat,lon,pageable));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<StoreDocument>> autocomplete(@RequestParam(name = "keyword") String keyword){
        return ResponseEntity.ok(storeSearchService.autocomplete(keyword));
    }

}
