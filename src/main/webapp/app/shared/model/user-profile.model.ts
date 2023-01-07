import { IUser } from 'app/shared/model/user.model';
import { IUserMessage } from 'app/shared/model/user-message.model';
import { IChannelMember } from 'app/shared/model/channel-member.model';
import { IChannelMessage } from 'app/shared/model/channel-message.model';

export interface IUserProfile {
  id?: number;
  displayName?: string | null;
  profileImageContentType?: string | null;
  profileImage?: string | null;
  user?: IUser | null;
  userMessages?: IUserMessage[] | null;
  channelMembers?: IChannelMember[] | null;
  channelMessages?: IChannelMessage[] | null;
}

export const defaultValue: Readonly<IUserProfile> = {};
