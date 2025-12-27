package com.kck.suljido.store.repository;

import com.kck.suljido.common.Address;
import com.kck.suljido.store.entity.Store;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {
    @Query(
                "SELECT s FROM Store s "+
                 "where ST_Distance_Sphere(s.location, :me)<= :radius "+
                    "ORDER BY ST_Distance_Sphere(s.location, :me)"
    )
    List<Store> findStoresDistance(@Param("me")Point myLocation,@Param("radius") double radiusMeters);

    Optional<Store> findByNameAndAddress_FullAddress(String storeName, String fullAddress);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Store s SET "+
                    "s.reviewCount = COALESCE (s.reviewCount, 0) + 1, "+
                    "s.totalRating = COALESCE(s.totalRating, 0) + :rating, "+
                    // 카테고리 카운트도 안전하게 COALESCE 적용
                    "s.sojuCount = COALESCE(s.sojuCount, 0) + (CASE WHEN :category = 'soju' THEN 1 ELSE 0 END), " +
                    "s.beerCount = COALESCE(s.beerCount, 0) + (CASE WHEN :category = 'beer' THEN 1 ELSE 0 END), " +
                    "s.wineCount = COALESCE(s.wineCount, 0) + (CASE WHEN :category = 'wine' THEN 1 ELSE 0 END), " +
                    "s.whiskyCount = COALESCE(s.whiskyCount, 0) + (CASE WHEN :category = 'whisky' THEN 1 ELSE 0 END), " +
                    "s.traditionalCount = COALESCE(s.traditionalCount, 0) + (CASE WHEN :category = 'traditional_liquor' THEN 1 ELSE 0 END) " +
                    "WHERE s.id = :storeId"
    )
    void incrementReviewStatus(@Param("storeId") Long storeId, @Param("rating") Integer rating,@Param("category")String category);
}
