package com.kck.suljido.review.entity;

import com.kck.suljido.entity.BaseTimeEntity;
import com.kck.suljido.store.entity.Store;
import com.kck.suljido.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

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

    @Lob
    private String content;

    private Byte rating;

}
