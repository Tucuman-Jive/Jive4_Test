package rocks.zipcode.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMessageMapperTest {

    private UserMessageMapper userMessageMapper;

    @BeforeEach
    public void setUp() {
        userMessageMapper = new UserMessageMapperImpl();
    }
}
