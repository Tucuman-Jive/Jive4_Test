package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class ChannelMessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelMessage.class);
        ChannelMessage channelMessage1 = new ChannelMessage();
        channelMessage1.setId(1L);
        ChannelMessage channelMessage2 = new ChannelMessage();
        channelMessage2.setId(channelMessage1.getId());
        assertThat(channelMessage1).isEqualTo(channelMessage2);
        channelMessage2.setId(2L);
        assertThat(channelMessage1).isNotEqualTo(channelMessage2);
        channelMessage1.setId(null);
        assertThat(channelMessage1).isNotEqualTo(channelMessage2);
    }
}
