package com.kck.suljido.tag.entity;

import com.kck.suljido.entity.BaseTimeEntity;
import com.kck.suljido.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewTag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Review review;

    @ManyToOne
    private Tag tag;


}
