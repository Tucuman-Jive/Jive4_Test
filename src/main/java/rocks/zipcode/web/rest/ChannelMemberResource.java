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
import rocks.zipcode.repository.ChannelMemberRepository;
import rocks.zipcode.service.ChannelMemberService;
import rocks.zipcode.service.dto.ChannelMemberDTO;
import rocks.zipcode.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link rocks.zipcode.domain.ChannelMember}.
 */
@RestController
@RequestMapping("/api")
public class ChannelMemberResource {

    private final Logger log = LoggerFactory.getLogger(ChannelMemberResource.class);

    private static final String ENTITY_NAME = "channelMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelMemberService channelMemberService;

    private final ChannelMemberRepository channelMemberRepository;

    public ChannelMemberResource(ChannelMemberService channelMemberService, ChannelMemberRepository channelMemberRepository) {
        this.channelMemberService = channelMemberService;
        this.channelMemberRepository = channelMemberRepository;
    }

    /**
     * {@code POST  /channel-members} : Create a new channelMember.
     *
     * @param channelMemberDTO the channelMemberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channelMemberDTO, or with status {@code 400 (Bad Request)} if the channelMember has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/channel-members")
    public ResponseEntity<ChannelMemberDTO> createChannelMember(@RequestBody ChannelMemberDTO channelMemberDTO) throws URISyntaxException {
        log.debug("REST request to save ChannelMember : {}", channelMemberDTO);
        if (channelMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new channelMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChannelMemberDTO result = channelMemberService.save(channelMemberDTO);
        return ResponseEntity
            .created(new URI("/api/channel-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /channel-members/:id} : Updates an existing channelMember.
     *
     * @param id the id of the channelMemberDTO to save.
     * @param channelMemberDTO the channelMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelMemberDTO,
     * or with status {@code 400 (Bad Request)} if the channelMemberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channelMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/channel-members/{id}")
    public ResponseEntity<ChannelMemberDTO> updateChannelMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChannelMemberDTO channelMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChannelMember : {}, {}", id, channelMemberDTO);
        if (channelMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channelMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChannelMemberDTO result = channelMemberService.update(channelMemberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelMemberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /channel-members/:id} : Partial updates given fields of an existing channelMember, field will ignore if it is null
     *
     * @param id the id of the channelMemberDTO to save.
     * @param channelMemberDTO the channelMemberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channelMemberDTO,
     * or with status {@code 400 (Bad Request)} if the channelMemberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the channelMemberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the channelMemberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/channel-members/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChannelMemberDTO> partialUpdateChannelMember(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChannelMemberDTO channelMemberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChannelMember partially : {}, {}", id, channelMemberDTO);
        if (channelMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channelMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChannelMemberDTO> result = channelMemberService.partialUpdate(channelMemberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channelMemberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /channel-members} : get all the channelMembers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channelMembers in body.
     */
    @GetMapping("/channel-members")
    public List<ChannelMemberDTO> getAllChannelMembers() {
        log.debug("REST request to get all ChannelMembers");
        return channelMemberService.findAll();
    }

    /**
     * {@code GET  /channel-members/:id} : get the "id" channelMember.
     *
     * @param id the id of the channelMemberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channelMemberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/channel-members/{id}")
    public ResponseEntity<ChannelMemberDTO> getChannelMember(@PathVariable Long id) {
        log.debug("REST request to get ChannelMember : {}", id);
        Optional<ChannelMemberDTO> channelMemberDTO = channelMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(channelMemberDTO);
    }

    /**
     * {@code DELETE  /channel-members/:id} : delete the "id" channelMember.
     *
     * @param id the id of the channelMemberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/channel-members/{id}")
    public ResponseEntity<Void> deleteChannelMember(@PathVariable Long id) {
        log.debug("REST request to delete ChannelMember : {}", id);
        channelMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
