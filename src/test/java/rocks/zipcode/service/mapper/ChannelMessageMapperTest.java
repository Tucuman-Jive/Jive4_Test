package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChannelMessageMapperTest {

    private ChannelMessageMapper channelMessageMapper;

    @BeforeEach
    public void setUp() {
        channelMessageMapper = new ChannelMessageMapperImpl();
    }
}
