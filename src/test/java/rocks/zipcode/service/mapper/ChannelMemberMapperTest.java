package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChannelMemberMapperTest {

    private ChannelMemberMapper channelMemberMapper;

    @BeforeEach
    public void setUp() {
        channelMemberMapper = new ChannelMemberMapperImpl();
    }
}
