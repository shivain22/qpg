package com.qpg.web.rest;

import com.qpg.domain.CourseMaster;
import com.qpg.service.CourseMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.CourseMasterCriteria;
import com.qpg.service.CourseMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.CourseMaster}.
 */
@RestController
@RequestMapping("/api")
public class CourseMasterResource {

    private final Logger log = LoggerFactory.getLogger(CourseMasterResource.class);

    private static final String ENTITY_NAME = "courseMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseMasterService courseMasterService;

    private final CourseMasterQueryService courseMasterQueryService;

    public CourseMasterResource(CourseMasterService courseMasterService, CourseMasterQueryService courseMasterQueryService) {
        this.courseMasterService = courseMasterService;
        this.courseMasterQueryService = courseMasterQueryService;
    }

    /**
     * {@code POST  /course-masters} : Create a new courseMaster.
     *
     * @param courseMaster the courseMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseMaster, or with status {@code 400 (Bad Request)} if the courseMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-masters")
    public ResponseEntity<CourseMaster> createCourseMaster(@Valid @RequestBody CourseMaster courseMaster) throws URISyntaxException {
        log.debug("REST request to save CourseMaster : {}", courseMaster);
        if (courseMaster.getId() != null) {
            throw new BadRequestAlertException("A new courseMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseMaster result = courseMasterService.save(courseMaster);
        return ResponseEntity.created(new URI("/api/course-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-masters} : Updates an existing courseMaster.
     *
     * @param courseMaster the courseMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseMaster,
     * or with status {@code 400 (Bad Request)} if the courseMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-masters")
    public ResponseEntity<CourseMaster> updateCourseMaster(@Valid @RequestBody CourseMaster courseMaster) throws URISyntaxException {
        log.debug("REST request to update CourseMaster : {}", courseMaster);
        if (courseMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseMaster result = courseMasterService.save(courseMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, courseMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-masters} : get all the courseMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseMasters in body.
     */
    @GetMapping("/course-masters")
    public ResponseEntity<List<CourseMaster>> getAllCourseMasters(CourseMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CourseMasters by criteria: {}", criteria);
        Page<CourseMaster> page = courseMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-masters/count} : count all the courseMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/course-masters/count")
    public ResponseEntity<Long> countCourseMasters(CourseMasterCriteria criteria) {
        log.debug("REST request to count CourseMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /course-masters/:id} : get the "id" courseMaster.
     *
     * @param id the id of the courseMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-masters/{id}")
    public ResponseEntity<CourseMaster> getCourseMaster(@PathVariable Long id) {
        log.debug("REST request to get CourseMaster : {}", id);
        Optional<CourseMaster> courseMaster = courseMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseMaster);
    }

    /**
     * {@code DELETE  /course-masters/:id} : delete the "id" courseMaster.
     *
     * @param id the id of the courseMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-masters/{id}")
    public ResponseEntity<Void> deleteCourseMaster(@PathVariable Long id) {
        log.debug("REST request to delete CourseMaster : {}", id);
        courseMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
