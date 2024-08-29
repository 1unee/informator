package com.oneune.informator.rest.repositories;

import com.oneune.informator.rest.store.entities.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<ActionEntity, Long> {
    List<ActionEntity> findActionEntitiesByTimestampAfter(LocalDateTime start);
}
