package com.qpg.web.rest;

import com.qpg.domain.QuestionBluePrintDetail;
import com.qpg.service.QuestionBluePrintDetailService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QuestionBluePrintDetailsCriteria;
import com.qpg.service.QuestionBluePrintDetailQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link QuestionBluePrintDetail}.
 */
@RestController
@RequestMapping("/api")
public class QuestionBluePrintDetailResource {

    private final Logger log = LoggerFactory.getLogger(QuestionBluePrintDetailResource.class);

    private static final String ENTITY_NAME = "questionBluePrintDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionBluePrintDetailService questionBluePrintDetailService;

    private final QuestionBluePrintDetailQueryService questionBluePrintDetailQueryService;

    public QuestionBluePrintDetailResource(QuestionBluePrintDetailService questionBluePrintDetailService, QuestionBluePrintDetailQueryService questionBluePrintDetailQueryService) {
        this.questionBluePrintDetailService = questionBluePrintDetailService;
        this.questionBluePrintDetailQueryService = questionBluePrintDetailQueryService;
    }

    /**
     * {@code POST  /question-blue-print-details} : Create a new questionBluePrintDetail.
     *
     * @param questionBluePrintDetail the questionBluePrintDetail to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionBluePrintDetail, or with status {@code 400 (Bad Request)} if the questionBluePrintDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-blue-print-details")
    public ResponseEntity<QuestionBluePrintDetail> createQuestionBluePrintDetails(@Valid @RequestBody QuestionBluePrintDetail questionBluePrintDetail) throws URISyntaxException {
        log.debug("REST request to save QuestionBluePrintDetail : {}", questionBluePrintDetail);
        if (questionBluePrintDetail.getId() != null) {
            throw new BadRequestAlertException("A new questionBluePrintDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionBluePrintDetail result = questionBluePrintDetailService.save(questionBluePrintDetail);
        return ResponseEntity.created(new URI("/api/question-blue-print-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-blue-print-details} : Updates an existing questionBluePrintDetail.
     *
     * @param questionBluePrintDetail the questionBluePrintDetail to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionBluePrintDetail,
     * or with status {@code 400 (Bad Request)} if the questionBluePrintDetail is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionBluePrintDetail couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-blue-print-details")
    public ResponseEntity<QuestionBluePrintDetail> updateQuestionBluePrintDetails(@Valid @RequestBody QuestionBluePrintDetail questionBluePrintDetail) throws URISyntaxException {
        log.debug("REST request to update QuestionBluePrintDetail : {}", questionBluePrintDetail);
        if (questionBluePrintDetail.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionBluePrintDetail result = questionBluePrintDetailService.save(questionBluePrintDetail);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionBluePrintDetail.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-blue-print-details} : get all the questionBluePrintDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionBluePrintDetails in body.
     */
    @GetMapping("/question-blue-print-details")
    public ResponseEntity<List<QuestionBluePrintDetail>> getAllQuestionBluePrintDetails(QuestionBluePrintDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionBluePrintDetail by criteria: {}", criteria);
        Page<QuestionBluePrintDetail> page = questionBluePrintDetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-blue-print-details/count} : count all the questionBluePrintDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-blue-print-details/count")
    public ResponseEntity<Long> countQuestionBluePrintDetails(QuestionBluePrintDetailsCriteria criteria) {
        log.debug("REST request to count QuestionBluePrintDetail by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionBluePrintDetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-blue-print-details/:id} : get the "id" questionBluePrintDetails.
     *
     * @param id the id of the questionBluePrintDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionBluePrintDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-blue-print-details/{id}")
    public ResponseEntity<QuestionBluePrintDetail> getQuestionBluePrintDetails(@PathVariable Long id) {
        log.debug("REST request to get QuestionBluePrintDetail : {}", id);
        Optional<QuestionBluePrintDetail> questionBluePrintDetails = questionBluePrintDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionBluePrintDetails);
    }

    /**
     * {@code DELETE  /question-blue-print-details/:id} : delete the "id" questionBluePrintDetails.
     *
     * @param id the id of the questionBluePrintDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-blue-print-details/{id}")
    public ResponseEntity<Void> deleteQuestionBluePrintDetails(@PathVariable Long id) {
        log.debug("REST request to delete QuestionBluePrintDetail : {}", id);
        questionBluePrintDetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
