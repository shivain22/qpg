package com.qpg.service;

import com.qpg.domain.DepartmentMaster;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DepartmentMaster}.
 */
public interface DepartmentMasterService {

    /**
     * Save a departmentMaster.
     *
     * @param departmentMaster the entity to save.
     * @return the persisted entity.
     */
    DepartmentMaster save(DepartmentMaster departmentMaster);

    /**
     * Get all the departmentMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepartmentMaster> findAll(Pageable pageable);


    /**
     * Get the "id" departmentMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartmentMaster> findOne(Long id);

    /**
     * Delete the "id" departmentMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
