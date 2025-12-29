package com.kck.suljido.runner;

import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"test","prod"})
@Order(1)
public class OpenSearchInitializer implements ApplicationRunner {
    private final ElasticsearchOperations operations;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[Opensearch] 기존 데이터 초기화 시작...");
        IndexOperations indexOps=operations.indexOps(StoreDocument.class);

        if(indexOps.exists()){
            indexOps.delete();
            log.info("기존 인덱스 삭제 완료");
        }

        indexOps.create();
        indexOps.putMapping(indexOps.createMapping());
        log.info("새 인덱스 생성 및 매핑 완료");
    }
}
