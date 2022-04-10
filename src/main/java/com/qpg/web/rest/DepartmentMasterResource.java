package com.qpg.web.rest;

import com.qpg.domain.DepartmentMaster;
import com.qpg.service.DepartmentMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.DepartmentMasterCriteria;
import com.qpg.service.DepartmentMasterQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qpg.domain.DepartmentMaster}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentMasterResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentMasterResource.class);

    private static final String ENTITY_NAME = "departmentMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentMasterService departmentMasterService;

    private final DepartmentMasterQueryService departmentMasterQueryService;

    public DepartmentMasterResource(DepartmentMasterService departmentMasterService, DepartmentMasterQueryService departmentMasterQueryService) {
        this.departmentMasterService = departmentMasterService;
        this.departmentMasterQueryService = departmentMasterQueryService;
    }

    /**
     * {@code POST  /department-masters} : Create a new departmentMaster.
     *
     * @param departmentMaster the departmentMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentMaster, or with status {@code 400 (Bad Request)} if the departmentMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/department-masters")
    public ResponseEntity<DepartmentMaster> createDepartmentMaster(@Valid @RequestBody DepartmentMaster departmentMaster) throws URISyntaxException {
        log.debug("REST request to save DepartmentMaster : {}", departmentMaster);
        if (departmentMaster.getId() != null) {
            throw new BadRequestAlertException("A new departmentMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentMaster result = departmentMasterService.save(departmentMaster);
        return ResponseEntity.created(new URI("/api/department-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /department-masters} : Updates an existing departmentMaster.
     *
     * @param departmentMaster the departmentMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentMaster,
     * or with status {@code 400 (Bad Request)} if the departmentMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departmentMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/department-masters")
    public ResponseEntity<DepartmentMaster> updateDepartmentMaster(@Valid @RequestBody DepartmentMaster departmentMaster) throws URISyntaxException {
        log.debug("REST request to update DepartmentMaster : {}", departmentMaster);
        if (departmentMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentMaster result = departmentMasterService.save(departmentMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, departmentMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /department-masters} : get all the departmentMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departmentMasters in body.
     */
    @GetMapping("/department-masters")
    public ResponseEntity<List<DepartmentMaster>> getAllDepartmentMasters(DepartmentMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DepartmentMasters by criteria: {}", criteria);
        Page<DepartmentMaster> page = departmentMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /department-masters/count} : count all the departmentMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/department-masters/count")
    public ResponseEntity<Long> countDepartmentMasters(DepartmentMasterCriteria criteria) {
        log.debug("REST request to count DepartmentMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(departmentMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /department-masters/:id} : get the "id" departmentMaster.
     *
     * @param id the id of the departmentMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/department-masters/{id}")
    public ResponseEntity<DepartmentMaster> getDepartmentMaster(@PathVariable Long id) {
        log.debug("REST request to get DepartmentMaster : {}", id);
        Optional<DepartmentMaster> departmentMaster = departmentMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentMaster);
    }

    /**
     * {@code DELETE  /department-masters/:id} : delete the "id" departmentMaster.
     *
     * @param id the id of the departmentMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/department-masters/{id}")
    public ResponseEntity<Void> deleteDepartmentMaster(@PathVariable Long id) {
        log.debug("REST request to delete DepartmentMaster : {}", id);
        departmentMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
