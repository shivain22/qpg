package com.qpg.web.rest;

import com.qpg.domain.QuestionBluePrintDetail;
import com.qpg.domain.QuestionBluePrintMaster;
import com.qpg.service.QuestionBluePrintDetailService;
import com.qpg.service.QuestionBluePrintMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.QuestionBluePrintMasterCriteria;
import com.qpg.service.QuestionBluePrintMasterQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.qpg.domain.QuestionBluePrintMaster}.
 */
@RestController
@RequestMapping("/api")
public class QuestionBluePrintMasterResource {

    private final Logger log = LoggerFactory.getLogger(QuestionBluePrintMasterResource.class);

    private static final String ENTITY_NAME = "questionBluePrintMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionBluePrintMasterService questionBluePrintMasterService;

    @Autowired
    private QuestionBluePrintDetailService questionBluePrintDetailService;

    private final QuestionBluePrintMasterQueryService questionBluePrintMasterQueryService;

    public QuestionBluePrintMasterResource(QuestionBluePrintMasterService questionBluePrintMasterService, QuestionBluePrintMasterQueryService questionBluePrintMasterQueryService) {
        this.questionBluePrintMasterService = questionBluePrintMasterService;
        this.questionBluePrintMasterQueryService = questionBluePrintMasterQueryService;
    }

    /**
     * {@code POST  /question-blue-print-masters} : Create a new questionBluePrintMaster.
     *
     * @param questionBluePrintMaster the questionBluePrintMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionBluePrintMaster, or with status {@code 400 (Bad Request)} if the questionBluePrintMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-blue-print-masters")
    public ResponseEntity<QuestionBluePrintMaster> createQuestionBluePrintMaster(@Valid @RequestBody QuestionBluePrintMaster questionBluePrintMaster) throws URISyntaxException {
        log.debug("REST request to save QuestionBluePrintMaster : {}", questionBluePrintMaster);
        for(QuestionBluePrintDetail qbpdtls:questionBluePrintMaster.getQuestionBluePrintDetails()){
            qbpdtls.setQuestionBluePrintMaster(questionBluePrintMaster);
        }
        if (questionBluePrintMaster.getId() != null) {
            throw new BadRequestAlertException("A new questionBluePrintMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionBluePrintMaster result = questionBluePrintMasterService.save(questionBluePrintMaster);
        return ResponseEntity.created(new URI("/api/question-blue-print-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-blue-print-masters} : Updates an existing questionBluePrintMaster.
     *
     * @param questionBluePrintMaster the questionBluePrintMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionBluePrintMaster,
     * or with status {@code 400 (Bad Request)} if the questionBluePrintMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionBluePrintMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-blue-print-masters")
    public ResponseEntity<QuestionBluePrintMaster> updateQuestionBluePrintMaster(@Valid @RequestBody QuestionBluePrintMaster questionBluePrintMaster) throws URISyntaxException {
        log.debug("REST request to update QuestionBluePrintMaster : {}", questionBluePrintMaster);
        if (questionBluePrintMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        for(QuestionBluePrintDetail qbpdtls:questionBluePrintMaster.getQuestionBluePrintDetails()){
            qbpdtls.setQuestionBluePrintMaster(questionBluePrintMaster);
            //questionBluePrintDetailService.save(qbpdtls);
        }
        QuestionBluePrintMaster result = questionBluePrintMasterService.save(questionBluePrintMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionBluePrintMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-blue-print-masters} : get all the questionBluePrintMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionBluePrintMasters in body.
     */
    @GetMapping("/question-blue-print-masters")
    public ResponseEntity<List<QuestionBluePrintMaster>> getAllQuestionBluePrintMasters(QuestionBluePrintMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get QuestionBluePrintMasters by criteria: {}", criteria);
        Page<QuestionBluePrintMaster> page = questionBluePrintMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /question-blue-print-masters/count} : count all the questionBluePrintMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/question-blue-print-masters/count")
    public ResponseEntity<Long> countQuestionBluePrintMasters(QuestionBluePrintMasterCriteria criteria) {
        log.debug("REST request to count QuestionBluePrintMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(questionBluePrintMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /question-blue-print-masters/:id} : get the "id" questionBluePrintMaster.
     *
     * @param id the id of the questionBluePrintMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionBluePrintMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-blue-print-masters/{id}")
    public ResponseEntity<QuestionBluePrintMaster> getQuestionBluePrintMaster(@PathVariable Long id) {
        log.debug("REST request to get QuestionBluePrintMaster : {}", id);
        Optional<QuestionBluePrintMaster> questionBluePrintMaster = questionBluePrintMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionBluePrintMaster);
    }

    /**
     * {@code DELETE  /question-blue-print-masters/:id} : delete the "id" questionBluePrintMaster.
     *
     * @param id the id of the questionBluePrintMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-blue-print-masters/{id}")
    public ResponseEntity<Void> deleteQuestionBluePrintMaster(@PathVariable Long id) {
        log.debug("REST request to delete QuestionBluePrintMaster : {}", id);
        questionBluePrintMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
