package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class ChannelMemberDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChannelMemberDTO.class);
        ChannelMemberDTO channelMemberDTO1 = new ChannelMemberDTO();
        channelMemberDTO1.setId(1L);
        ChannelMemberDTO channelMemberDTO2 = new ChannelMemberDTO();
        assertThat(channelMemberDTO1).isNotEqualTo(channelMemberDTO2);
        channelMemberDTO2.setId(channelMemberDTO1.getId());
        assertThat(channelMemberDTO1).isEqualTo(channelMemberDTO2);
        channelMemberDTO2.setId(2L);
        assertThat(channelMemberDTO1).isNotEqualTo(channelMemberDTO2);
        channelMemberDTO1.setId(null);
        assertThat(channelMemberDTO1).isNotEqualTo(channelMemberDTO2);
    }
}
