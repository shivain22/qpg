package com.qpg.web.rest;

import com.qpg.domain.QuestionBankMaster;
import com.qpg.service.QuestionBankMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QuestionBankMasterCriteria;
import com.qpg.service.QuestionBankMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.QuestionBankMaster}.
 */
@RestController
@RequestMapping("/api")
public class QuestionBankMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionBankMasterResource.class);

    private static final String ENTITY_NAME = "questionBankMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionBankMasterService questionBankMasterService;

    private final QuestionBankMasterQueryService questionBankMasterQueryService;

    public QuestionBankMasterResource(QuestionBankMasterService questionBankMasterService, QuestionBankMasterQueryService questionBankMasterQueryService) {
        this.questionBankMasterService = questionBankMasterService;
        this.questionBankMasterQueryService = questionBankMasterQueryService;
    }

    /**
     * {@code POST  /question-bank-masters} : Create a new questionBankMaster.
     *
     * @param questionBankMaster the questionBankMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionBankMaster, or with status {@code 400 (Bad Request)} if the questionBankMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-bank-masters")
    public ResponseEntity<QuestionBankMaster> createQuestionBankMaster(@Valid @RequestBody QuestionBankMaster questionBankMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionBankMaster : {}", questionBankMaster);
        if (questionBankMaster.getId() != null) {
            throw new BadRequestAlertException("A new questionBankMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionBankMaster result = questionBankMasterService.save(questionBankMaster);
        return ResponseEntity.created(new URI("/api/question-bank-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-bank-masters} : Updates an existing questionBankMaster.
     *
     * @param questionBankMaster the questionBankMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionBankMaster,
     * or with status {@code 400 (Bad Request)} if the questionBankMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionBankMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-bank-masters")
    public ResponseEntity<QuestionBankMaster> updateQuestionBankMaster(@Valid @RequestBody QuestionBankMaster questionBankMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionBankMaster : {}", questionBankMaster);
        if (questionBankMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionBankMaster result = questionBankMasterService.save(questionBankMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionBankMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-bank-masters} : get all the questionBankMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionBankMasters in body.
     */
    @GetMapping("/question-bank-masters")
    public ResponseEntity<List<QuestionBankMaster>> getAllQuestionBankMasters(QuestionBankMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionBankMasters by criteria: {}", criteria);
        Page<QuestionBankMaster> page = questionBankMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-bank-masters/count} : count all the questionBankMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-bank-masters/count")
    public ResponseEntity<Long> countQuestionBankMasters(QuestionBankMasterCriteria criteria) {
        log.debug("REST request to count QuestionBankMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionBankMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-bank-masters/:id} : get the "id" questionBankMaster.
     *
     * @param id the id of the questionBankMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionBankMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-bank-masters/{id}")
    public ResponseEntity<QuestionBankMaster> getQuestionBankMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionBankMaster : {}", id);
        Optional<QuestionBankMaster> questionBankMaster = questionBankMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionBankMaster);
    }

    /**
     * {@code DELETE  /question-bank-masters/:id} : delete the "id" questionBankMaster.
     *
     * @param id the id of the questionBankMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-bank-masters/{id}")
    public ResponseEntity<Void> deleteQuestionBankMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionBankMaster : {}", id);
        questionBankMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
