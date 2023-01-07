package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.domain.ChannelMember;
import rocks.zipcode.domain.UserProfile;
import rocks.zipcode.service.dto.ChannelDTO;
import rocks.zipcode.service.dto.ChannelMemberDTO;
import rocks.zipcode.service.dto.UserProfileDTO;

/**
 * Mapper for the entity {@link ChannelMember} and its DTO {@link ChannelMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChannelMemberMapper extends EntityMapper<ChannelMemberDTO, ChannelMember> {
    @Mapping(target = "userProfile", source = "userProfile", qualifiedByName = "userProfileId")
    @Mapping(target = "channel", source = "channel", qualifiedByName = "channelId")
    ChannelMemberDTO toDto(ChannelMember s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);

    @Named("channelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChannelDTO toDtoChannelId(Channel channel);
}
