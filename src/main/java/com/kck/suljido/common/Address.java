package com.kck.suljido.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {
    private String province; // 시/도
    private String city; // 시/군/구
    private String district; // 읍/면/동

    @Column(name = "road_name")
    private String roadName; // 도로명

    @Column(nullable = false,name="full_address")
    private String fullAddress; //전체 주소

}
