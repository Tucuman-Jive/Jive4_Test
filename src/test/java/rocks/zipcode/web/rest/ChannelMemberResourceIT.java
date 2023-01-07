package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.IntegrationTest;
import rocks.zipcode.domain.ChannelMember;
import rocks.zipcode.repository.ChannelMemberRepository;
import rocks.zipcode.service.dto.ChannelMemberDTO;
import rocks.zipcode.service.mapper.ChannelMemberMapper;

/**
 * Integration tests for the {@link ChannelMemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChannelMemberResourceIT {

    private static final String ENTITY_API_URL = "/api/channel-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChannelMemberRepository channelMemberRepository;

    @Autowired
    private ChannelMemberMapper channelMemberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChannelMemberMockMvc;

    private ChannelMember channelMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChannelMember createEntity(EntityManager em) {
        ChannelMember channelMember = new ChannelMember();
        return channelMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChannelMember createUpdatedEntity(EntityManager em) {
        ChannelMember channelMember = new ChannelMember();
        return channelMember;
    }

    @BeforeEach
    public void initTest() {
        channelMember = createEntity(em);
    }

    @Test
    @Transactional
    void createChannelMember() throws Exception {
        int databaseSizeBeforeCreate = channelMemberRepository.findAll().size();
        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);
        restChannelMemberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeCreate + 1);
        ChannelMember testChannelMember = channelMemberList.get(channelMemberList.size() - 1);
    }

    @Test
    @Transactional
    void createChannelMemberWithExistingId() throws Exception {
        // Create the ChannelMember with an existing ID
        channelMember.setId(1L);
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        int databaseSizeBeforeCreate = channelMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMemberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChannelMembers() throws Exception {
        // Initialize the database
        channelMemberRepository.saveAndFlush(channelMember);

        // Get all the channelMemberList
        restChannelMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channelMember.getId().intValue())));
    }

    @Test
    @Transactional
    void getChannelMember() throws Exception {
        // Initialize the database
        channelMemberRepository.saveAndFlush(channelMember);

        // Get the channelMember
        restChannelMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, channelMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(channelMember.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingChannelMember() throws Exception {
        // Get the channelMember
        restChannelMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChannelMember() throws Exception {
        // Initialize the database
        channelMemberRepository.saveAndFlush(channelMember);

        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();

        // Update the channelMember
        ChannelMember updatedChannelMember = channelMemberRepository.findById(channelMember.getId()).get();
        // Disconnect from session so that the updates on updatedChannelMember are not directly saved in db
        em.detach(updatedChannelMember);
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(updatedChannelMember);

        restChannelMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, channelMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
        ChannelMember testChannelMember = channelMemberList.get(channelMemberList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingChannelMember() throws Exception {
        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();
        channelMember.setId(count.incrementAndGet());

        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, channelMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChannelMember() throws Exception {
        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();
        channelMember.setId(count.incrementAndGet());

        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChannelMember() throws Exception {
        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();
        channelMember.setId(count.incrementAndGet());

        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMemberMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChannelMemberWithPatch() throws Exception {
        // Initialize the database
        channelMemberRepository.saveAndFlush(channelMember);

        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();

        // Update the channelMember using partial update
        ChannelMember partialUpdatedChannelMember = new ChannelMember();
        partialUpdatedChannelMember.setId(channelMember.getId());

        restChannelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChannelMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChannelMember))
            )
            .andExpect(status().isOk());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
        ChannelMember testChannelMember = channelMemberList.get(channelMemberList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateChannelMemberWithPatch() throws Exception {
        // Initialize the database
        channelMemberRepository.saveAndFlush(channelMember);

        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();

        // Update the channelMember using partial update
        ChannelMember partialUpdatedChannelMember = new ChannelMember();
        partialUpdatedChannelMember.setId(channelMember.getId());

        restChannelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChannelMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChannelMember))
            )
            .andExpect(status().isOk());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
        ChannelMember testChannelMember = channelMemberList.get(channelMemberList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingChannelMember() throws Exception {
        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();
        channelMember.setId(count.incrementAndGet());

        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, channelMemberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChannelMember() throws Exception {
        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();
        channelMember.setId(count.incrementAndGet());

        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChannelMember() throws Exception {
        int databaseSizeBeforeUpdate = channelMemberRepository.findAll().size();
        channelMember.setId(count.incrementAndGet());

        // Create the ChannelMember
        ChannelMemberDTO channelMemberDTO = channelMemberMapper.toDto(channelMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(channelMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChannelMember in the database
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChannelMember() throws Exception {
        // Initialize the database
        channelMemberRepository.saveAndFlush(channelMember);

        int databaseSizeBeforeDelete = channelMemberRepository.findAll().size();

        // Delete the channelMember
        restChannelMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, channelMember.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChannelMember> channelMemberList = channelMemberRepository.findAll();
        assertThat(channelMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
