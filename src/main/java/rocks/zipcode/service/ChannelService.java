package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.repository.ChannelRepository;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.mapper.ChannelMapper;

/**
 * Service Implementation for managing {@link Channel}.
 */
@Service
@Transactional
public class ChannelService {

    private final Logger log = LoggerFactory.getLogger(ChannelService.class);

    private final ChannelRepository channelRepository;

    private final ChannelMapper channelMapper;

    public ChannelService(ChannelRepository channelRepository, ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
    }

    /**
     * Save a channel.
     *
     * @param channelDTO the entity to save.
     * @return the persisted entity.
     */
    public ChannelDTO save(ChannelDTO channelDTO) {
        log.debug("Request to save Channel : {}", channelDTO);
        Channel channel = channelMapper.toEntity(channelDTO);
        channel = channelRepository.save(channel);
        return channelMapper.toDto(channel);
    }

    /**
     * Update a channel.
     *
     * @param channelDTO the entity to save.
     * @return the persisted entity.
     */
    public ChannelDTO update(ChannelDTO channelDTO) {
        log.debug("Request to update Channel : {}", channelDTO);
        Channel channel = channelMapper.toEntity(channelDTO);
        channel = channelRepository.save(channel);
        return channelMapper.toDto(channel);
    }

    /**
     * Partially update a channel.
     *
     * @param channelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChannelDTO> partialUpdate(ChannelDTO channelDTO) {
        log.debug("Request to partially update Channel : {}", channelDTO);

        return channelRepository
            .findById(channelDTO.getId())
            .map(existingChannel -> {
                channelMapper.partialUpdate(existingChannel, channelDTO);

                return existingChannel;
            })
            .map(channelRepository::save)
            .map(channelMapper::toDto);
    }

    /**
     * Get all the channels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelDTO> findAll() {
        log.debug("Request to get all Channels");
        return channelRepository.findAll().stream().map(channelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one channel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChannelDTO> findOne(Long id) {
        log.debug("Request to get Channel : {}", id);
        return channelRepository.findById(id).map(channelMapper::toDto);
    }

    /**
     * Delete the channel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Channel : {}", id);
        channelRepository.deleteById(id);
    }
}
