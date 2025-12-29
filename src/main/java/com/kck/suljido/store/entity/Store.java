package com.kck.suljido.store.entity;

import com.kck.suljido.common.Address;
import com.kck.suljido.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private Point location;

    private String thumbnailImage;

    private Long totalRating;

    private Integer reviewCount;

    @Builder.Default
    private Integer sojuCount = 0;
    @Builder.Default
    private Integer beerCount = 0;
    @Builder.Default
    private Integer wineCount = 0;
    @Builder.Default
    private Integer whiskyCount = 0;
    @Builder.Default
    private Integer traditionalCount = 0;
    public Double getAvgRating(){
        if(reviewCount==null || reviewCount==0 ) return 0.0;
       long total = (this.totalRating==null)? 0L : this.totalRating;
        return (double)total/reviewCount;
    }
    public String getBestCategory(){
        Map<String,Integer> counts=new HashMap<>();
        counts.put("소주", sojuCount);
        counts.put("맥주", beerCount);
        counts.put("와인", wineCount);
        counts.put("위스키", whiskyCount);
        counts.put("전통주", traditionalCount);

        int maxCount= Collections.max(counts.values());

        if(maxCount==0) return "정보없음";

        return counts.entrySet().stream()
                .filter(entry->entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

}
