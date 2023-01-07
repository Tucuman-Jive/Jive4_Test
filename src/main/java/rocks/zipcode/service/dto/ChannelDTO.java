package rocks.zipcode.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link rocks.zipcode.domain.Channel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ChannelDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelDTO)) {
            return false;
        }

        ChannelDTO channelDTO = (ChannelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, channelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
