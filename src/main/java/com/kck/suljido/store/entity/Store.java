package com.kck.suljido.store.entity;

import com.kck.suljido.common.Address;
import com.kck.suljido.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

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


}
