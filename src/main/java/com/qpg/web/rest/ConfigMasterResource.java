package com.qpg.web.rest;

import com.qpg.domain.ConfigMaster;
import com.qpg.service.ConfigMasterQueryService;
import com.qpg.service.ConfigMasterService;
import com.qpg.service.dto.ConfigMasterCriteria;
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
 * REST controller for managing {@link ConfigMaster}.
 */
@RestController
@RequestMapping("/api")
public class ConfigMasterResource {

    private final Logger log = LoggerFactory.getLogger(ConfigMasterResource.class);

    private static final String ENTITY_NAME = "configMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfigMasterService configMasterService;

    private final ConfigMasterQueryService configMasterQueryService;

    public ConfigMasterResource(ConfigMasterService configMasterService, ConfigMasterQueryService configMasterQueryService) {
        this.configMasterService = configMasterService;
        this.configMasterQueryService = configMasterQueryService;
    }

    /**
     * {@code POST  /config-masters} : Create a new configMaster.
     *
     * @param configMaster the configMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new configMaster, or with status {@code 400 (Bad Request)} if the configMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/config-masters")
    public ResponseEntity<ConfigMaster> createConfigMaster(@Valid @RequestBody ConfigMaster configMaster) throws URISyntaxException {
        log.debug("REST request to save ConfigMaster : {}", configMaster);
        if (configMaster.getId() != null) {
            throw new BadRequestAlertException("A new configMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConfigMaster result = configMasterService.save(configMaster);
        return ResponseEntity.created(new URI("/api/config-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /config-masters} : Updates an existing configMaster.
     *
     * @param configMaster the configMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated configMaster,
     * or with status {@code 400 (Bad Request)} if the configMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the configMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/config-masters")
    public ResponseEntity<ConfigMaster> updateConfigMaster(@Valid @RequestBody ConfigMaster configMaster) throws URISyntaxException {
        log.debug("REST request to update ConfigMaster : {}", configMaster);
        if (configMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConfigMaster result = configMasterService.save(configMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, configMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /config-masters} : get all the configMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of configMasters in body.
     */
    @GetMapping("/config-masters")
    public ResponseEntity<List<ConfigMaster>> getAllConfigMasters(ConfigMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ConfigMasters by criteria: {}", criteria);
        Page<ConfigMaster> page = configMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /config-masters/count} : count all the configMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/config-masters/count")
    public ResponseEntity<Long> countConfigMasters(ConfigMasterCriteria criteria) {
        log.debug("REST request to count ConfigMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(configMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /config-masters/:id} : get the "id" configMaster.
     *
     * @param id the id of the configMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the configMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/config-masters/{id}")
    public ResponseEntity<ConfigMaster> getConfigMaster(@PathVariable Long id) {
        log.debug("REST request to get ConfigMaster : {}", id);
        Optional<ConfigMaster> configMaster = configMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(configMaster);
    }

    /**
     * {@code DELETE  /config-masters/:id} : delete the "id" configMaster.
     *
     * @param id the id of the configMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/config-masters/{id}")
    public ResponseEntity<Void> deleteConfigMaster(@PathVariable Long id) {
        log.debug("REST request to delete ConfigMaster : {}", id);
        configMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
