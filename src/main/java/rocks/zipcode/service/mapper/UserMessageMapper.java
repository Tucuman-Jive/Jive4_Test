package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.UserMessage;
import rocks.zipcode.domain.UserProfile;
import rocks.zipcode.service.dto.UserMessageDTO;
import rocks.zipcode.service.dto.UserProfileDTO;

/**
 * Mapper for the entity {@link UserMessage} and its DTO {@link UserMessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMessageMapper extends EntityMapper<UserMessageDTO, UserMessage> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userProfileId")
    UserMessageDTO toDto(UserMessage s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
