package com.kck.suljido.runner;

import com.kck.suljido.store.entity.elasticsearch.StoreDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.opensearch.client.Request;
import org.opensearch.client.Response;
import org.opensearch.client.RestClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"test","prod"})
@Order(1)
public class OpenSearchInitializer implements ApplicationRunner {
    private final RestClient restClient;
    private final Environment environment; // 현재 프로파일 확인용
    private final ElasticsearchOperations operations; // ★ Spring Data 작업용 추가

    @Override
    public void run(ApplicationArguments args) {
        String indexName = "suljido_stores";

        try {
            // 1. 현재 프로파일 확인
            String[] activeProfiles = environment.getActiveProfiles();
            boolean isProd = Arrays.asList(activeProfiles).contains("prod");

            // 2. 파일 선택 (prod면 은전한닢, 아니면 Nori)
            String settingFileName = isProd ?
                    "elasticsearch/elastic-settings-prod.json" :
                    "elasticsearch/elastic-settings-local.json";

            log.info("🚀 [OpenSearch] 초기화 시작! (환경: {}, 파일: {})",
                    isProd ? "PROD(AWS)" : "LOCAL", settingFileName);

            // 3. 기존 인덱스 삭제
            try {
                restClient.performRequest(new Request("DELETE", "/" + indexName));
                log.info("🗑️ 기존 인덱스 삭제 완료");
            } catch (Exception e) {
                log.info("ℹ️ 삭제할 인덱스가 없습니다.");
            }

            // 4. 선택된 설정 파일 읽기
            ClassPathResource resource = new ClassPathResource(settingFileName);
            String settingsJson = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            // 5. 인덱스 생성
            Request createRequest = new Request("PUT", "/" + indexName);
            createRequest.setJsonEntity(settingsJson);
            Response response = restClient.performRequest(createRequest);

            log.info("✨ 인덱스 생성 완료! Status: {}", response.getStatusLine().getStatusCode());

            // 3. Mappings(필드) 적용 (High Level - Annotation 활용!) ★★★
            // Spring Data가 StoreDocument 클래스의 @Field를 읽어서 매핑을 만들어줍니다.
            IndexOperations indexOps = operations.indexOps(StoreDocument.class);
            indexOps.putMapping(indexOps.createMapping());

            log.info("🗺️ 매핑(Annotation) 적용 완료!");

        } catch (Exception e) {
            log.error("❌ 인덱스 초기화 실패", e);
            // 로컬 테스트 편의를 위해 에러가 나도 앱이 죽지는 않게 하려면 아래 줄 주석 처리
            throw new RuntimeException("OpenSearch 초기화 실패", e);
        }
    }
}
