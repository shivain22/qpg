package com.qpg.web.rest;

import com.qpg.domain.TopicMaster;
import com.qpg.service.TopicMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.TopicMasterCriteria;
import com.qpg.service.TopicMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.TopicMaster}.
 */
@RestController
@RequestMapping("/api")
public class TopicMasterResource {

    private final Logger log = LoggerFactory.getLogger(TopicMasterResource.class);

    private static final String ENTITY_NAME = "topicMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicMasterService topicMasterService;

    private final TopicMasterQueryService topicMasterQueryService;

    public TopicMasterResource(TopicMasterService topicMasterService, TopicMasterQueryService topicMasterQueryService) {
        this.topicMasterService = topicMasterService;
        this.topicMasterQueryService = topicMasterQueryService;
    }

    /**
     * {@code POST  /topic-masters} : Create a new topicMaster.
     *
     * @param topicMaster the topicMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicMaster, or with status {@code 400 (Bad Request)} if the topicMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-masters")
    public ResponseEntity<TopicMaster> createTopicMaster(@Valid @RequestBody TopicMaster topicMaster) throws URISyntaxException {
        log.debug("REST request to save TopicMaster : {}", topicMaster);
        if (topicMaster.getId() != null) {
            throw new BadRequestAlertException("A new topicMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicMaster result = topicMasterService.save(topicMaster);
        return ResponseEntity.created(new URI("/api/topic-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-masters} : Updates an existing topicMaster.
     *
     * @param topicMaster the topicMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicMaster,
     * or with status {@code 400 (Bad Request)} if the topicMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-masters")
    public ResponseEntity<TopicMaster> updateTopicMaster(@Valid @RequestBody TopicMaster topicMaster) throws URISyntaxException {
        log.debug("REST request to update TopicMaster : {}", topicMaster);
        if (topicMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicMaster result = topicMasterService.save(topicMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-masters} : get all the topicMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicMasters in body.
     */
    @GetMapping("/topic-masters")
    public ResponseEntity<List<TopicMaster>> getAllTopicMasters(TopicMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TopicMasters by criteria: {}", criteria);
        Page<TopicMaster> page = topicMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-masters/count} : count all the topicMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/topic-masters/count")
    public ResponseEntity<Long> countTopicMasters(TopicMasterCriteria criteria) {
        log.debug("REST request to count TopicMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(topicMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /topic-masters/:id} : get the "id" topicMaster.
     *
     * @param id the id of the topicMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-masters/{id}")
    public ResponseEntity<TopicMaster> getTopicMaster(@PathVariable Long id) {
        log.debug("REST request to get TopicMaster : {}", id);
        Optional<TopicMaster> topicMaster = topicMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicMaster);
    }

    /**
     * {@code DELETE  /topic-masters/:id} : delete the "id" topicMaster.
     *
     * @param id the id of the topicMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-masters/{id}")
    public ResponseEntity<Void> deleteTopicMaster(@PathVariable Long id) {
        log.debug("REST request to delete TopicMaster : {}", id);
        topicMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
