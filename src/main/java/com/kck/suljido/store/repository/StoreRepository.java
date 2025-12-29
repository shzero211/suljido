package com.kck.suljido.store.repository;

import com.kck.suljido.store.entity.Store;
import com.kck.suljido.store.entity.enums.Category;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(value = """
            SELECT s.* ,
            ST_Distance_Sphere(s.location,POINT(:lng,:lat)) as distance
            from Store s
            WHERE ST_Distance_Sphere(s.location,POINT(:lng,:lat)) <= :radius
            AND (:category IS NULL OR s.main_category = :#{#category?.name()})
            ORDER BY distance ASC
            """,nativeQuery = true)
    List<Store> findStoresByRadiusAndCategory(@Param("lat")double myLat,
                                              @Param("lng")double myLng,
                                              @Param("radius") double radius,
                                              @Param("category")Category category
    );
}
