package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.ChannelMessage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelMessageDTO implements Serializable {

    private Long id;

    private String message;

    private Instant updatedAt;

    private UserProfileDTO userProfile;

    private ChannelDTO channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelMessageDTO)) {
            return false;
        }

        ChannelMessageDTO channelMessageDTO = (ChannelMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, channelMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelMessageDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", userProfile=" + getUserProfile() +
            ", channel=" + getChannel() +
            "}";
    }
}
