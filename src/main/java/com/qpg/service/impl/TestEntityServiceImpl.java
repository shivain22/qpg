package com.qpg.service.impl;

import com.qpg.service.TestEntityService;
import com.qpg.domain.TestEntity;
import com.qpg.repository.TestEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TestEntity}.
 */
@Service
@Transactional
public class TestEntityServiceImpl implements TestEntityService {

    private final Logger log = LoggerFactory.getLogger(TestEntityServiceImpl.class);

    private final TestEntityRepository testEntityRepository;

    public TestEntityServiceImpl(TestEntityRepository testEntityRepository) {
        this.testEntityRepository = testEntityRepository;
    }

    @Override
    public TestEntity save(TestEntity testEntity) {
        log.debug("Request to save TestEntity : {}", testEntity);
        return testEntityRepository.save(testEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestEntity> findAll(Pageable pageable) {
        log.debug("Request to get all TestEntities");
        return testEntityRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TestEntity> findOne(Long id) {
        log.debug("Request to get TestEntity : {}", id);
        return testEntityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestEntity : {}", id);
        testEntityRepository.deleteById(id);
    }
}
