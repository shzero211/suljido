package com.kck.suljido.store.repository.elasticsearch;

import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoreSearchRepository extends ElasticsearchRepository<StoreDocument,Long> {
}
