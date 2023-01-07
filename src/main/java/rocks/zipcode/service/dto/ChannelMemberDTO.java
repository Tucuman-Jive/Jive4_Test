package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.ChannelMember} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelMemberDTO implements Serializable {

    private Long id;

    private UserProfileDTO userProfile;

    private ChannelDTO channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof ChannelMemberDTO)) {
            return false;
        }

        ChannelMemberDTO channelMemberDTO = (ChannelMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, channelMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelMemberDTO{" +
            "id=" + getId() +
            ", userProfile=" + getUserProfile() +
            ", channel=" + getChannel() +
            "}";
    }
}
