package com.kck.suljido.store.entity;

import com.kck.suljido.common.Address;
import com.kck.suljido.entity.BaseTimeEntity;
import com.kck.suljido.store.entity.enums.Category;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "main_category")
    private Category mainCategory;

    public Double getAvgRating(){
        if(reviewCount==null || reviewCount==0 ) return 0.0;
       long total = (this.totalRating==null)? 0L : this.totalRating;
        return (double)total/reviewCount;
    }

    public void updateMainCategory(Category topCategory) {
        this.mainCategory=topCategory;
    }
}
