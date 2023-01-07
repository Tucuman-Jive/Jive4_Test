package rocks.zipcode.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.zipcode.repository.UserMessageRepository;
import rocks.zipcode.service.UserMessageService;
import rocks.zipcode.service.dto.UserMessageDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.UserMessage}.
 */
@RestController
@RequestMapping("/api")
public class UserMessageResource {

    private final Logger log = LoggerFactory.getLogger(UserMessageResource.class);

    private static final String ENTITY_NAME = "userMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserMessageService userMessageService;

    private final UserMessageRepository userMessageRepository;

    public UserMessageResource(UserMessageService userMessageService, UserMessageRepository userMessageRepository) {
        this.userMessageService = userMessageService;
        this.userMessageRepository = userMessageRepository;
    }

    /**
     * {@code POST  /user-messages} : Create a new userMessage.
     *
     * @param userMessageDTO the userMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userMessageDTO, or with status {@code 400 (Bad Request)} if the userMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-messages")
    public ResponseEntity<UserMessageDTO> createUserMessage(@RequestBody UserMessageDTO userMessageDTO) throws URISyntaxException {
        log.debug("REST request to save UserMessage : {}", userMessageDTO);
        if (userMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new userMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMessageDTO result = userMessageService.save(userMessageDTO);
        return ResponseEntity
            .created(new URI("/api/user-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-messages/:id} : Updates an existing userMessage.
     *
     * @param id the id of the userMessageDTO to save.
     * @param userMessageDTO the userMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMessageDTO,
     * or with status {@code 400 (Bad Request)} if the userMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-messages/{id}")
    public ResponseEntity<UserMessageDTO> updateUserMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserMessageDTO userMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserMessage : {}, {}", id, userMessageDTO);
        if (userMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserMessageDTO result = userMessageService.update(userMessageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-messages/:id} : Partial updates given fields of an existing userMessage, field will ignore if it is null
     *
     * @param id the id of the userMessageDTO to save.
     * @param userMessageDTO the userMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMessageDTO,
     * or with status {@code 400 (Bad Request)} if the userMessageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userMessageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-messages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserMessageDTO> partialUpdateUserMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserMessageDTO userMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserMessage partially : {}, {}", id, userMessageDTO);
        if (userMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserMessageDTO> result = userMessageService.partialUpdate(userMessageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userMessageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-messages} : get all the userMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userMessages in body.
     */
    @GetMapping("/user-messages")
    public List<UserMessageDTO> getAllUserMessages() {
        log.debug("REST request to get all UserMessages");
        return userMessageService.findAll();
    }

    /**
     * {@code GET  /user-messages/:id} : get the "id" userMessage.
     *
     * @param id the id of the userMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-messages/{id}")
    public ResponseEntity<UserMessageDTO> getUserMessage(@PathVariable Long id) {
        log.debug("REST request to get UserMessage : {}", id);
        Optional<UserMessageDTO> userMessageDTO = userMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userMessageDTO);
    }

    /**
     * {@code DELETE  /user-messages/:id} : delete the "id" userMessage.
     *
     * @param id the id of the userMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-messages/{id}")
    public ResponseEntity<Void> deleteUserMessage(@PathVariable Long id) {
        log.debug("REST request to delete UserMessage : {}", id);
        userMessageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
