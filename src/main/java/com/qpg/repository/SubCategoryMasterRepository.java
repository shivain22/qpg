package com.qpg.repository;

import com.qpg.domain.SubCategoryMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubCategoryMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoryMasterRepository extends JpaRepository<SubCategoryMaster, Long>, JpaSpecificationExecutor<SubCategoryMaster> {
}
