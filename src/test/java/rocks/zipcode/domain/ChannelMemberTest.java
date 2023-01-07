package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class ChannelMemberTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelMember.class);
        ChannelMember channelMember1 = new ChannelMember();
        channelMember1.setId(1L);
        ChannelMember channelMember2 = new ChannelMember();
        channelMember2.setId(channelMember1.getId());
        assertThat(channelMember1).isEqualTo(channelMember2);
        channelMember2.setId(2L);
        assertThat(channelMember1).isNotEqualTo(channelMember2);
        channelMember1.setId(null);
        assertThat(channelMember1).isNotEqualTo(channelMember2);
    }
}
