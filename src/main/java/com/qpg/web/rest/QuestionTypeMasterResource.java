package com.qpg.web.rest;

import com.qpg.domain.QuestionTypeMaster;
import com.qpg.service.QuestionTypeMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QuestionTypeMasterCriteria;
import com.qpg.service.QuestionTypeMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.QuestionTypeMaster}.
 */
@RestController
@RequestMapping("/api")
public class QuestionTypeMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeMasterResource.class);

    private static final String ENTITY_NAME = "questionTypeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionTypeMasterService questionTypeMasterService;

    private final QuestionTypeMasterQueryService questionTypeMasterQueryService;

    public QuestionTypeMasterResource(QuestionTypeMasterService questionTypeMasterService, QuestionTypeMasterQueryService questionTypeMasterQueryService) {
        this.questionTypeMasterService = questionTypeMasterService;
        this.questionTypeMasterQueryService = questionTypeMasterQueryService;
    }

    /**
     * {@code POST  /question-type-masters} : Create a new questionTypeMaster.
     *
     * @param questionTypeMaster the questionTypeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionTypeMaster, or with status {@code 400 (Bad Request)} if the questionTypeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-type-masters")
    public ResponseEntity<QuestionTypeMaster> createQuestionTypeMaster(@Valid @RequestBody QuestionTypeMaster questionTypeMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionTypeMaster : {}", questionTypeMaster);
        if (questionTypeMaster.getId() != null) {
            throw new BadRequestAlertException("A new questionTypeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionTypeMaster result = questionTypeMasterService.save(questionTypeMaster);
        return ResponseEntity.created(new URI("/api/question-type-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-type-masters} : Updates an existing questionTypeMaster.
     *
     * @param questionTypeMaster the questionTypeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionTypeMaster,
     * or with status {@code 400 (Bad Request)} if the questionTypeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionTypeMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-type-masters")
    public ResponseEntity<QuestionTypeMaster> updateQuestionTypeMaster(@Valid @RequestBody QuestionTypeMaster questionTypeMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionTypeMaster : {}", questionTypeMaster);
        if (questionTypeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionTypeMaster result = questionTypeMasterService.save(questionTypeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionTypeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-type-masters} : get all the questionTypeMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionTypeMasters in body.
     */
    @GetMapping("/question-type-masters")
    public ResponseEntity<List<QuestionTypeMaster>> getAllQuestionTypeMasters(QuestionTypeMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionTypeMasters by criteria: {}", criteria);
        Page<QuestionTypeMaster> page = questionTypeMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-type-masters/count} : count all the questionTypeMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-type-masters/count")
    public ResponseEntity<Long> countQuestionTypeMasters(QuestionTypeMasterCriteria criteria) {
        log.debug("REST request to count QuestionTypeMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionTypeMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-type-masters/:id} : get the "id" questionTypeMaster.
     *
     * @param id the id of the questionTypeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionTypeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-type-masters/{id}")
    public ResponseEntity<QuestionTypeMaster> getQuestionTypeMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionTypeMaster : {}", id);
        Optional<QuestionTypeMaster> questionTypeMaster = questionTypeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionTypeMaster);
    }

    /**
     * {@code DELETE  /question-type-masters/:id} : delete the "id" questionTypeMaster.
     *
     * @param id the id of the questionTypeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-type-masters/{id}")
    public ResponseEntity<Void> deleteQuestionTypeMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionTypeMaster : {}", id);
        questionTypeMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
