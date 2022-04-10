package com.qpg.web.rest;

import com.qpg.domain.QuestionChoiceMaster;
import com.qpg.service.QuestionChoiceMasterQueryService;
import com.qpg.service.QuestionChoiceMasterService;
import com.qpg.service.dto.QuestionChoiceMasterCriteria;
import com.qpg.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link QuestionChoiceMaster}.
 */
@RestController
@RequestMapping("/api")
public class QuestionChoiceMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionChoiceMasterResource.class);

    private static final String ENTITY_NAME = "questionChoiceMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionChoiceMasterService questionChoiceMasterService;

    private final QuestionChoiceMasterQueryService questionChoiceMasterQueryService;

    public QuestionChoiceMasterResource(QuestionChoiceMasterService questionChoiceMasterService, QuestionChoiceMasterQueryService questionChoiceMasterQueryService) {
        this.questionChoiceMasterService = questionChoiceMasterService;
        this.questionChoiceMasterQueryService = questionChoiceMasterQueryService;
    }

    /**
     * {@code POST  /question-choice-masters} : Create a new questionChoiceMaster.
     *
     * @param questionChoiceMaster the questionChoiceMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionChoiceMaster, or with status {@code 400 (Bad Request)} if the questionChoiceMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-choice-masters")
    public ResponseEntity<QuestionChoiceMaster> createQuestionChoiceMaster(@Valid @RequestBody QuestionChoiceMaster questionChoiceMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionChoiceMaster : {}", questionChoiceMaster);
        if (questionChoiceMaster.getId() != null) {
            throw new BadRequestAlertException("A new questionChoiceMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionChoiceMaster result = questionChoiceMasterService.save(questionChoiceMaster);
        return ResponseEntity.created(new URI("/api/question-choice-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-choice-masters} : Updates an existing questionChoiceMaster.
     *
     * @param questionChoiceMaster the questionChoiceMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionChoiceMaster,
     * or with status {@code 400 (Bad Request)} if the questionChoiceMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionChoiceMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-choice-masters")
    public ResponseEntity<QuestionChoiceMaster> updateQuestionChoiceMaster(@Valid @RequestBody QuestionChoiceMaster questionChoiceMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionChoiceMaster : {}", questionChoiceMaster);
        if (questionChoiceMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionChoiceMaster result = questionChoiceMasterService.save(questionChoiceMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionChoiceMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-choice-masters} : get all the questionChoiceMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionChoiceMasters in body.
     */
    @GetMapping("/question-choice-masters")
    public ResponseEntity<List<QuestionChoiceMaster>> getAllQuestionChoiceMasters(QuestionChoiceMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionChoiceMasters by criteria: {}", criteria);
        Page<QuestionChoiceMaster> page = questionChoiceMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-choice-masters/count} : count all the questionChoiceMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-choice-masters/count")
    public ResponseEntity<Long> countQuestionChoiceMasters(QuestionChoiceMasterCriteria criteria) {
        log.debug("REST request to count QuestionChoiceMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionChoiceMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-choice-masters/:id} : get the "id" questionChoiceMaster.
     *
     * @param id the id of the questionChoiceMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionChoiceMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-choice-masters/{id}")
    public ResponseEntity<QuestionChoiceMaster> getQuestionChoiceMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionChoiceMaster : {}", id);
        Optional<QuestionChoiceMaster> questionChoiceMaster = questionChoiceMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionChoiceMaster);
    }

    /**
     * {@code DELETE  /question-choice-masters/:id} : delete the "id" questionChoiceMaster.
     *
     * @param id the id of the questionChoiceMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-choice-masters/{id}")
    public ResponseEntity<Void> deleteQuestionChoiceMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionChoiceMaster : {}", id);
        questionChoiceMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
