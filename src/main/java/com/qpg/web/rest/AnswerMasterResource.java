package com.qpg.web.rest;

import com.qpg.domain.AnswerMaster;
import com.qpg.service.AnswerMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.AnswerMasterCriteria;
import com.qpg.service.AnswerMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.AnswerMaster}.
 */
@RestController
@RequestMapping("/api")
public class AnswerMasterResource {

    private final Logger log = LoggerFactory.getLogger(AnswerMasterResource.class);

    private static final String ENTITY_NAME = "answerMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerMasterService answerMasterService;

    private final AnswerMasterQueryService answerMasterQueryService;

    public AnswerMasterResource(AnswerMasterService answerMasterService, AnswerMasterQueryService answerMasterQueryService) {
        this.answerMasterService = answerMasterService;
        this.answerMasterQueryService = answerMasterQueryService;
    }

    /**
     * {@code POST  /answer-masters} : Create a new answerMaster.
     *
     * @param answerMaster the answerMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerMaster, or with status {@code 400 (Bad Request)} if the answerMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-masters")
    public ResponseEntity<AnswerMaster> createAnswerMaster(@Valid @RequestBody AnswerMaster answerMaster) throws URISyntaxException {
        log.debug("REST request to save AnswerMaster : {}", answerMaster);
        if (answerMaster.getId() != null) {
            throw new BadRequestAlertException("A new answerMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerMaster result = answerMasterService.save(answerMaster);
        return ResponseEntity.created(new URI("/api/answer-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-masters} : Updates an existing answerMaster.
     *
     * @param answerMaster the answerMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerMaster,
     * or with status {@code 400 (Bad Request)} if the answerMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-masters")
    public ResponseEntity<AnswerMaster> updateAnswerMaster(@Valid @RequestBody AnswerMaster answerMaster) throws URISyntaxException {
        log.debug("REST request to update AnswerMaster : {}", answerMaster);
        if (answerMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerMaster result = answerMasterService.save(answerMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, answerMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answer-masters} : get all the answerMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerMasters in body.
     */
    @GetMapping("/answer-masters")
    public ResponseEntity<List<AnswerMaster>> getAllAnswerMasters(AnswerMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AnswerMasters by criteria: {}", criteria);
        Page<AnswerMaster> page = answerMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /answer-masters/count} : count all the answerMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/answer-masters/count")
    public ResponseEntity<Long> countAnswerMasters(AnswerMasterCriteria criteria) {
        log.debug("REST request to count AnswerMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(answerMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /answer-masters/:id} : get the "id" answerMaster.
     *
     * @param id the id of the answerMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-masters/{id}")
    public ResponseEntity<AnswerMaster> getAnswerMaster(@PathVariable Long id) {
        log.debug("REST request to get AnswerMaster : {}", id);
        Optional<AnswerMaster> answerMaster = answerMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerMaster);
    }

    /**
     * {@code DELETE  /answer-masters/:id} : delete the "id" answerMaster.
     *
     * @param id the id of the answerMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-masters/{id}")
    public ResponseEntity<Void> deleteAnswerMaster(@PathVariable Long id) {
        log.debug("REST request to delete AnswerMaster : {}", id);
        answerMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
