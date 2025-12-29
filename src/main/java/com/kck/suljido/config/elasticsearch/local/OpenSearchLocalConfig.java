package com.kck.suljido.config.elasticsearch.local;

import org.opensearch.data.client.osc.OpenSearchConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.lang.NonNull;

@Configuration
@Profile("!prod")
public class OpenSearchLocalConfig extends OpenSearchConfiguration {
    @Value("${opensearch.custom.uri}")
    private String uri;
    @Value("${opensearch.custom.username}")
    private String id;
    @Value("${opensearch.custom.password}")
    private String pwd;
    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                // 로컬 Docker 주소
                .connectedTo(uri)
                // .usingSsl()  <-- 로컬은 보통 SSL 안 쓰므로 제거
                // .withBasicAuth(...) <-- 로컬 Docker 설정에 따라 제거하거나 admin/admin 입력
                .withConnectTimeout(10000)
                .withSocketTimeout(60000)
                .withBasicAuth(id,pwd)
                .build();
    }
}