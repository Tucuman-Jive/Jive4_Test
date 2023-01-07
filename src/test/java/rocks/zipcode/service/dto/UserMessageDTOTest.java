package rocks.zipcode.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class UserMessageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMessageDTO.class);
        UserMessageDTO userMessageDTO1 = new UserMessageDTO();
        userMessageDTO1.setId(1L);
        UserMessageDTO userMessageDTO2 = new UserMessageDTO();
        assertThat(userMessageDTO1).isNotEqualTo(userMessageDTO2);
        userMessageDTO2.setId(userMessageDTO1.getId());
        assertThat(userMessageDTO1).isEqualTo(userMessageDTO2);
        userMessageDTO2.setId(2L);
        assertThat(userMessageDTO1).isNotEqualTo(userMessageDTO2);
        userMessageDTO1.setId(null);
        assertThat(userMessageDTO1).isNotEqualTo(userMessageDTO2);
    }
}
