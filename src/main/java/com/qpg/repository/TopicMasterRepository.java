package com.qpg.repository;

import com.qpg.domain.TopicMaster;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TopicMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicMasterRepository extends JpaRepository<TopicMaster, Long>, JpaSpecificationExecutor<TopicMaster> {
}
