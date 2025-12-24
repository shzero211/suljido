package com.kck.suljido.store.service.impl;

import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import com.kck.suljido.store.service.StoreSearchService;
import lombok.RequiredArgsConstructor;
import org.opensearch.data.core.OpenSearchOperations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreSearchServiceImpl implements StoreSearchService {
    private final OpenSearchOperations openSearchOperations;
    @Override
    public List<StoreDocument> searchStores(String keyword, String category, Double lat, Double lon, Pageable pageable) {
        Criteria criteria=new Criteria();

        //검색어 조건 (이름 or 주소) - must(score 점수 계산)
        if(keyword!=null&&!keyword.isBlank()){
            criteria=criteria.subCriteria(
                    new Criteria("name").matches(keyword).boost(2.0f)
                            .or("searchAddress").matches(keyword)
            );
        }

        //카테고리 조건 - filter (score 영향 없음)
        if(category!=null && !category.isBlank()){
            criteria=criteria.and(new Criteria("category").is(category));
        }

        if(lat!=null && lon !=null){
            criteria=criteria.and(new Criteria("location").within(new GeoPoint(lat,lon),"5km"));
        }

        CriteriaQuery query=new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<StoreDocument> searchHits = openSearchOperations.search(query,StoreDocument.class);

        return searchHits.stream().map(SearchHit::getContent).toList();
    }

    @Override
    public List<StoreDocument> autocomplete(String keyword) {
        if(keyword==null||keyword.isBlank()){
            return List.of();
        }
        Criteria criteria=new Criteria();
        criteria=criteria.subCriteria(
                new Criteria("name").matches(keyword).boost(3.0f)
                        .or("searchAddress").matches(keyword)
        );
        CriteriaQuery query=new CriteriaQuery(criteria).setPageable(PageRequest.of(0,10));
        SearchHits<StoreDocument> searchHits=openSearchOperations.search(query,StoreDocument.class);
        return searchHits.stream().map(SearchHit::getContent).toList();
    }
}
