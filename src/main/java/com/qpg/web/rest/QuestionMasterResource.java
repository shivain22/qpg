package com.qpg.web.rest;

import com.qpg.domain.QuestionMaster;
import com.qpg.service.QuestionMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QuestionMasterCriteria;
import com.qpg.service.QuestionMasterQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qpg.domain.QuestionMaster}.
 */
@RestController
@RequestMapping("/api")
public class QuestionMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionMasterResource.class);

    private static final String ENTITY_NAME = "questionMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionMasterService questionMasterService;

    private final QuestionMasterQueryService questionMasterQueryService;

    public QuestionMasterResource(QuestionMasterService questionMasterService, QuestionMasterQueryService questionMasterQueryService) {
        this.questionMasterService = questionMasterService;
        this.questionMasterQueryService = questionMasterQueryService;
    }

    /**
     * {@code POST  /question-masters} : Create a new questionMaster.
     *
     * @param questionMaster the questionMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionMaster, or with status {@code 400 (Bad Request)} if the questionMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-masters")
    public ResponseEntity<QuestionMaster> createQuestionMaster(@Valid @RequestBody QuestionMaster questionMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionMaster : {}", questionMaster);
        if (questionMaster.getId() != null) {
            throw new BadRequestAlertException("A new questionMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionMaster result = questionMasterService.save(questionMaster);
        return ResponseEntity.created(new URI("/api/question-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-masters} : Updates an existing questionMaster.
     *
     * @param questionMaster the questionMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionMaster,
     * or with status {@code 400 (Bad Request)} if the questionMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-masters")
    public ResponseEntity<QuestionMaster> updateQuestionMaster(@Valid @RequestBody QuestionMaster questionMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionMaster : {}", questionMaster);
        if (questionMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionMaster result = questionMasterService.save(questionMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-masters} : get all the questionMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionMasters in body.
     */
    @GetMapping("/question-masters")
    public ResponseEntity<List<QuestionMaster>> getAllQuestionMasters(QuestionMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionMasters by criteria: {}", criteria);
        Page<QuestionMaster> page = questionMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-masters/count} : count all the questionMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-masters/count")
    public ResponseEntity<Long> countQuestionMasters(QuestionMasterCriteria criteria) {
        log.debug("REST request to count QuestionMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-masters/:id} : get the "id" questionMaster.
     *
     * @param id the id of the questionMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-masters/{id}")
    public ResponseEntity<QuestionMaster> getQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionMaster : {}", id);
        Optional<QuestionMaster> questionMaster = questionMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionMaster);
    }

    /**
     * {@code DELETE  /question-masters/:id} : delete the "id" questionMaster.
     *
     * @param id the id of the questionMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-masters/{id}")
    public ResponseEntity<Void> deleteQuestionMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionMaster : {}", id);
        questionMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
