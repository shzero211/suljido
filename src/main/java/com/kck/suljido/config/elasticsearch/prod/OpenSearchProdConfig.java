package com.kck.suljido.config.elasticsearch.prod;

import org.opensearch.data.client.osc.OpenSearchConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.lang.NonNull;

@Configuration
@Profile("prod")
public class OpenSearchProdConfig extends OpenSearchConfiguration {
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
                // AWS OpenSearch 주소 (https:// 제외)
                .connectedTo(uri)
                .usingSsl() // AWS는 SSL 필수
                .withConnectTimeout(10000)
                .withSocketTimeout(60000)
                .withBasicAuth(id, pwd) // AWS 마스터 계정
                .build();
    }
}
