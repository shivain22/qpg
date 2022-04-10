package com.qpg.repository;

import com.qpg.domain.CategoryMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryMasterRepository extends JpaRepository<CategoryMaster, Long>, JpaSpecificationExecutor<CategoryMaster> {
}
