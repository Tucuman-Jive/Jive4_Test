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
import rocks.zipcode.repository.ChannelMessageRepository;
import rocks.zipcode.service.ChannelMessageService;
import rocks.zipcode.service.dto.ChannelMessageDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.ChannelMessage}.
 */
@RestController
@RequestMapping("/api")
public class ChannelMessageResource {

    private final Logger log = LoggerFactory.getLogger(ChannelMessageResource.class);

    private static final String ENTITY_NAME = "channelMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelMessageService channelMessageService;

    private final ChannelMessageRepository channelMessageRepository;

    public ChannelMessageResource(ChannelMessageService channelMessageService, ChannelMessageRepository channelMessageRepository) {
        this.channelMessageService = channelMessageService;
        this.channelMessageRepository = channelMessageRepository;
    }

    /**
     * {@code POST  /channel-messages} : Create a new channelMessage.
     *
     * @param channelMessageDTO the channelMessageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channelMessageDTO, or with status {@code 400 (Bad Request)} if the channelMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/channel-messages")
    public ResponseEntity<ChannelMessageDTO> createChannelMessage(@RequestBody ChannelMessageDTO channelMessageDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChannelMessage : {}", channelMessageDTO);
        if (channelMessageDTO.getId() != null) {
            throw new BadRequestAlertException("A new channelMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChannelMessageDTO result = channelMessageService.save(channelMessageDTO);
        return ResponseEntity
            .created(new URI("/api/channel-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /channel-messages/:id} : Updates an existing channelMessage.
     *
     * @param id the id of the channelMessageDTO to save.
     * @param channelMessageDTO the channelMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelMessageDTO,
     * or with status {@code 400 (Bad Request)} if the channelMessageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channelMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/channel-messages/{id}")
    public ResponseEntity<ChannelMessageDTO> updateChannelMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChannelMessageDTO channelMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChannelMessage : {}, {}", id, channelMessageDTO);
        if (channelMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channelMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChannelMessageDTO result = channelMessageService.update(channelMessageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelMessageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /channel-messages/:id} : Partial updates given fields of an existing channelMessage, field will ignore if it is null
     *
     * @param id the id of the channelMessageDTO to save.
     * @param channelMessageDTO the channelMessageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelMessageDTO,
     * or with status {@code 400 (Bad Request)} if the channelMessageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the channelMessageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the channelMessageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/channel-messages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChannelMessageDTO> partialUpdateChannelMessage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChannelMessageDTO channelMessageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChannelMessage partially : {}, {}", id, channelMessageDTO);
        if (channelMessageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channelMessageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelMessageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChannelMessageDTO> result = channelMessageService.partialUpdate(channelMessageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelMessageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /channel-messages} : get all the channelMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channelMessages in body.
     */
    @GetMapping("/channel-messages")
    public List<ChannelMessageDTO> getAllChannelMessages() {
        log.debug("REST request to get all ChannelMessages");
        return channelMessageService.findAll();
    }

    /**
     * {@code GET  /channel-messages/:id} : get the "id" channelMessage.
     *
     * @param id the id of the channelMessageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channelMessageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/channel-messages/{id}")
    public ResponseEntity<ChannelMessageDTO> getChannelMessage(@PathVariable Long id) {
        log.debug("REST request to get ChannelMessage : {}", id);
        Optional<ChannelMessageDTO> channelMessageDTO = channelMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(channelMessageDTO);
    }

    /**
     * {@code DELETE  /channel-messages/:id} : delete the "id" channelMessage.
     *
     * @param id the id of the channelMessageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/channel-messages/{id}")
    public ResponseEntity<Void> deleteChannelMessage(@PathVariable Long id) {
        log.debug("REST request to delete ChannelMessage : {}", id);
        channelMessageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
