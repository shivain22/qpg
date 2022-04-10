package com.qpg.repository;

import com.qpg.domain.CourseMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CourseMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseMasterRepository extends JpaRepository<CourseMaster, Long>, JpaSpecificationExecutor<CourseMaster> {
}
