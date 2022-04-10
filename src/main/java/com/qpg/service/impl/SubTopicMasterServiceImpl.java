package com.qpg.service.impl;

import com.qpg.domain.TopicMaster;
import com.qpg.service.SubTopicMasterService;
import com.qpg.domain.SubTopicMaster;
import com.qpg.repository.SubTopicMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SubTopicMaster}.
 */
@Service
@Transactional
public class SubTopicMasterServiceImpl implements SubTopicMasterService {

    private final Logger log = LoggerFactory.getLogger(SubTopicMasterServiceImpl.class);

    private final SubTopicMasterRepository subTopicMasterRepository;

    public SubTopicMasterServiceImpl(SubTopicMasterRepository subTopicMasterRepository) {
        this.subTopicMasterRepository = subTopicMasterRepository;
    }

    @Override
    public SubTopicMaster save(SubTopicMaster subTopicMaster) {
        log.debug("Request to save SubTopicMaster : {}", subTopicMaster);
        return subTopicMasterRepository.save(subTopicMaster);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubTopicMaster> findAll(Pageable pageable) {
        log.debug("Request to get all SubTopicMasters");
        return subTopicMasterRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SubTopicMaster> findOne(Long id) {
        log.debug("Request to get SubTopicMaster : {}", id);
        return subTopicMasterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubTopicMaster : {}", id);
        subTopicMasterRepository.deleteById(id);
    }

    @Override
    public Optional<SubTopicMaster> findSubTopicMasterByTopicMasterAndName(TopicMaster topicMaster, String subTopicName) {
        log.debug("Reequest to get SubTopicMaster by topicMaster and SubTopicMasterName");
        return subTopicMasterRepository.findSubTopicMasterByTopicMasterAndAndName(topicMaster, subTopicName);
    }
}
