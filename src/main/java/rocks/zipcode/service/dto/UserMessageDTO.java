package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.UserMessage} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserMessageDTO implements Serializable {

    private Long id;

    private Integer recipientID;

    private String message;

    private Instant updatedAt;

    private UserProfileDTO userProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(Integer recipientID) {
        this.recipientID = recipientID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserMessageDTO)) {
            return false;
        }

        UserMessageDTO userMessageDTO = (UserMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserMessageDTO{" +
            "id=" + getId() +
            ", recipientID=" + getRecipientID() +
            ", message='" + getMessage() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", userProfile=" + getUserProfile() +
            "}";
    }
}
