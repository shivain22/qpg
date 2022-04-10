package com.qpg.service;

import com.qpg.domain.SubTopicMaster;

import com.qpg.domain.TopicMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link SubTopicMaster}.
 */
public interface SubTopicMasterService {

    /**
     * Save a subTopicMaster.
     *
     * @param subTopicMaster the entity to save.
     * @return the persisted entity.
     */
    SubTopicMaster save(SubTopicMaster subTopicMaster);

    /**
     * Get all the subTopicMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubTopicMaster> findAll(Pageable pageable);


    /**
     * Get the "id" subTopicMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubTopicMaster> findOne(Long id);

    /**
     * Delete the "id" subTopicMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<SubTopicMaster> findSubTopicMasterByTopicMasterAndName(TopicMaster topicMaster, String subTopicName);
}
