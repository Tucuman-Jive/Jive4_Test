package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.ChannelMessage;
import rocks.zipcode.repository.ChannelMessageRepository;
import rocks.zipcode.service.dto.ChannelMessageDTO;
import rocks.zipcode.service.mapper.ChannelMessageMapper;

/**
 * Service Implementation for managing {@link ChannelMessage}.
 */
@Service
@Transactional
public class ChannelMessageService {

    private final Logger log = LoggerFactory.getLogger(ChannelMessageService.class);

    private final ChannelMessageRepository channelMessageRepository;

    private final ChannelMessageMapper channelMessageMapper;

    public ChannelMessageService(ChannelMessageRepository channelMessageRepository, ChannelMessageMapper channelMessageMapper) {
        this.channelMessageRepository = channelMessageRepository;
        this.channelMessageMapper = channelMessageMapper;
    }

    /**
     * Save a channelMessage.
     *
     * @param channelMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public ChannelMessageDTO save(ChannelMessageDTO channelMessageDTO) {
        log.debug("Request to save ChannelMessage : {}", channelMessageDTO);
        ChannelMessage channelMessage = channelMessageMapper.toEntity(channelMessageDTO);
        channelMessage = channelMessageRepository.save(channelMessage);
        return channelMessageMapper.toDto(channelMessage);
    }

    /**
     * Update a channelMessage.
     *
     * @param channelMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public ChannelMessageDTO update(ChannelMessageDTO channelMessageDTO) {
        log.debug("Request to update ChannelMessage : {}", channelMessageDTO);
        ChannelMessage channelMessage = channelMessageMapper.toEntity(channelMessageDTO);
        channelMessage = channelMessageRepository.save(channelMessage);
        return channelMessageMapper.toDto(channelMessage);
    }

    /**
     * Partially update a channelMessage.
     *
     * @param channelMessageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChannelMessageDTO> partialUpdate(ChannelMessageDTO channelMessageDTO) {
        log.debug("Request to partially update ChannelMessage : {}", channelMessageDTO);

        return channelMessageRepository
            .findById(channelMessageDTO.getId())
            .map(existingChannelMessage -> {
                channelMessageMapper.partialUpdate(existingChannelMessage, channelMessageDTO);

                return existingChannelMessage;
            })
            .map(channelMessageRepository::save)
            .map(channelMessageMapper::toDto);
    }

    /**
     * Get all the channelMessages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelMessageDTO> findAll() {
        log.debug("Request to get all ChannelMessages");
        return channelMessageRepository
            .findAll()
            .stream()
            .map(channelMessageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one channelMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChannelMessageDTO> findOne(Long id) {
        log.debug("Request to get ChannelMessage : {}", id);
        return channelMessageRepository.findById(id).map(channelMessageMapper::toDto);
    }

    /**
     * Delete the channelMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChannelMessage : {}", id);
        channelMessageRepository.deleteById(id);
    }
}
