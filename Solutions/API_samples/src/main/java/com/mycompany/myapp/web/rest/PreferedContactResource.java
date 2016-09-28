package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PreferedContact;

import com.mycompany.myapp.repository.PreferedContactRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PreferedContact.
 */
@RestController
@RequestMapping("/api")
public class PreferedContactResource {

    private final Logger log = LoggerFactory.getLogger(PreferedContactResource.class);
        
    @Inject
    private PreferedContactRepository preferedContactRepository;

    /**
     * POST  /prefered-contacts : Create a new preferedContact.
     *
     * @param preferedContact the preferedContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preferedContact, or with status 400 (Bad Request) if the preferedContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/prefered-contacts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreferedContact> createPreferedContact(@Valid @RequestBody PreferedContact preferedContact) throws URISyntaxException {
        log.debug("REST request to save PreferedContact : {}", preferedContact);
        if (preferedContact.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("preferedContact", "idexists", "A new preferedContact cannot already have an ID")).body(null);
        }
        PreferedContact result = preferedContactRepository.save(preferedContact);
        return ResponseEntity.created(new URI("/api/prefered-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("preferedContact", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prefered-contacts : Updates an existing preferedContact.
     *
     * @param preferedContact the preferedContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preferedContact,
     * or with status 400 (Bad Request) if the preferedContact is not valid,
     * or with status 500 (Internal Server Error) if the preferedContact couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/prefered-contacts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreferedContact> updatePreferedContact(@Valid @RequestBody PreferedContact preferedContact) throws URISyntaxException {
        log.debug("REST request to update PreferedContact : {}", preferedContact);
        if (preferedContact.getId() == null) {
            return createPreferedContact(preferedContact);
        }
        PreferedContact result = preferedContactRepository.save(preferedContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("preferedContact", preferedContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prefered-contacts : get all the preferedContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preferedContacts in body
     */
    @RequestMapping(value = "/prefered-contacts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PreferedContact> getAllPreferedContacts() {
        log.debug("REST request to get all PreferedContacts");
        List<PreferedContact> preferedContacts = preferedContactRepository.findAll();
        return preferedContacts;
    }

    /**
     * GET  /prefered-contacts/:id : get the "id" preferedContact.
     *
     * @param id the id of the preferedContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preferedContact, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/prefered-contacts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreferedContact> getPreferedContact(@PathVariable Long id) {
        log.debug("REST request to get PreferedContact : {}", id);
        PreferedContact preferedContact = preferedContactRepository.findOne(id);
        return Optional.ofNullable(preferedContact)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prefered-contacts/:id : delete the "id" preferedContact.
     *
     * @param id the id of the preferedContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/prefered-contacts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePreferedContact(@PathVariable Long id) {
        log.debug("REST request to delete PreferedContact : {}", id);
        preferedContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("preferedContact", id.toString())).build();
    }

}
