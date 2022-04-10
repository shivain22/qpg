package com.qpg.service;

import com.qpg.domain.TopicMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TopicMaster}.
 */
public interface TopicMasterService {

    /**
     * Save a topicMaster.
     *
     * @param topicMaster the entity to save.
     * @return the persisted entity.
     */
    TopicMaster save(TopicMaster topicMaster);

    /**
     * Get all the topicMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicMaster> findAll(Pageable pageable);


    /**
     * Get the "id" topicMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicMaster> findOne(Long id);

    /**
     * Delete the "id" topicMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
