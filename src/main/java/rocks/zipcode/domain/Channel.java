package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Channel.
 */
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userProfile", "channel" }, allowSetters = true)
    private Set<ChannelMessage> channelMessages = new HashSet<>();

    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userProfile", "channel" }, allowSetters = true)
    private Set<ChannelMember> channelMembers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Channel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Channel name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Channel description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Channel updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<ChannelMessage> getChannelMessages() {
        return this.channelMessages;
    }

    public void setChannelMessages(Set<ChannelMessage> channelMessages) {
        if (this.channelMessages != null) {
            this.channelMessages.forEach(i -> i.setChannel(null));
        }
        if (channelMessages != null) {
            channelMessages.forEach(i -> i.setChannel(this));
        }
        this.channelMessages = channelMessages;
    }

    public Channel channelMessages(Set<ChannelMessage> channelMessages) {
        this.setChannelMessages(channelMessages);
        return this;
    }

    public Channel addChannelMessage(ChannelMessage channelMessage) {
        this.channelMessages.add(channelMessage);
        channelMessage.setChannel(this);
        return this;
    }

    public Channel removeChannelMessage(ChannelMessage channelMessage) {
        this.channelMessages.remove(channelMessage);
        channelMessage.setChannel(null);
        return this;
    }

    public Set<ChannelMember> getChannelMembers() {
        return this.channelMembers;
    }

    public void setChannelMembers(Set<ChannelMember> channelMembers) {
        if (this.channelMembers != null) {
            this.channelMembers.forEach(i -> i.setChannel(null));
        }
        if (channelMembers != null) {
            channelMembers.forEach(i -> i.setChannel(this));
        }
        this.channelMembers = channelMembers;
    }

    public Channel channelMembers(Set<ChannelMember> channelMembers) {
        this.setChannelMembers(channelMembers);
        return this;
    }

    public Channel addChannelMember(ChannelMember channelMember) {
        this.channelMembers.add(channelMember);
        channelMember.setChannel(this);
        return this;
    }

    public Channel removeChannelMember(ChannelMember channelMember) {
        this.channelMembers.remove(channelMember);
        channelMember.setChannel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Channel)) {
            return false;
        }
        return id != null && id.equals(((Channel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Channel{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
