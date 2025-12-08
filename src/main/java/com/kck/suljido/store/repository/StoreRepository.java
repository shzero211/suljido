package com.kck.suljido.store.repository;

import com.kck.suljido.common.Address;
import com.kck.suljido.store.entity.Store;
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

    Optional<Store> findByNameAndAddress(String storeName, Address address);
}
