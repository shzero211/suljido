package com.kck.suljido.review.entity;

import com.kck.suljido.entity.BaseTimeEntity;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Store store;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> images=new ArrayList<>();

    @Lob
    private String content;

    private Integer rating;
    private String category; // 추천 주종
    private LocalDateTime visitTime; //방문 시간

    public void addImage(ReviewImage reviewImage) {
        this.images.add(reviewImage);
        reviewImage.setReview(this);
    }
}
