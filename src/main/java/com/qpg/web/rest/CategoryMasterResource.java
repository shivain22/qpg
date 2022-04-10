package com.qpg.web.rest;

import com.qpg.domain.CategoryMaster;
import com.qpg.service.CategoryMasterService;
import com.qpg.web.rest.errors.BadRequestAlertException;
import com.qpg.service.dto.CategoryMasterCriteria;
import com.qpg.service.CategoryMasterQueryService;

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
 * REST controller for managing {@link com.qpg.domain.CategoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class CategoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(CategoryMasterResource.class);

    private static final String ENTITY_NAME = "categoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryMasterService categoryMasterService;

    private final CategoryMasterQueryService categoryMasterQueryService;

    public CategoryMasterResource(CategoryMasterService categoryMasterService, CategoryMasterQueryService categoryMasterQueryService) {
        this.categoryMasterService = categoryMasterService;
        this.categoryMasterQueryService = categoryMasterQueryService;
    }

    /**
     * {@code POST  /category-masters} : Create a new categoryMaster.
     *
     * @param categoryMaster the categoryMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryMaster, or with status {@code 400 (Bad Request)} if the categoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-masters")
    public ResponseEntity<CategoryMaster> createCategoryMaster(@Valid @RequestBody CategoryMaster categoryMaster) throws URISyntaxException {
        log.debug("REST request to save CategoryMaster : {}", categoryMaster);
        if (categoryMaster.getId() != null) {
            throw new BadRequestAlertException("A new categoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryMaster result = categoryMasterService.save(categoryMaster);
        return ResponseEntity.created(new URI("/api/category-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-masters} : Updates an existing categoryMaster.
     *
     * @param categoryMaster the categoryMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryMaster,
     * or with status {@code 400 (Bad Request)} if the categoryMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-masters")
    public ResponseEntity<CategoryMaster> updateCategoryMaster(@Valid @RequestBody CategoryMaster categoryMaster) throws URISyntaxException {
        log.debug("REST request to update CategoryMaster : {}", categoryMaster);
        if (categoryMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryMaster result = categoryMasterService.save(categoryMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-masters} : get all the categoryMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryMasters in body.
     */
    @GetMapping("/category-masters")
    public ResponseEntity<List<CategoryMaster>> getAllCategoryMasters(CategoryMasterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CategoryMasters by criteria: {}", criteria);
        Page<CategoryMaster> page = categoryMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-masters/count} : count all the categoryMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/category-masters/count")
    public ResponseEntity<Long> countCategoryMasters(CategoryMasterCriteria criteria) {
        log.debug("REST request to count CategoryMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(categoryMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /category-masters/:id} : get the "id" categoryMaster.
     *
     * @param id the id of the categoryMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-masters/{id}")
    public ResponseEntity<CategoryMaster> getCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to get CategoryMaster : {}", id);
        Optional<CategoryMaster> categoryMaster = categoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryMaster);
    }

    /**
     * {@code DELETE  /category-masters/:id} : delete the "id" categoryMaster.
     *
     * @param id the id of the categoryMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-masters/{id}")
    public ResponseEntity<Void> deleteCategoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete CategoryMaster : {}", id);
        categoryMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
