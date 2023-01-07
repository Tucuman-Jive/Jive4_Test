package rocks.zipcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @Column(name = "profile_image_content_type")
    private String profileImageContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userProfile" }, allowSetters = true)
    private Set<UserMessage> userMessages = new HashSet<>();

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userProfile", "channel" }, allowSetters = true)
    private Set<ChannelMember> channelMembers = new HashSet<>();

    @OneToMany(mappedBy = "userProfile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userProfile", "channel" }, allowSetters = true)
    private Set<ChannelMessage> channelMessages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public UserProfile displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public byte[] getProfileImage() {
        return this.profileImage;
    }

    public UserProfile profileImage(byte[] profileImage) {
        this.setProfileImage(profileImage);
        return this;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageContentType() {
        return this.profileImageContentType;
    }

    public UserProfile profileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
        return this;
    }

    public void setProfileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<UserMessage> getUserMessages() {
        return this.userMessages;
    }

    public void setUserMessages(Set<UserMessage> userMessages) {
        if (this.userMessages != null) {
            this.userMessages.forEach(i -> i.setUserProfile(null));
        }
        if (userMessages != null) {
            userMessages.forEach(i -> i.setUserProfile(this));
        }
        this.userMessages = userMessages;
    }

    public UserProfile userMessages(Set<UserMessage> userMessages) {
        this.setUserMessages(userMessages);
        return this;
    }

    public UserProfile addUserMessage(UserMessage userMessage) {
        this.userMessages.add(userMessage);
        userMessage.setUserProfile(this);
        return this;
    }

    public UserProfile removeUserMessage(UserMessage userMessage) {
        this.userMessages.remove(userMessage);
        userMessage.setUserProfile(null);
        return this;
    }

    public Set<ChannelMember> getChannelMembers() {
        return this.channelMembers;
    }

    public void setChannelMembers(Set<ChannelMember> channelMembers) {
        if (this.channelMembers != null) {
            this.channelMembers.forEach(i -> i.setUserProfile(null));
        }
        if (channelMembers != null) {
            channelMembers.forEach(i -> i.setUserProfile(this));
        }
        this.channelMembers = channelMembers;
    }

    public UserProfile channelMembers(Set<ChannelMember> channelMembers) {
        this.setChannelMembers(channelMembers);
        return this;
    }

    public UserProfile addChannelMember(ChannelMember channelMember) {
        this.channelMembers.add(channelMember);
        channelMember.setUserProfile(this);
        return this;
    }

    public UserProfile removeChannelMember(ChannelMember channelMember) {
        this.channelMembers.remove(channelMember);
        channelMember.setUserProfile(null);
        return this;
    }

    public Set<ChannelMessage> getChannelMessages() {
        return this.channelMessages;
    }

    public void setChannelMessages(Set<ChannelMessage> channelMessages) {
        if (this.channelMessages != null) {
            this.channelMessages.forEach(i -> i.setUserProfile(null));
        }
        if (channelMessages != null) {
            channelMessages.forEach(i -> i.setUserProfile(this));
        }
        this.channelMessages = channelMessages;
    }

    public UserProfile channelMessages(Set<ChannelMessage> channelMessages) {
        this.setChannelMessages(channelMessages);
        return this;
    }

    public UserProfile addChannelMessage(ChannelMessage channelMessage) {
        this.channelMessages.add(channelMessage);
        channelMessage.setUserProfile(this);
        return this;
    }

    public UserProfile removeChannelMessage(ChannelMessage channelMessage) {
        this.channelMessages.remove(channelMessage);
        channelMessage.setUserProfile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", displayName='" + getDisplayName() + "'" +
            ", profileImage='" + getProfileImage() + "'" +
            ", profileImageContentType='" + getProfileImageContentType() + "'" +
            "}";
    }
}
