package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class UserMessageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMessage.class);
        UserMessage userMessage1 = new UserMessage();
        userMessage1.setId(1L);
        UserMessage userMessage2 = new UserMessage();
        userMessage2.setId(userMessage1.getId());
        assertThat(userMessage1).isEqualTo(userMessage2);
        userMessage2.setId(2L);
        assertThat(userMessage1).isNotEqualTo(userMessage2);
        userMessage1.setId(null);
        assertThat(userMessage1).isNotEqualTo(userMessage2);
    }
}
