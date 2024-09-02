package com.oneune.informator.rest.repositories;

import com.oneune.informator.rest.store.entities.RequestHistoryEntity;
import com.oneune.informator.rest.store.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestHistoryRepository extends JpaRepository<RequestHistoryEntity, Long> {
    Optional<RequestHistoryEntity> findByBarcode(String barcode);
    List<RequestHistoryEntity> findAllByUser(UserEntity user);
}
