package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ApiSamplesApp;

import com.mycompany.myapp.domain.PreferedContact;
import com.mycompany.myapp.repository.PreferedContactRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PreferedContactResource REST controller.
 *
 * @see PreferedContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiSamplesApp.class)
public class PreferedContactResourceIntTest {

    private static final String DEFAULT_NAME_OF_CHOICE = "AAAAA";
    private static final String UPDATED_NAME_OF_CHOICE = "BBBBB";

    @Inject
    private PreferedContactRepository preferedContactRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPreferedContactMockMvc;

    private PreferedContact preferedContact;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PreferedContactResource preferedContactResource = new PreferedContactResource();
        ReflectionTestUtils.setField(preferedContactResource, "preferedContactRepository", preferedContactRepository);
        this.restPreferedContactMockMvc = MockMvcBuilders.standaloneSetup(preferedContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreferedContact createEntity(EntityManager em) {
        PreferedContact preferedContact = new PreferedContact()
                .nameOfChoice(DEFAULT_NAME_OF_CHOICE);
        return preferedContact;
    }

    @Before
    public void initTest() {
        preferedContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreferedContact() throws Exception {
        int databaseSizeBeforeCreate = preferedContactRepository.findAll().size();

        // Create the PreferedContact

        restPreferedContactMockMvc.perform(post("/api/prefered-contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(preferedContact)))
                .andExpect(status().isCreated());

        // Validate the PreferedContact in the database
        List<PreferedContact> preferedContacts = preferedContactRepository.findAll();
        assertThat(preferedContacts).hasSize(databaseSizeBeforeCreate + 1);
        PreferedContact testPreferedContact = preferedContacts.get(preferedContacts.size() - 1);
        assertThat(testPreferedContact.getNameOfChoice()).isEqualTo(DEFAULT_NAME_OF_CHOICE);
    }

    @Test
    @Transactional
    public void checkNameOfChoiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = preferedContactRepository.findAll().size();
        // set the field null
        preferedContact.setNameOfChoice(null);

        // Create the PreferedContact, which fails.

        restPreferedContactMockMvc.perform(post("/api/prefered-contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(preferedContact)))
                .andExpect(status().isBadRequest());

        List<PreferedContact> preferedContacts = preferedContactRepository.findAll();
        assertThat(preferedContacts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPreferedContacts() throws Exception {
        // Initialize the database
        preferedContactRepository.saveAndFlush(preferedContact);

        // Get all the preferedContacts
        restPreferedContactMockMvc.perform(get("/api/prefered-contacts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(preferedContact.getId().intValue())))
                .andExpect(jsonPath("$.[*].nameOfChoice").value(hasItem(DEFAULT_NAME_OF_CHOICE.toString())));
    }

    @Test
    @Transactional
    public void getPreferedContact() throws Exception {
        // Initialize the database
        preferedContactRepository.saveAndFlush(preferedContact);

        // Get the preferedContact
        restPreferedContactMockMvc.perform(get("/api/prefered-contacts/{id}", preferedContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preferedContact.getId().intValue()))
            .andExpect(jsonPath("$.nameOfChoice").value(DEFAULT_NAME_OF_CHOICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreferedContact() throws Exception {
        // Get the preferedContact
        restPreferedContactMockMvc.perform(get("/api/prefered-contacts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreferedContact() throws Exception {
        // Initialize the database
        preferedContactRepository.saveAndFlush(preferedContact);
        int databaseSizeBeforeUpdate = preferedContactRepository.findAll().size();

        // Update the preferedContact
        PreferedContact updatedPreferedContact = preferedContactRepository.findOne(preferedContact.getId());
        updatedPreferedContact
                .nameOfChoice(UPDATED_NAME_OF_CHOICE);

        restPreferedContactMockMvc.perform(put("/api/prefered-contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPreferedContact)))
                .andExpect(status().isOk());

        // Validate the PreferedContact in the database
        List<PreferedContact> preferedContacts = preferedContactRepository.findAll();
        assertThat(preferedContacts).hasSize(databaseSizeBeforeUpdate);
        PreferedContact testPreferedContact = preferedContacts.get(preferedContacts.size() - 1);
        assertThat(testPreferedContact.getNameOfChoice()).isEqualTo(UPDATED_NAME_OF_CHOICE);
    }

    @Test
    @Transactional
    public void deletePreferedContact() throws Exception {
        // Initialize the database
        preferedContactRepository.saveAndFlush(preferedContact);
        int databaseSizeBeforeDelete = preferedContactRepository.findAll().size();

        // Get the preferedContact
        restPreferedContactMockMvc.perform(delete("/api/prefered-contacts/{id}", preferedContact.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PreferedContact> preferedContacts = preferedContactRepository.findAll();
        assertThat(preferedContacts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
