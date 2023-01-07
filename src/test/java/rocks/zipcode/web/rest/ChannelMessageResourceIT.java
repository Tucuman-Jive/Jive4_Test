package rocks.zipcode.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import rocks.zipcode.domain.ChannelMessage;
import rocks.zipcode.repository.ChannelMessageRepository;
import rocks.zipcode.service.dto.ChannelMessageDTO;
import rocks.zipcode.service.mapper.ChannelMessageMapper;

/**
 * Integration tests for the {@link ChannelMessageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChannelMessageResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/channel-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChannelMessageRepository channelMessageRepository;

    @Autowired
    private ChannelMessageMapper channelMessageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChannelMessageMockMvc;

    private ChannelMessage channelMessage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChannelMessage createEntity(EntityManager em) {
        ChannelMessage channelMessage = new ChannelMessage().message(DEFAULT_MESSAGE).updatedAt(DEFAULT_UPDATED_AT);
        return channelMessage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChannelMessage createUpdatedEntity(EntityManager em) {
        ChannelMessage channelMessage = new ChannelMessage().message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);
        return channelMessage;
    }

    @BeforeEach
    public void initTest() {
        channelMessage = createEntity(em);
    }

    @Test
    @Transactional
    void createChannelMessage() throws Exception {
        int databaseSizeBeforeCreate = channelMessageRepository.findAll().size();
        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);
        restChannelMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeCreate + 1);
        ChannelMessage testChannelMessage = channelMessageList.get(channelMessageList.size() - 1);
        assertThat(testChannelMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testChannelMessage.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createChannelMessageWithExistingId() throws Exception {
        // Create the ChannelMessage with an existing ID
        channelMessage.setId(1L);
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        int databaseSizeBeforeCreate = channelMessageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChannelMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChannelMessages() throws Exception {
        // Initialize the database
        channelMessageRepository.saveAndFlush(channelMessage);

        // Get all the channelMessageList
        restChannelMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(channelMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getChannelMessage() throws Exception {
        // Initialize the database
        channelMessageRepository.saveAndFlush(channelMessage);

        // Get the channelMessage
        restChannelMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, channelMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(channelMessage.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingChannelMessage() throws Exception {
        // Get the channelMessage
        restChannelMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChannelMessage() throws Exception {
        // Initialize the database
        channelMessageRepository.saveAndFlush(channelMessage);

        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();

        // Update the channelMessage
        ChannelMessage updatedChannelMessage = channelMessageRepository.findById(channelMessage.getId()).get();
        // Disconnect from session so that the updates on updatedChannelMessage are not directly saved in db
        em.detach(updatedChannelMessage);
        updatedChannelMessage.message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(updatedChannelMessage);

        restChannelMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, channelMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
        ChannelMessage testChannelMessage = channelMessageList.get(channelMessageList.size() - 1);
        assertThat(testChannelMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testChannelMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingChannelMessage() throws Exception {
        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();
        channelMessage.setId(count.incrementAndGet());

        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, channelMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChannelMessage() throws Exception {
        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();
        channelMessage.setId(count.incrementAndGet());

        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChannelMessage() throws Exception {
        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();
        channelMessage.setId(count.incrementAndGet());

        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMessageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChannelMessageWithPatch() throws Exception {
        // Initialize the database
        channelMessageRepository.saveAndFlush(channelMessage);

        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();

        // Update the channelMessage using partial update
        ChannelMessage partialUpdatedChannelMessage = new ChannelMessage();
        partialUpdatedChannelMessage.setId(channelMessage.getId());

        partialUpdatedChannelMessage.message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);

        restChannelMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChannelMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChannelMessage))
            )
            .andExpect(status().isOk());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
        ChannelMessage testChannelMessage = channelMessageList.get(channelMessageList.size() - 1);
        assertThat(testChannelMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testChannelMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateChannelMessageWithPatch() throws Exception {
        // Initialize the database
        channelMessageRepository.saveAndFlush(channelMessage);

        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();

        // Update the channelMessage using partial update
        ChannelMessage partialUpdatedChannelMessage = new ChannelMessage();
        partialUpdatedChannelMessage.setId(channelMessage.getId());

        partialUpdatedChannelMessage.message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);

        restChannelMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChannelMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChannelMessage))
            )
            .andExpect(status().isOk());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
        ChannelMessage testChannelMessage = channelMessageList.get(channelMessageList.size() - 1);
        assertThat(testChannelMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testChannelMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingChannelMessage() throws Exception {
        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();
        channelMessage.setId(count.incrementAndGet());

        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChannelMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, channelMessageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChannelMessage() throws Exception {
        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();
        channelMessage.setId(count.incrementAndGet());

        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChannelMessage() throws Exception {
        int databaseSizeBeforeUpdate = channelMessageRepository.findAll().size();
        channelMessage.setId(count.incrementAndGet());

        // Create the ChannelMessage
        ChannelMessageDTO channelMessageDTO = channelMessageMapper.toDto(channelMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChannelMessageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(channelMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChannelMessage in the database
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChannelMessage() throws Exception {
        // Initialize the database
        channelMessageRepository.saveAndFlush(channelMessage);

        int databaseSizeBeforeDelete = channelMessageRepository.findAll().size();

        // Delete the channelMessage
        restChannelMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, channelMessage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChannelMessage> channelMessageList = channelMessageRepository.findAll();
        assertThat(channelMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
