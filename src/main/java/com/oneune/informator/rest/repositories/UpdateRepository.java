package com.oneune.informator.rest.repositories;

import com.oneune.informator.rest.store.entities.UpdateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateRepository extends JpaRepository<UpdateEntity, Long> {
}
