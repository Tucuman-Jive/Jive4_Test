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
import rocks.zipcode.domain.UserMessage;
import rocks.zipcode.repository.UserMessageRepository;
import rocks.zipcode.service.dto.UserMessageDTO;
import rocks.zipcode.service.mapper.UserMessageMapper;

/**
 * Integration tests for the {@link UserMessageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserMessageResourceIT {

    private static final Integer DEFAULT_RECIPIENT_ID = 1;
    private static final Integer UPDATED_RECIPIENT_ID = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private UserMessageMapper userMessageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserMessageMockMvc;

    private UserMessage userMessage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMessage createEntity(EntityManager em) {
        UserMessage userMessage = new UserMessage()
            .recipientID(DEFAULT_RECIPIENT_ID)
            .message(DEFAULT_MESSAGE)
            .updatedAt(DEFAULT_UPDATED_AT);
        return userMessage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMessage createUpdatedEntity(EntityManager em) {
        UserMessage userMessage = new UserMessage()
            .recipientID(UPDATED_RECIPIENT_ID)
            .message(UPDATED_MESSAGE)
            .updatedAt(UPDATED_UPDATED_AT);
        return userMessage;
    }

    @BeforeEach
    public void initTest() {
        userMessage = createEntity(em);
    }

    @Test
    @Transactional
    void createUserMessage() throws Exception {
        int databaseSizeBeforeCreate = userMessageRepository.findAll().size();
        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);
        restUserMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeCreate + 1);
        UserMessage testUserMessage = userMessageList.get(userMessageList.size() - 1);
        assertThat(testUserMessage.getRecipientID()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testUserMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testUserMessage.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createUserMessageWithExistingId() throws Exception {
        // Create the UserMessage with an existing ID
        userMessage.setId(1L);
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        int databaseSizeBeforeCreate = userMessageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMessageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserMessages() throws Exception {
        // Initialize the database
        userMessageRepository.saveAndFlush(userMessage);

        // Get all the userMessageList
        restUserMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].recipientID").value(hasItem(DEFAULT_RECIPIENT_ID)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getUserMessage() throws Exception {
        // Initialize the database
        userMessageRepository.saveAndFlush(userMessage);

        // Get the userMessage
        restUserMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, userMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userMessage.getId().intValue()))
            .andExpect(jsonPath("$.recipientID").value(DEFAULT_RECIPIENT_ID))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserMessage() throws Exception {
        // Get the userMessage
        restUserMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserMessage() throws Exception {
        // Initialize the database
        userMessageRepository.saveAndFlush(userMessage);

        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();

        // Update the userMessage
        UserMessage updatedUserMessage = userMessageRepository.findById(userMessage.getId()).get();
        // Disconnect from session so that the updates on updatedUserMessage are not directly saved in db
        em.detach(updatedUserMessage);
        updatedUserMessage.recipientID(UPDATED_RECIPIENT_ID).message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(updatedUserMessage);

        restUserMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
        UserMessage testUserMessage = userMessageList.get(userMessageList.size() - 1);
        assertThat(testUserMessage.getRecipientID()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testUserMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testUserMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingUserMessage() throws Exception {
        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();
        userMessage.setId(count.incrementAndGet());

        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userMessageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserMessage() throws Exception {
        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();
        userMessage.setId(count.incrementAndGet());

        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserMessage() throws Exception {
        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();
        userMessage.setId(count.incrementAndGet());

        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMessageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMessageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserMessageWithPatch() throws Exception {
        // Initialize the database
        userMessageRepository.saveAndFlush(userMessage);

        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();

        // Update the userMessage using partial update
        UserMessage partialUpdatedUserMessage = new UserMessage();
        partialUpdatedUserMessage.setId(userMessage.getId());

        partialUpdatedUserMessage.recipientID(UPDATED_RECIPIENT_ID).message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);

        restUserMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserMessage))
            )
            .andExpect(status().isOk());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
        UserMessage testUserMessage = userMessageList.get(userMessageList.size() - 1);
        assertThat(testUserMessage.getRecipientID()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testUserMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testUserMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateUserMessageWithPatch() throws Exception {
        // Initialize the database
        userMessageRepository.saveAndFlush(userMessage);

        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();

        // Update the userMessage using partial update
        UserMessage partialUpdatedUserMessage = new UserMessage();
        partialUpdatedUserMessage.setId(userMessage.getId());

        partialUpdatedUserMessage.recipientID(UPDATED_RECIPIENT_ID).message(UPDATED_MESSAGE).updatedAt(UPDATED_UPDATED_AT);

        restUserMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserMessage))
            )
            .andExpect(status().isOk());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
        UserMessage testUserMessage = userMessageList.get(userMessageList.size() - 1);
        assertThat(testUserMessage.getRecipientID()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testUserMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testUserMessage.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingUserMessage() throws Exception {
        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();
        userMessage.setId(count.incrementAndGet());

        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userMessageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserMessage() throws Exception {
        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();
        userMessage.setId(count.incrementAndGet());

        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserMessage() throws Exception {
        int databaseSizeBeforeUpdate = userMessageRepository.findAll().size();
        userMessage.setId(count.incrementAndGet());

        // Create the UserMessage
        UserMessageDTO userMessageDTO = userMessageMapper.toDto(userMessage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMessageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userMessageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserMessage in the database
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserMessage() throws Exception {
        // Initialize the database
        userMessageRepository.saveAndFlush(userMessage);

        int databaseSizeBeforeDelete = userMessageRepository.findAll().size();

        // Delete the userMessage
        restUserMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, userMessage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserMessage> userMessageList = userMessageRepository.findAll();
        assertThat(userMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
