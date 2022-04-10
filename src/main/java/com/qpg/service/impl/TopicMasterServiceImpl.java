package com.qpg.service.impl;

import com.qpg.service.TopicMasterService;
import com.qpg.domain.TopicMaster;
import com.qpg.repository.TopicMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicMaster}.
 */
@Service
@Transactional
public class TopicMasterServiceImpl implements TopicMasterService {

    private final Logger log = LoggerFactory.getLogger(TopicMasterServiceImpl.class);

    private final TopicMasterRepository topicMasterRepository;

    public TopicMasterServiceImpl(TopicMasterRepository topicMasterRepository) {
        this.topicMasterRepository = topicMasterRepository;
    }

    @Override
    public TopicMaster save(TopicMaster topicMaster) {
        log.debug("Request to save TopicMaster : {}", topicMaster);
        return topicMasterRepository.save(topicMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicMaster> findAll(Pageable pageable) {
        log.debug("Request to get all TopicMasters");
        return topicMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicMaster> findOne(Long id) {
        log.debug("Request to get TopicMaster : {}", id);
        return topicMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicMaster : {}", id);
        topicMasterRepository.deleteById(id);
    }
}
