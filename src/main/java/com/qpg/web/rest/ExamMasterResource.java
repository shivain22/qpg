package com.qpg.web.rest;

import com.qpg.domain.ExamMaster;
import com.qpg.service.ExamMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.ExamMasterCriteria;
import com.qpg.service.ExamMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.ExamMaster}.
 */
@RestController
@RequestMapping("/api")
public class ExamMasterResource {

    private final Logger log = LoggerFactory.getLogger(ExamMasterResource.class);

    private static final String ENTITY_NAME = "examMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamMasterService examMasterService;

    private final ExamMasterQueryService examMasterQueryService;

    public ExamMasterResource(ExamMasterService examMasterService, ExamMasterQueryService examMasterQueryService) {
        this.examMasterService = examMasterService;
        this.examMasterQueryService = examMasterQueryService;
    }

    /**
     * {@code POST  /exam-masters} : Create a new examMaster.
     *
     * @param examMaster the examMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examMaster, or with status {@code 400 (Bad Request)} if the examMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-masters")
    public ResponseEntity<ExamMaster> createExamMaster(@Valid @RequestBody ExamMaster examMaster) throws URISyntaxException {
        log.debug("REST request to save ExamMaster : {}", examMaster);
        if (examMaster.getId() != null) {
            throw new BadRequestAlertException("A new examMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(examMaster.getSubjectMaster().getId()==null){
            examMaster.setSubjectMaster(null);
        }
        if(examMaster.getSubSubjectMaster().getId()==null){
            examMaster.setSubSubjectMaster(null);
        }
        if(examMaster.getTopicMaster().getId()==null){
            examMaster.setTopicMaster(null);
        }
        if(examMaster.getSubTopicMaster().getId()==null){
            examMaster.setSubTopicMaster(null);
        }
        ExamMaster result = examMasterService.save(examMaster);
        return ResponseEntity.created(new URI("/api/exam-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-masters} : Updates an existing examMaster.
     *
     * @param examMaster the examMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examMaster,
     * or with status {@code 400 (Bad Request)} if the examMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-masters")
    public ResponseEntity<ExamMaster> updateExamMaster(@Valid @RequestBody ExamMaster examMaster) throws URISyntaxException {
        log.debug("REST request to update ExamMaster : {}", examMaster);
        if(examMaster.getSubjectMaster().getId()==null){
            examMaster.setSubjectMaster(null);
        }
        if(examMaster.getSubSubjectMaster().getId()==null){
            examMaster.setSubSubjectMaster(null);
        }
        if(examMaster.getTopicMaster().getId()==null){
            examMaster.setTopicMaster(null);
        }
        if(examMaster.getSubTopicMaster().getId()==null){
            examMaster.setSubTopicMaster(null);
        }
        if (examMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamMaster result = examMasterService.save(examMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-masters} : Updates an existing examMaster.
     *
     * @param examMaster the examMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examMaster,
     * or with status {@code 400 (Bad Request)} if the examMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-masters/{id}")
    public ResponseEntity<ExamMaster> generateQuestionPaper(@Valid @RequestBody ExamMaster examMaster,@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to update ExamMaster : {}", examMaster);
        if (examMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
         examMasterService.createQuestionPaper(examMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examMaster.getId().toString()))
            .body(examMaster);
    }

    /**
     * {@code GET  /exam-masters} : get all the examMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examMasters in body.
     */
    @GetMapping("/exam-masters")
    public ResponseEntity<List<ExamMaster>> getAllExamMasters(ExamMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExamMasters by criteria: {}", criteria);
        Page<ExamMaster> page = examMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exam-masters/count} : count all the examMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/exam-masters/count")
    public ResponseEntity<Long> countExamMasters(ExamMasterCriteria criteria) {
        log.debug("REST request to count ExamMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(examMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /exam-masters/:id} : get the "id" examMaster.
     *
     * @param id the id of the examMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-masters/{id}")
    public ResponseEntity<ExamMaster> getExamMaster(@PathVariable Long id) {
        log.debug("REST request to get ExamMaster : {}", id);
        Optional<ExamMaster> examMaster = examMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examMaster);
    }

    /**
     * {@code DELETE  /exam-masters/:id} : delete the "id" examMaster.
     *
     * @param id the id of the examMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-masters/{id}")
    public ResponseEntity<Void> deleteExamMaster(@PathVariable Long id) {
        log.debug("REST request to delete ExamMaster : {}", id);
        examMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
