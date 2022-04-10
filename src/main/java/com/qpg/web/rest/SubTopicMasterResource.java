package com.qpg.web.rest;

import com.qpg.domain.SubTopicMaster;
import com.qpg.service.SubTopicMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.SubTopicMasterCriteria;
import com.qpg.service.SubTopicMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.SubTopicMaster}.
 */
@RestController
@RequestMapping("/api")
public class SubTopicMasterResource {

    private final Logger log = LoggerFactory.getLogger(SubTopicMasterResource.class);

    private static final String ENTITY_NAME = "subTopicMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubTopicMasterService subTopicMasterService;

    private final SubTopicMasterQueryService subTopicMasterQueryService;

    public SubTopicMasterResource(SubTopicMasterService subTopicMasterService, SubTopicMasterQueryService subTopicMasterQueryService) {
        this.subTopicMasterService = subTopicMasterService;
        this.subTopicMasterQueryService = subTopicMasterQueryService;
    }

    /**
     * {@code POST  /sub-topic-masters} : Create a new subTopicMaster.
     *
     * @param subTopicMaster the subTopicMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subTopicMaster, or with status {@code 400 (Bad Request)} if the subTopicMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-topic-masters")
    public ResponseEntity<SubTopicMaster> createSubTopicMaster(@Valid @RequestBody SubTopicMaster subTopicMaster) throws URISyntaxException {
        log.debug("REST request to save SubTopicMaster : {}", subTopicMaster);
        if (subTopicMaster.getId() != null) {
            throw new BadRequestAlertException("A new subTopicMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubTopicMaster result = subTopicMasterService.save(subTopicMaster);
        return ResponseEntity.created(new URI("/api/sub-topic-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-topic-masters} : Updates an existing subTopicMaster.
     *
     * @param subTopicMaster the subTopicMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subTopicMaster,
     * or with status {@code 400 (Bad Request)} if the subTopicMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subTopicMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-topic-masters")
    public ResponseEntity<SubTopicMaster> updateSubTopicMaster(@Valid @RequestBody SubTopicMaster subTopicMaster) throws URISyntaxException {
        log.debug("REST request to update SubTopicMaster : {}", subTopicMaster);
        if (subTopicMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubTopicMaster result = subTopicMasterService.save(subTopicMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subTopicMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-topic-masters} : get all the subTopicMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subTopicMasters in body.
     */
    @GetMapping("/sub-topic-masters")
    public ResponseEntity<List<SubTopicMaster>> getAllSubTopicMasters(SubTopicMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SubTopicMasters by criteria: {}", criteria);
        Page<SubTopicMaster> page = subTopicMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-topic-masters/count} : count all the subTopicMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sub-topic-masters/count")
    public ResponseEntity<Long> countSubTopicMasters(SubTopicMasterCriteria criteria) {
        log.debug("REST request to count SubTopicMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(subTopicMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sub-topic-masters/:id} : get the "id" subTopicMaster.
     *
     * @param id the id of the subTopicMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subTopicMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-topic-masters/{id}")
    public ResponseEntity<SubTopicMaster> getSubTopicMaster(@PathVariable Long id) {
        log.debug("REST request to get SubTopicMaster : {}", id);
        Optional<SubTopicMaster> subTopicMaster = subTopicMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subTopicMaster);
    }

    /**
     * {@code DELETE  /sub-topic-masters/:id} : delete the "id" subTopicMaster.
     *
     * @param id the id of the subTopicMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-topic-masters/{id}")
    public ResponseEntity<Void> deleteSubTopicMaster(@PathVariable Long id) {
        log.debug("REST request to delete SubTopicMaster : {}", id);
        subTopicMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
