package com.kck.suljido.store.controller;

import com.kck.suljido.common.Result;
import com.kck.suljido.store.dto.StoreDto;
import com.kck.suljido.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/all")
    public Result<List<StoreDto.SearchAllResponse>> getAll(){
        List<StoreDto.SearchAllResponse> stores=storeService.searchAll();
        return new Result<>(stores.size(),stores);
    }

}
