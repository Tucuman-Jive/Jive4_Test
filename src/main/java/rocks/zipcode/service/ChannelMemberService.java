package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.ChannelMember;
import rocks.zipcode.repository.ChannelMemberRepository;
import rocks.zipcode.service.dto.ChannelMemberDTO;
import rocks.zipcode.service.mapper.ChannelMemberMapper;

/**
 * Service Implementation for managing {@link ChannelMember}.
 */
@Service
@Transactional
public class ChannelMemberService {

    private final Logger log = LoggerFactory.getLogger(ChannelMemberService.class);

    private final ChannelMemberRepository channelMemberRepository;

    private final ChannelMemberMapper channelMemberMapper;

    public ChannelMemberService(ChannelMemberRepository channelMemberRepository, ChannelMemberMapper channelMemberMapper) {
        this.channelMemberRepository = channelMemberRepository;
        this.channelMemberMapper = channelMemberMapper;
    }

    /**
     * Save a channelMember.
     *
     * @param channelMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public ChannelMemberDTO save(ChannelMemberDTO channelMemberDTO) {
        log.debug("Request to save ChannelMember : {}", channelMemberDTO);
        ChannelMember channelMember = channelMemberMapper.toEntity(channelMemberDTO);
        channelMember = channelMemberRepository.save(channelMember);
        return channelMemberMapper.toDto(channelMember);
    }

    /**
     * Update a channelMember.
     *
     * @param channelMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public ChannelMemberDTO update(ChannelMemberDTO channelMemberDTO) {
        log.debug("Request to update ChannelMember : {}", channelMemberDTO);
        ChannelMember channelMember = channelMemberMapper.toEntity(channelMemberDTO);
        channelMember = channelMemberRepository.save(channelMember);
        return channelMemberMapper.toDto(channelMember);
    }

    /**
     * Partially update a channelMember.
     *
     * @param channelMemberDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChannelMemberDTO> partialUpdate(ChannelMemberDTO channelMemberDTO) {
        log.debug("Request to partially update ChannelMember : {}", channelMemberDTO);

        return channelMemberRepository
            .findById(channelMemberDTO.getId())
            .map(existingChannelMember -> {
                channelMemberMapper.partialUpdate(existingChannelMember, channelMemberDTO);

                return existingChannelMember;
            })
            .map(channelMemberRepository::save)
            .map(channelMemberMapper::toDto);
    }

    /**
     * Get all the channelMembers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ChannelMemberDTO> findAll() {
        log.debug("Request to get all ChannelMembers");
        return channelMemberRepository.findAll().stream().map(channelMemberMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one channelMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChannelMemberDTO> findOne(Long id) {
        log.debug("Request to get ChannelMember : {}", id);
        return channelMemberRepository.findById(id).map(channelMemberMapper::toDto);
    }

    /**
     * Delete the channelMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChannelMember : {}", id);
        channelMemberRepository.deleteById(id);
    }
}
