import userProfile from 'app/entities/user-profile/user-profile.reducer';
import channel from 'app/entities/channel/channel.reducer';
import channelMessage from 'app/entities/channel-message/channel-message.reducer';
import channelMember from 'app/entities/channel-member/channel-member.reducer';
import userMessage from 'app/entities/user-message/user-message.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  userProfile,
  channel,
  channelMessage,
  channelMember,
  userMessage,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
