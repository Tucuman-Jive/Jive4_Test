import dayjs from 'dayjs';
import { IChannelMessage } from 'app/shared/model/channel-message.model';
import { IChannelMember } from 'app/shared/model/channel-member.model';

export interface IChannel {
  id?: number;
  name?: string | null;
  description?: string | null;
  updatedAt?: string | null;
  channelMessages?: IChannelMessage[] | null;
  channelMembers?: IChannelMember[] | null;
}

export const defaultValue: Readonly<IChannel> = {};
