package rocks.zipcode.service.mapper;

import org.mapstruct.*;
import rocks.zipcode.domain.Channel;
import rocks.zipcode.service.dto.ChannelDTO;

/**
 * Mapper for the entity {@link Channel} and its DTO {@link ChannelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChannelMapper extends EntityMapper<ChannelDTO, Channel> {}
