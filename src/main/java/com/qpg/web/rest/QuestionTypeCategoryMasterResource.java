package com.qpg.web.rest;

import com.qpg.domain.QuestionTypeCategoryMaster;
import com.qpg.service.QuestionTypeCategoryMasterQueryService;
import com.qpg.service.QuestionTypeCategoryMasterService;
import com.qpg.service.dto.QuestionTypeCategoryMasterCriteria;
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
 * REST controller for managing {@link QuestionTypeCategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class QuestionTypeCategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeCategoryMasterResource.class);

    private static final String ENTITY_NAME = "questionTypeCategoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionTypeCategoryMasterService questionTypeCategoryMasterService;

    private final QuestionTypeCategoryMasterQueryService questionTypeCategoryMasterQueryService;

    public QuestionTypeCategoryMasterResource(QuestionTypeCategoryMasterService questionTypeCategoryMasterService, QuestionTypeCategoryMasterQueryService questionTypeCategoryMasterQueryService) {
        this.questionTypeCategoryMasterService = questionTypeCategoryMasterService;
        this.questionTypeCategoryMasterQueryService = questionTypeCategoryMasterQueryService;
    }

    /**
     * {@code POST  /question-type-category-masters} : Create a new questionTypeCategoryMaster.
     *
     * @param questionTypeCategoryMaster the questionTypeCategoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionTypeCategoryMaster, or with status {@code 400 (Bad Request)} if the questionTypeCategoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-type-category-masters")
    public ResponseEntity<QuestionTypeCategoryMaster> createQuestionTypeCategoryMaster(@Valid @RequestBody QuestionTypeCategoryMaster questionTypeCategoryMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionTypeCategoryMaster : {}", questionTypeCategoryMaster);
        if (questionTypeCategoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new questionTypeCategoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionTypeCategoryMaster result = questionTypeCategoryMasterService.save(questionTypeCategoryMaster);
        return ResponseEntity.created(new URI("/api/question-type-category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-type-category-masters} : Updates an existing questionTypeCategoryMaster.
     *
     * @param questionTypeCategoryMaster the questionTypeCategoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionTypeCategoryMaster,
     * or with status {@code 400 (Bad Request)} if the questionTypeCategoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionTypeCategoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-type-category-masters")
    public ResponseEntity<QuestionTypeCategoryMaster> updateQuestionTypeCategoryMaster(@Valid @RequestBody QuestionTypeCategoryMaster questionTypeCategoryMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionTypeCategoryMaster : {}", questionTypeCategoryMaster);
        if (questionTypeCategoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionTypeCategoryMaster result = questionTypeCategoryMasterService.save(questionTypeCategoryMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionTypeCategoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-type-category-masters} : get all the questionTypeCategoryMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionTypeCategoryMasters in body.
     */
    @GetMapping("/question-type-category-masters")
    public ResponseEntity<List<QuestionTypeCategoryMaster>> getAllQuestionTypeCategoryMasters(QuestionTypeCategoryMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionTypeCategoryMasters by criteria: {}", criteria);
        Page<QuestionTypeCategoryMaster> page = questionTypeCategoryMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-type-category-masters/count} : count all the questionTypeCategoryMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-type-category-masters/count")
    public ResponseEntity<Long> countQuestionTypeCategoryMasters(QuestionTypeCategoryMasterCriteria criteria) {
        log.debug("REST request to count QuestionTypeCategoryMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionTypeCategoryMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-type-category-masters/:id} : get the "id" questionTypeCategoryMaster.
     *
     * @param id the id of the questionTypeCategoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionTypeCategoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-type-category-masters/{id}")
    public ResponseEntity<QuestionTypeCategoryMaster> getQuestionTypeCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionTypeCategoryMaster : {}", id);
        Optional<QuestionTypeCategoryMaster> questionTypeCategoryMaster = questionTypeCategoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionTypeCategoryMaster);
    }

    /**
     * {@code DELETE  /question-type-category-masters/:id} : delete the "id" questionTypeCategoryMaster.
     *
     * @param id the id of the questionTypeCategoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-type-category-masters/{id}")
    public ResponseEntity<Void> deleteQuestionTypeCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionTypeCategoryMaster : {}", id);
        questionTypeCategoryMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
