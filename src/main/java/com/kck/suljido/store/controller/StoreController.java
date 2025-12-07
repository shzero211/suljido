package com.kck.suljido.store.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.store.dto.StoreDto;
import com.kck.suljido.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/all")
    public Result<List<StoreDto.SearchAllResponse>> getAll(){
        List<StoreDto.SearchAllResponse> stores=storeService.searchAll();
        return new Result<>(stores.size(),stores);
    }

    @GetMapping("/nearby")
    public Result<List<StoreDto.FindNearByStoresResponse>> getNearbyStores(@RequestParam double lat,@RequestParam double lng,@RequestParam double km){
        List stores=storeService.findStoresNearby(lat,lng,km);
        return new Result<>(stores.size(),stores);
    }

}
