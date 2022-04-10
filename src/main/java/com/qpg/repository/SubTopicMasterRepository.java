package com.qpg.repository;

import com.qpg.domain.SubTopicMaster;

import com.qpg.domain.TopicMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the SubTopicMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubTopicMasterRepository extends JpaRepository<SubTopicMaster, Long>, JpaSpecificationExecutor<SubTopicMaster> {

    Optional<SubTopicMaster> findSubTopicMasterByTopicMasterAndAndName(TopicMaster topicMaster, String subTopicName);

}
