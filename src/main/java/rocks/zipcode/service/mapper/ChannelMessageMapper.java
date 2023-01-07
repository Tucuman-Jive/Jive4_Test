package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.ChannelMessage;
import rocks.zipcode.domain.UserProfile;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.ChannelMessageDTO;
import rocks.zipcode.service.dto.UserProfileDTO;

/**
 * Mapper for the entity {@link ChannelMessage} and its DTO {@link ChannelMessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChannelMessageMapper extends EntityMapper<ChannelMessageDTO, ChannelMessage> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userProfileId")
    @Mapping(target = "channel", source = "channel", qualifiedByName = "channelId")
    ChannelMessageDTO toDto(ChannelMessage s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);

    @Named("channelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChannelDTO toDtoChannelId(Channel channel);
}
