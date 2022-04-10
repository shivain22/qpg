package com.qpg.web.rest;

import com.qpg.domain.SubCategoryMaster;
import com.qpg.service.SubCategoryMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.SubCategoryMasterCriteria;
import com.qpg.service.SubCategoryMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.SubCategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class SubCategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoryMasterResource.class);

    private static final String ENTITY_NAME = "subCategoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubCategoryMasterService subCategoryMasterService;

    private final SubCategoryMasterQueryService subCategoryMasterQueryService;

    public SubCategoryMasterResource(SubCategoryMasterService subCategoryMasterService, SubCategoryMasterQueryService subCategoryMasterQueryService) {
        this.subCategoryMasterService = subCategoryMasterService;
        this.subCategoryMasterQueryService = subCategoryMasterQueryService;
    }

    /**
     * {@code POST  /sub-category-masters} : Create a new subCategoryMaster.
     *
     * @param subCategoryMaster the subCategoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subCategoryMaster, or with status {@code 400 (Bad Request)} if the subCategoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-category-masters")
    public ResponseEntity<SubCategoryMaster> createSubCategoryMaster(@Valid @RequestBody SubCategoryMaster subCategoryMaster) throws URISyntaxException {
        log.debug("REST request to save SubCategoryMaster : {}", subCategoryMaster);
        if (subCategoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new subCategoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCategoryMaster result = subCategoryMasterService.save(subCategoryMaster);
        return ResponseEntity.created(new URI("/api/sub-category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-category-masters} : Updates an existing subCategoryMaster.
     *
     * @param subCategoryMaster the subCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategoryMaster,
     * or with status {@code 400 (Bad Request)} if the subCategoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-category-masters")
    public ResponseEntity<SubCategoryMaster> updateSubCategoryMaster(@Valid @RequestBody SubCategoryMaster subCategoryMaster) throws URISyntaxException {
        log.debug("REST request to update SubCategoryMaster : {}", subCategoryMaster);
        if (subCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubCategoryMaster result = subCategoryMasterService.save(subCategoryMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subCategoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-category-masters} : get all the subCategoryMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subCategoryMasters in body.
     */
    @GetMapping("/sub-category-masters")
    public ResponseEntity<List<SubCategoryMaster>> getAllSubCategoryMasters(SubCategoryMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SubCategoryMasters by criteria: {}", criteria);
        Page<SubCategoryMaster> page = subCategoryMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-category-masters/count} : count all the subCategoryMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sub-category-masters/count")
    public ResponseEntity<Long> countSubCategoryMasters(SubCategoryMasterCriteria criteria) {
        log.debug("REST request to count SubCategoryMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(subCategoryMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sub-category-masters/:id} : get the "id" subCategoryMaster.
     *
     * @param id the id of the subCategoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subCategoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-category-masters/{id}")
    public ResponseEntity<SubCategoryMaster> getSubCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get SubCategoryMaster : {}", id);
        Optional<SubCategoryMaster> subCategoryMaster = subCategoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subCategoryMaster);
    }

    /**
     * {@code DELETE  /sub-category-masters/:id} : delete the "id" subCategoryMaster.
     *
     * @param id the id of the subCategoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-category-masters/{id}")
    public ResponseEntity<Void> deleteSubCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete SubCategoryMaster : {}", id);
        subCategoryMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
