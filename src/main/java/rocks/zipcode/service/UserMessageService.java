package rocks.zipcode.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocks.zipcode.domain.UserMessage;
import rocks.zipcode.repository.UserMessageRepository;
import rocks.zipcode.service.dto.UserMessageDTO;
import rocks.zipcode.service.mapper.UserMessageMapper;

/**
 * Service Implementation for managing {@link UserMessage}.
 */
@Service
@Transactional
public class UserMessageService {

    private final Logger log = LoggerFactory.getLogger(UserMessageService.class);

    private final UserMessageRepository userMessageRepository;

    private final UserMessageMapper userMessageMapper;

    public UserMessageService(UserMessageRepository userMessageRepository, UserMessageMapper userMessageMapper) {
        this.userMessageRepository = userMessageRepository;
        this.userMessageMapper = userMessageMapper;
    }

    /**
     * Save a userMessage.
     *
     * @param userMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public UserMessageDTO save(UserMessageDTO userMessageDTO) {
        log.debug("Request to save UserMessage : {}", userMessageDTO);
        UserMessage userMessage = userMessageMapper.toEntity(userMessageDTO);
        userMessage = userMessageRepository.save(userMessage);
        return userMessageMapper.toDto(userMessage);
    }

    /**
     * Update a userMessage.
     *
     * @param userMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public UserMessageDTO update(UserMessageDTO userMessageDTO) {
        log.debug("Request to update UserMessage : {}", userMessageDTO);
        UserMessage userMessage = userMessageMapper.toEntity(userMessageDTO);
        userMessage = userMessageRepository.save(userMessage);
        return userMessageMapper.toDto(userMessage);
    }

    /**
     * Partially update a userMessage.
     *
     * @param userMessageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserMessageDTO> partialUpdate(UserMessageDTO userMessageDTO) {
        log.debug("Request to partially update UserMessage : {}", userMessageDTO);

        return userMessageRepository
            .findById(userMessageDTO.getId())
            .map(existingUserMessage -> {
                userMessageMapper.partialUpdate(existingUserMessage, userMessageDTO);

                return existingUserMessage;
            })
            .map(userMessageRepository::save)
            .map(userMessageMapper::toDto);
    }

    /**
     * Get all the userMessages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserMessageDTO> findAll() {
        log.debug("Request to get all UserMessages");
        return userMessageRepository.findAll().stream().map(userMessageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one userMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserMessageDTO> findOne(Long id) {
        log.debug("Request to get UserMessage : {}", id);
        return userMessageRepository.findById(id).map(userMessageMapper::toDto);
    }

    /**
     * Delete the userMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserMessage : {}", id);
        userMessageRepository.deleteById(id);
    }
}
