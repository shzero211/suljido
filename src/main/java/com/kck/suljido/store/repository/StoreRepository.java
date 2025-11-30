package com.kck.suljido.store.repository;

import com.kck.suljido.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
}
