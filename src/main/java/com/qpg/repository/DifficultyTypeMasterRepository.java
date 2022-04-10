package com.qpg.repository;

import com.qpg.domain.DifficultyTypeMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the DifficultyTypeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DifficultyTypeMasterRepository extends JpaRepository<DifficultyTypeMaster, Long>, JpaSpecificationExecutor<DifficultyTypeMaster> {
    Optional<DifficultyTypeMaster> findDifficultyTypeMasterByName(String difficultyTypeName);
}
