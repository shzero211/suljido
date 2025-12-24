package com.kck.suljido.store.entity.elasticsearch;

import com.kck.suljido.common.Address;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "suljido_stores")
@Setting(settingPath = "/elasticsearch/store-setting.json")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StoreDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "korean_analyzer",searchAnalyzer = "korean_analyzer")
    private String name;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Keyword)
    private String category;

    private Integer rating;

    private Integer reviewCount;

    @Field(type = FieldType.Object)
    private Address address;

    @Field(type = FieldType.Text, analyzer = "korean_analyzer", searchAnalyzer = "korean_analyzer")
    private String searchAddress;
}
